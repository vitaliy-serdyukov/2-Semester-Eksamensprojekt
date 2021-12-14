package com.example.web;


import com.example.domain.exceptions.ProjectInputException;
import com.example.domain.exceptions.SubprojectInputException;
import com.example.domain.exceptions.SubprojectUpdateException;
import com.example.domain.models.Project;
import com.example.domain.models.Subproject;
import com.example.domain.models.Task;
import com.example.domain.services.CalculatorService;
import com.example.domain.services.DateService;
import com.example.domain.services.SubprojectService;
import com.example.domain.services.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


@Controller
public class SubprojectController {

  private final SubprojectService subprojectService = new SubprojectService();


  @GetMapping("/createSubproject")
  public String createSubroject(HttpServletRequest request, Model model){

    Subproject subprojectNew = new Subproject();
    model.addAttribute("subprojectNew", subprojectNew);

    HttpSession session = request.getSession();
    Project projectSelected = (Project) session.getAttribute("projectSelected");

    LocalDate minStartDateSubproject = projectSelected.getStartDate();
    model.addAttribute("minStartDateSubproject", minStartDateSubproject);

    LocalDate maxEndDateSubproject = projectSelected.getEndDate();
    model.addAttribute("maxEndDateSubproject", maxEndDateSubproject);

    int timeLeftProject = new CalculatorService().calculateTimeLeftProject(projectSelected);
    model.addAttribute("timeLeftProject", timeLeftProject);

    return "subproject/createsubproject";
  }


  // method for "Add Subproject" fields and button on "createsubproject"
  @PostMapping("/addSubproject")
  public String saveSubproject(HttpServletRequest request, RedirectAttributes redirectAttrs) throws ProjectInputException, SubprojectInputException {

    //Retrieve request from session
    HttpSession session = request.getSession();
    Project projectSelected = (Project) session.getAttribute("projectSelected");

    int projectID = projectSelected.getProjectID();

    String subprojectName = request.getParameter("subprojectName");

    // validate subproject name for backspace or empty String input
    if (subprojectName == null || subprojectName.trim().equals( "" )) {
      throw new SubprojectInputException("The subproject name cannot be empty, please, try again");
    }

    String hoursTotalStr = request.getParameter("hoursTotal");
    int hoursTotal = Integer.parseInt(hoursTotalStr);


    int timeLeftProject = new CalculatorService().calculateTimeLeftProject(projectSelected);
    if (timeLeftProject < hoursTotal) {
      throw new SubprojectInputException("It was unable to create a subproject, because the entered amount of hours exceeds" +
          "available amount of hours in project left ");
    }

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    String subprojectStartDateStr = request.getParameter("startDate");
    //convert String to LocalDate
    LocalDate subprojectStartDate = LocalDate.parse(subprojectStartDateStr, formatter);

    String subprojectEndDateStr = request.getParameter("endDate");
    //convert String to LocalDate
    LocalDate subprojectEndDate = LocalDate.parse(subprojectEndDateStr, formatter);

    // validate end date
    boolean isValidEndDate = new DateService().isValidEndDate(subprojectStartDate, subprojectEndDate);
    if (!isValidEndDate){
      throw new SubprojectInputException("Entered end date is wrong, please, choose end date as minimum" +
          "as the next day after start date");
    }

    String subprojectDescription = request.getParameter("description");

    // Make "subprojectNew" object and assign new values
    Subproject subprojectNew = new Subproject(projectID, subprojectName, hoursTotal, subprojectStartDate,
        subprojectEndDate, subprojectDescription);

    // Work + data is delegated to login service
    subprojectService.createSubproject(subprojectNew);
    redirectAttrs.addAttribute("projectName", projectSelected.getProjectName());
    // Go to page
    return "redirect:/showProject/{projectName}";
  }



  @GetMapping("/showSubproject/{subprojectName}")
  public String showSubproject(HttpServletRequest request, Model model,
                               @PathVariable(value = "subprojectName") String subprojectName) {
    HttpSession session = request.getSession();

    Subproject subprojectSelected = subprojectService.showSubprojectInfo(subprojectName);

    ArrayList<Task> tasksTemp = new TaskService().
        findAllTasksInSubproject(subprojectSelected.getSubprojectID());

    subprojectSelected.addTasks(tasksTemp);

    session.setAttribute("subprojectSelected", subprojectSelected);
    model.addAttribute("subprojectSelected", subprojectSelected);

    Task taskNew = new Task();
    model.addAttribute("taskNew", taskNew);

    ArrayList<Task> tasks = subprojectSelected.getTasks();

    model.addAttribute("tasks", tasks);
    return "subproject/showsubproject";
  }



  @GetMapping("/editSubproject/{subprojectName}")
  public String editSubProject(HttpServletRequest request, Model model, @PathVariable(value = "subprojectName") String subProjectName) { //TO DO path in browser
    HttpSession session = request.getSession();
    Subproject subprojectSelected = subprojectService.showSubprojectInfo(subProjectName);
    session.setAttribute("subprojectSelected", subprojectSelected);
    model.addAttribute("subprojectSelected", subprojectSelected);
    return "subproject/editsubproject";
  }



  @PostMapping("/updateSubproject")
  public String updateSubproject(HttpServletRequest request, RedirectAttributes redirectAttrs) {
    //Retrieve request from session

    HttpSession session = request.getSession();

    // Retrieve values from HTML form via HTTPServlet request
    Subproject subprojectToUpdate = (Subproject) session.getAttribute("subprojectSelected");

    Subproject subprojectUpdated = new Subproject(
        (subprojectToUpdate.getProjectID()),
        (subprojectToUpdate.getSubprojectID()),
        (request.getParameter("subprojectName")),
        (Integer.parseInt(request.getParameter("hoursTotal"))),
        (LocalDate.parse(request.getParameter("startDate"), DateTimeFormatter.ofPattern("yyyy-MM-dd"))),
        (LocalDate.parse(request.getParameter("endDate"), DateTimeFormatter.ofPattern("yyyy-MM-dd"))),
        (request.getParameter("description")));

    subprojectService.updateSubProject(subprojectUpdated);

    redirectAttrs.addAttribute("subprojectName", subprojectUpdated.getSubprojectName());
    return "redirect:/showSubproject/{subprojectName}";
  }



  @GetMapping("/deleteSubproject/{subprojectName}")
  public String deleteSubroject(HttpServletRequest request, RedirectAttributes redirectAttrs,
                                @PathVariable(value = "subprojectName") String subprojectName) {
    subprojectService.deleteSubproject(subprojectName);

    HttpSession session = request.getSession();
    Project projectSelected = (Project) session.getAttribute("projectSelected");

    redirectAttrs.addAttribute("projectName", projectSelected.getProjectName());
    return "redirect:/showProject/{projectName}";
  }


  @GetMapping("/returnToProject")
  public String returnToProject(HttpServletRequest request, RedirectAttributes redirectAttrs) {
    HttpSession session = request.getSession();
    Project projectSelected = (Project) session.getAttribute("projectSelected");
    redirectAttrs.addAttribute("projectName", projectSelected.getProjectName());
    return "redirect:/showProject/{projectName}";
  }

  @ExceptionHandler(SubprojectInputException.class)
  public String handleInputError(Model model, Exception exception) {
    model.addAttribute("message", exception.getMessage());
    return "subproject/subprojectInputExceptionPage";
  }

  @ExceptionHandler(SubprojectUpdateException.class)
  public String handleUpdateError(Model model, Exception exception) {
    model.addAttribute("message", exception.getMessage());
    return "subproject/subprojectUpdateExceptionPage";
  }
}