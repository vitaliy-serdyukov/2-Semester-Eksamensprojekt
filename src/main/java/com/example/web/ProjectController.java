package com.example.web;

import com.example.domain.LoginSampleException;
import com.example.domain.models.Project;
import com.example.domain.services.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;



@Controller
public class ProjectController {

  private ProjectService projectService = new ProjectService();


  // method for "Add project" fields and button on "dashboard"
  @PostMapping("/addProject")
  public String saveWishlist(HttpServletRequest request, Model model) throws LoginSampleException {

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

    // Retrieve "email" String object from HTTP session
    String ownerEmail = (String) session.getAttribute("email");
    String projectDescription = request.getParameter("description");


   /* if (projectName.equals("")) {
      return "redirect:/dashboard";
    } else {*/
      // Make "projectNew" object and assign new values
      Project projectNew = new Project(projectName, projectCategory, hoursTotal, projectStartDate, projectEndDate,
          ownerEmail, projectDescription);
    System.out.println("This is new project" + projectNew);

    session.setAttribute("projectNew", projectNew);
    /*model.addAttribute("projectNew", projectNew);// ????*/

      // Work + data is delegated to login service
      projectService.createProject(projectNew);

    // Go to page
    return "redirect:/dashboard";
  }
}
