package com.example.web;


import com.example.domain.models.Project;
import com.example.domain.models.Subproject;
import com.example.domain.models.Task;
import com.example.domain.services.ProjectService;
import com.example.domain.services.SubprojectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
  public String createSubroject(Model model){

    Subproject subprojectNew = new Subproject();
    model.addAttribute("subprojectNew", subprojectNew);

    return "createsubproject";
  }


  // method for "Add Subproject" fields and button on "dashboardselect"
  @PostMapping("/addSubproject")
  public String saveSubproject(HttpServletRequest request, RedirectAttributes redirectAttrs, Model model)  {

    //Retrieve request from session
    HttpSession session = request.getSession();
    Project projectSelected = (Project) session.getAttribute("projectSelected");

    int projectID = projectSelected.getProjectID();

    String subprojectName = request.getParameter("subprojectName");
    String hoursTotalStr = request.getParameter("hoursTotal");
    int hoursTotal = Integer.parseInt(hoursTotalStr);

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    String subprojectStartDateStr = request.getParameter("startDate");
    //convert String to LocalDate
    LocalDate subprojectStartDate = LocalDate.parse(subprojectStartDateStr, formatter);

    String subprojectEndDateStr = request.getParameter("endDate");
    //convert String to LocalDate
    LocalDate subprojectEndDate = LocalDate.parse(subprojectEndDateStr, formatter);

    String projectDescription = request.getParameter("description");
/*
 if (subprojectName.equals("")) {
      return "redirect:/dashboard";
    } else {
*/
    // Make "subprojectNew" object and assign new values
    Subproject subprojectNew = new Subproject(projectID, subprojectName, hoursTotal, subprojectStartDate,
        subprojectEndDate, projectDescription);

    // Work + data is delegated to login service
    subprojectService.createSubproject(subprojectNew);
    redirectAttrs.addAttribute("projectName", projectSelected.getProjectName()).
        addFlashAttribute("message", "Redirecting til project page...");
    // Go to page
    return "redirect:/showProject/{projectName}";
  }


  @GetMapping("/showSubproject/{subprojectName}")
  public String showSubproject(HttpServletRequest request, Model model,
                            @PathVariable(value = "subprojectName") String subprojectName) {
    HttpSession session = request.getSession();


    //  Assign model attribute to arraylist med  projects
  /*  ArrayList<Subproject> subprojectsCurrentProject = (ArrayList) session.getAttribute("subprojects");
    model.addAttribute("subprojectsCurrentProject", subprojectsCurrentProject);*/


    Subproject subprojectSelected = subprojectService.showSubprojectInfo(subprojectName);
    session.setAttribute("subprojectSelected", subprojectSelected);
    model.addAttribute("subprojectSelected", subprojectSelected);


   /* Task taskNew = new Task();
    model.addAttribute("taskNew", taskNew);*/

    // tasks
   /* ArrayList<Subproject> subprojects = new SubprojectService().findAllSubprojectsInProject(projectSelected.getProjectID()); // DATABASE-agtig kodning???

    session.setAttribute("subprojects", subprojects);
    model.addAttribute("subprojects", subprojects);*/


    return "showsubproject";
  }
}
