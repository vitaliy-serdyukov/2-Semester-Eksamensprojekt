package com.example.web;

import com.example.domain.LoginSampleException;
import com.example.domain.models.Project;
import com.example.domain.models.Subproject;
import com.example.domain.services.ProjectService;
import com.example.domain.services.SubprojectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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


  @GetMapping("/createProject")
  public String createProject(Model model){

    Project projectNew = new Project();
    model.addAttribute("projectNew", projectNew);

    return "createproject";
  }


  // method for "Add project" fields and button on "dashboard"
  @PostMapping("/addProject")
  public String saveProject(HttpServletRequest request) throws LoginSampleException {

    //Retrieve request from session
    HttpSession session = request.getSession();

    // Retrieve values from HTML form via WebRequest
    String projectName = request.getParameter("projectName");
    String projectCategory  = request.getParameter("category");

    String hoursTotalStr = request.getParameter("hoursTotal");
    int hoursTotal = Integer.parseInt(hoursTotalStr);

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String projectStartDateStr = request.getParameter("startDate");

    //convert String to LocalDate
    LocalDate projectStartDate = LocalDate.parse(projectStartDateStr, formatter);
    String projectEndDateStr = request.getParameter("endDate");

    //convert String to LocalDate
    LocalDate projectEndDate = LocalDate.parse(projectEndDateStr, formatter);

    // Retrieve "email" String object from HTTP session
    String ownerEmail = (String) session.getAttribute("email");
    String projectDescription = request.getParameter("description");

   /* if (projectName.equals("")) {
      return "redirect:/dashboard";
    } else {*/
      // Make "projectNew" object and assign new values
      Project projectNew = new Project(projectName, projectCategory, hoursTotal, projectStartDate, projectEndDate,
          ownerEmail, projectDescription);


      // Work + data is delegated to login service
      projectService.createProject(projectNew);
      session.setAttribute("projectNew", projectNew);

    ArrayList<Project> projects = new ProjectService().findAllProjectsUser(ownerEmail); // DATABASE-agtig kodning???
    session.setAttribute("projects", projects);


    // Go to page
    return "redirect:/dashboard";
  }

  @GetMapping("/showProject/{projectName}")
  public String showProject(HttpServletRequest request, Model model,
                            @PathVariable(value = "projectName") String projectName) {
    HttpSession session = request.getSession();



    //  Assign model attribute to arraylist med  projects
    ArrayList<Project> projectsCurrentUser = (ArrayList) session.getAttribute("projects");
    model.addAttribute("projectsCurrentUser", projectsCurrentUser);

    Project projectSelected = projectService.showProjectInfo(projectName);
    session.setAttribute("projectSelected", projectSelected);
    model.addAttribute("projectSelected", projectSelected);

    Subproject subprojectNew = new Subproject();
    model.addAttribute("subprojectNew", subprojectNew);


    //____________________________________________________________________
    ArrayList<Subproject> subprojects = new SubprojectService().findAllSubprojectsInProject(projectSelected.getProjectID()); // DATABASE-agtig kodning???

    session.setAttribute("subprojects", subprojects);
    model.addAttribute("subprojects", subprojects);
    //----------------------------------------------------------------------------

    return "showproject";
  }

  @GetMapping("/editProject/{projectName}")
  public String editProject(HttpServletRequest request, Model model,
                            @PathVariable(value = "projectName") String projectName) { //TO DO path in browser
    HttpSession session = request.getSession();
    //  Assign model attribute to arraylist med  projects
    ArrayList<Project> projectsCurrentUser = (ArrayList) session.getAttribute("projects");
    model.addAttribute("projectsCurrentUser",projectsCurrentUser );

    Project projectSelected = (Project) session.getAttribute("projectSelected");
    model.addAttribute("projectSelected", projectSelected);//


    return "dashboardedit";
  }

  @PostMapping("/updateProject")
  public String updateProject(HttpServletRequest request) throws LoginSampleException {
    //Retrieve request from session
    HttpSession session = request.getSession();
    // Retrieve values from HTML form via WebRequest
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

    System.out.println(projectUpdated);

    projectService.updateProject(projectUpdated);


    // Go to page
    return "redirect:/dashboard";   // TO DO, evt return to dashboard select
  }
}
