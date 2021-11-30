package com.example.web;


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
public class SubprojectController {

  private final SubprojectService subprojectService = new SubprojectService();


  // method for "Add Subproject" fields and button on "dashboardselect"
  @PostMapping("/addSubproject")
  public String saveSubproject(HttpServletRequest request, Model model)  {

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

    session.setAttribute("subprojectNew", subprojectNew);

    //--------------------------------------------------------
    /*String projectName = projectSelected.getProjectName();
    session.setAttribute("projectName", projectName);*/
   /* ArrayList<Subproject> subprojectsCurrentProject = (ArrayList) session.getAttribute("subprojects");
    model.addAttribute("subprojectsCurrentProject", subprojectsCurrentProject);*/
    //--------------------------------------------------------
    // Go to page
    return "redirect:/dashboard";
  }

  /*@GetMapping("/showSubproject/{projectName}")
  public String showProject(HttpServletRequest request, Model model,
                            @PathVariable(value = "projectName") String projectName) {
    HttpSession session = request.getSession();

    //  Assign model attribute to arraylist med  projects
    ArrayList<Subproject> subprojectsCurrentProject = (ArrayList) session.getAttribute("subprojects");
    model.addAttribute("subprojectsCurrentProject", subprojectsCurrentProject);



    Subproject subprojectNew = new Subproject();
    model.addAttribute("subprojectNew", subprojectNew);

    return "redirect:/dashboardselect";
  }*/

}
