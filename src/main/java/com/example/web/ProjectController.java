package com.example.web;

import com.example.domain.exceptions.ProjectInputException;
import com.example.domain.exceptions.ProjectUpdateException;
import com.example.domain.models.Project;
import com.example.domain.models.Subproject;

import com.example.domain.models.Task;
import com.example.domain.services.*;
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
public class ProjectController {

  private final ProjectService projectService = new ProjectService();
  private final TeammateService teammateService = new TeammateService();

  // navigate to "create project" form, "Add project" button on "frontpage"
  @GetMapping("/createProject")
  public String createProject(Model model) {
    Project projectNew = new Project();
    model.addAttribute("projectNew", projectNew);
    LocalDate minStartDateProject = LocalDate.now();
    model.addAttribute("minStartDateProject", minStartDateProject);
    return "project/createproject";
  }


  //  gathering data and sending user to "frontpage" again
  @PostMapping("/addProject")
  public String saveProject(HttpServletRequest request) throws ProjectInputException {

    //return the valid session object associated with the request
    HttpSession session = request.getSession();

    // Retrieve values from HTML form via request
    String projectName = request.getParameter("projectName");

    // validate project name for backspace or empty String input
    if (!new ValidatorService().isValidName(projectName)){
      throw new ProjectInputException("The subproject name cannot be empty, please, try again");
    }

    String projectCategory = request.getParameter("category");

    String hoursTotalStr = request.getParameter("hoursTotal");
    // getting int from our String
    int hoursTotal = Integer.parseInt(hoursTotalStr);

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String projectStartDateStr = request.getParameter("startDate");

    //convert String to LocalDate
    LocalDate projectStartDate = LocalDate.parse(projectStartDateStr, formatter);

    String projectEndDateStr = request.getParameter("endDate");

    //convert String to LocalDate
    LocalDate projectEndDate = LocalDate.parse(projectEndDateStr, formatter);

    // validate end date
    if (!new ValidatorService().isValidEndDate(projectStartDate, projectEndDate)){
      throw new ProjectInputException("Entered end date is wrong, please, choose end date as minimum" +
          "as the next day after start date");
    }

    // Retrieve "email" String object from HTTP session
    String ownerEmail = (String) session.getAttribute("email");
    String projectDescription = request.getParameter("description");

    // Assign ny values to projectNew object
    Project projectNew = new Project(projectName, projectCategory, hoursTotal, projectStartDate, projectEndDate,
        ownerEmail, projectDescription);


    // Work + data is delegated to project service
    projectService.createProject(projectNew);
    session.setAttribute("projectNew", projectNew);

    ArrayList<Project> projects = new ProjectService().findAllProjectsUser(ownerEmail);
    session.setAttribute("projects", projects);

    // Go to page
    return "redirect:/frontpage";
  }


  @GetMapping("/showProject/{projectName}")
  public String showProject(HttpServletRequest request, Model model,
                            @PathVariable(value = "projectName") String projectName) {
    HttpSession session = request.getSession();

    Project projectSelected = projectService.showProjectInfo(projectName);

    ArrayList<Subproject> subprojectsTemp = new SubprojectService().
        findAllSubprojectsInProject(projectSelected.getProjectID());

    projectSelected.addSubprojects(subprojectsTemp);
    session.setAttribute("projectSelected", projectSelected);

    model.addAttribute("projectSelected", projectSelected);
    model.addAttribute("subprojects", projectSelected.getSubprojects());

    Subproject subprojectNew = new Subproject();
    model.addAttribute("subprojectNew", subprojectNew);
    String teammateNew = "";
    model.addAttribute("teammateNew", teammateNew);

    ArrayList<String> teammates = teammateService.readTeammates(projectSelected.getProjectID());
    model.addAttribute("teammates", teammates);

    int amountPersonsInTeam = teammateService.countTeammates(projectSelected.getProjectID());
    model.addAttribute("amountPersonsInTeam", amountPersonsInTeam);

    return "project/showproject";
  }



  @GetMapping("/editProject/{projectName}")
  public String editProject(HttpServletRequest request, Model model/*, @PathVariable(value = "projectName")
      String projectName*/) throws ProjectUpdateException {

    HttpSession session = request.getSession();
    CalculatorService calculatorService = new CalculatorService();

    // taking back out of session our selected project, now with subprojects
    Project projectSelected = (Project) session.getAttribute("projectSelected");
    model.addAttribute("projectSelected", projectSelected);

    int teammatesAmount = teammateService.countTeammates(projectSelected.getProjectID());
    model.addAttribute("teammatesAmount", teammatesAmount);

    if (teammatesAmount < 1){
      throw new ProjectUpdateException ("The project must have at least one team member");
    }

    int totalHoursTeam = teammateService.calculateTotalHoursPerDay(projectSelected.getProjectID());
    model.addAttribute("totalHoursTeam", totalHoursTeam);

    int hoursLeftProject = projectSelected.getHoursLeftProject();
    model.addAttribute("hoursLeftProject", hoursLeftProject); // virker???

    double dayAmountNeeded = calculatorService.calculateDaysNeeded(projectSelected.getHoursTotal(),
        projectSelected.getProjectID());
    model.addAttribute("dayAmountNeeded", dayAmountNeeded);

    double dayAmountExpected = calculatorService.countDaysExpected(projectSelected.getStartDate(),
        projectSelected.getEndDate());
    model.addAttribute("dayAmountExpected", dayAmountExpected);

    LocalDate realEndDate = calculatorService.countDateEnd(projectSelected.getStartDate(),
        projectSelected.getHoursTotal(), projectSelected.getProjectID());
    model.addAttribute("realEndDate", realEndDate);

    boolean isEnough = calculatorService.isTimeEnough(projectSelected.getStartDate(), projectSelected.getEndDate(),
        projectSelected.getHoursTotal(), projectSelected.getProjectID());
    model.addAttribute("isEnough", isEnough);

    double neededSpeed = calculatorService.calculateSpeedDaily(projectSelected.getStartDate(), projectSelected.getEndDate(),
        projectSelected.getHoursTotal());
    model.addAttribute("neededSpeed", neededSpeed);

    return "project/editproject";
  }


  @PostMapping("/updateProject")
  public String updateProject(HttpServletRequest request, RedirectAttributes redirectAttrs)
      throws ProjectUpdateException {

    //Retrieve request from session
    HttpSession session = request.getSession();

    // Retrieve values from HTML form via HttpServletRequest
    Project projectSelected = (Project) session.getAttribute("projectSelected");

    Project projectUpdated = new Project(
        (projectSelected.getProjectID()),
        (request.getParameter("projectName")),
        (request.getParameter("category")),
        (Integer.parseInt(request.getParameter("hoursTotal"))),
        (LocalDate.parse(request.getParameter("startDate"), DateTimeFormatter.ofPattern("yyyy-MM-dd"))),
        (LocalDate.parse(request.getParameter("endDate"), DateTimeFormatter.ofPattern("yyyy-MM-dd"))),
        ((String) session.getAttribute("email")),
        (request.getParameter("description")));

        projectUpdated.setSubprojects(projectSelected.getSubprojects());

    // validate project name for backspace or empty String input and validate end date
        ValidatorService validatorService = new ValidatorService();

    if (!validatorService.isValidStartDateProject(projectUpdated.getStartDate()) ||
        !validatorService.isValidName(projectUpdated.getProjectName()) ||
        !validatorService.isValidEndDate(projectUpdated.getStartDate(), projectUpdated.getEndDate())){
      throw new ProjectUpdateException("Either project name or end date is wrong..." +
          "Name may not be empty, start date may not be before today and" +
          " the end date has to be as minimum as the next day after start date." +
          "Please, try again");
    }

    projectService.updateProject(projectUpdated);
    projectSelected = projectUpdated;
    session.setAttribute("projectSelected", projectSelected);

    // Go to page
    redirectAttrs.addAttribute("projectName", projectSelected.getProjectName());
    return "redirect:/showProject/{projectName}";
  }


  @GetMapping("/deleteProject/{projectName}")
  public String deleteProject(@PathVariable(value = "projectName") String projectName) {
    projectService.deleteProject(projectName);
    return "redirect:/frontpage";
  }

  @GetMapping("/treeview") // An experiment, under construction, not finished yet
  public String treeView(HttpServletRequest request, Model model) {
    HttpSession session = request.getSession();

    Project projectTree = (Project) session.getAttribute("projectSelected");
    model.addAttribute("projectTree", projectTree);

    ArrayList<Subproject> subprojectsTree = projectTree.getSubprojects();
    model.addAttribute("subprojectsTree", subprojectsTree);

    Subproject subprojectTree = new Subproject();
    model.addAttribute("subprojectTree", subprojectTree);

    ArrayList<Task> tasksTree = subprojectTree.getTasks();
    model.addAttribute("tasksTree", tasksTree);

    Task taskTree = new Task();
    model.addAttribute("taskTree", taskTree);

      return "project/treeview";
    }

  @ExceptionHandler(ProjectInputException.class)
  public String handleInputError(Model model, Exception exception) {
    model.addAttribute("message", exception.getMessage());
    return "project/projectInputExceptionPage";

  }

  @ExceptionHandler(ProjectUpdateException.class)
  public String handleUpdateError(Model model, Exception exception) {
    model.addAttribute("message", exception.getMessage());
    return "project/projectUpdateExceptionPage";
  }
}