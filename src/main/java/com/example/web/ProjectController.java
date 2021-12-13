package com.example.web;

import com.example.domain.CustomException;
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


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


@Controller
public class ProjectController {

  private final ProjectService projectService = new ProjectService();
  private final TeammateService teammateService = new TeammateService();


  @GetMapping("/createProject")
  public String createProject(Model model) {
    Project projectNew = new Project();
    model.addAttribute("projectNew", projectNew);

    LocalDate minStartDateProject = new DateService().getToday();
    model.addAttribute("minStartDateProject", minStartDateProject);

    return "createproject";
  }


  // method for "Add project" fields and button on "frontpage"
  @PostMapping("/addProject")
  public String saveProject(HttpServletRequest request) throws CustomException {

    //Retrieve request from session
    HttpSession session = request.getSession();

    // Retrieve values from HTML form via WebRequest
    String projectName = request.getParameter("projectName");
    String projectCategory = request.getParameter("category");

    String hoursTotalStr = request.getParameter("hoursTotal");
    int hoursTotal = Integer.parseInt(hoursTotalStr);

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String projectStartDateStr = request.getParameter("startDate");

    //convert String to LocalDate
    LocalDate projectStartDate = LocalDate.parse(projectStartDateStr, formatter);
    String projectEndDateStr = request.getParameter("endDate");

    //convert String to LocalDate
    LocalDate projectEndDate = LocalDate.parse(projectEndDateStr, formatter);

     boolean isValidEndDate = new DateService().isValidEndDate(projectStartDate, projectEndDate);

     if (!isValidEndDate){
        throw new CustomException("Entered end date is wrong, please, choose end date as minimum" +
            "as the next day after start date");
     }

    // Retrieve "email" String object from HTTP session
    String ownerEmail = (String) session.getAttribute("email");
    String projectDescription = request.getParameter("description");

    if (projectName == null || projectName.trim().equals( "" )) {
      throw new CustomException("The project name cannot be empty, please, try again");
    }
    // Make "projectNew" object and assign new values
    Project projectNew = new Project(projectName, projectCategory, hoursTotal, projectStartDate, projectEndDate,
        ownerEmail, projectDescription);


    // Work + data is delegated to login service
    projectService.createProject(projectNew);
    session.setAttribute("projectNew", projectNew);

    ArrayList<Project> projects = new ProjectService().findAllProjectsUser(ownerEmail); // DATABASE-agtig kodning???
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

    Subproject subprojectNew = new Subproject();
    model.addAttribute("subprojectNew", subprojectNew);
    String teammateNew = new String();
    model.addAttribute("teammateNew", teammateNew);

    ArrayList<String> teammates = teammateService.readTeammates(projectSelected.getProjectID());
    model.addAttribute("teammates", teammates);


    int amountPersonsInTeam = teammateService.countTeammates(projectSelected.getProjectID());
    model.addAttribute("amountPersonsInTeam", amountPersonsInTeam);

    ArrayList<Subproject> subprojects = projectSelected.getSubprojects(); // DATABASE-agtig kodning???

    model.addAttribute("subprojects", subprojects);

    return "showproject";
  }

  @GetMapping("/editProject/{projectName}")
  public String editProject(HttpServletRequest request, Model model, @PathVariable(value = "projectName")
      String projectName) throws CustomException { //TO DO path in browser

    HttpSession session = request.getSession();
    CalculatorService calculatorService = new CalculatorService(); // make privat final for whole Class?

    // taking back out of session our selected project, now with subprojects
    Project projectSelected = (Project) session.getAttribute("projectSelected");
    model.addAttribute("projectSelected", projectSelected);

    int teammatesAmount = teammateService.countTeammates(projectSelected.getProjectID());
    model.addAttribute("teammatesAmount", teammatesAmount);

    if (teammatesAmount < 1){
      throw new CustomException("The project must have at least one team member");
    }


    int totalHoursTeam = teammateService.calculateTotalHoursPerDay(projectSelected.getProjectID());
    model.addAttribute("totalHoursTeam", totalHoursTeam);

    int timeLeftProject = calculatorService.calculateTimeLeftProject(projectSelected);
    model.addAttribute("timeLeftProject", timeLeftProject);

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

    return "projectedit";
  }


  @PostMapping("/updateProject")
  public String updateProject(HttpServletRequest request) throws CustomException {
    //Retrieve request from session
    HttpSession session = request.getSession();

    // Retrieve values from HTML form via HttpServletRequest
    Project projectToUpdate = (Project) session.getAttribute("projectSelected");

    Project projectUpdated = new Project(
        (projectToUpdate.getProjectID()),
        (request.getParameter("projectName")),
        (request.getParameter("category")),
        (Integer.parseInt(request.getParameter("hoursTotal"))),
        (LocalDate.parse(request.getParameter("startDate"), DateTimeFormatter.ofPattern("yyyy-MM-dd"))),
        (LocalDate.parse(request.getParameter("endDate"), DateTimeFormatter.ofPattern("yyyy-MM-dd"))),
        ((String) session.getAttribute("email")),
        (request.getParameter("description")));

    projectService.updateProject(projectUpdated);


    // Go to page
    return "redirect:/frontpage";   // TO DO, evt return to frontpage select
  }

  @GetMapping("/deleteProject/{projectName}")
  public String deleteProject(@PathVariable(value = "projectName") String projectName) {
    projectService.deleteProject(projectName);
    return "redirect:/frontpage";
  }

  @GetMapping("/treeview")
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

    /*  ArrayList<String> teammatesEmail = new TeammateService().readTeammates(projectTree.getProjectID());
      model.addAttribute("teammatesEmail", teammatesEmail);*/

      return "treeview";
    }

  @ExceptionHandler(CustomException.class)
  public String handleError(Model model, Exception exception) {
    model.addAttribute("message", exception.getMessage());
    return "exceptionpageProject";

  }
}
