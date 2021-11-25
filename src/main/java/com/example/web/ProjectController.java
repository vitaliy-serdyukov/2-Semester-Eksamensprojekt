package com.example.web;

import com.example.domain.LoginSampleException;
import com.example.domain.models.Project;
import com.example.domain.services.ProjectService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ProjectController {

  private ProjectService projectService = new ProjectService();


  // method for "Add project" fields and button on "dashboard"
  @PostMapping("/addProject")
  public String saveWishlist(HttpServletRequest request, Model model) throws LoginSampleException {

    //Retrieve request from session
    HttpSession session = request.getSession();

    // Retrieve values from HTML form via WebRequest
    String projectName = request.getParameter("projectName");
    String projectCategory = request.getParameter("projectCategory");
    String hoursTotalStr = request.getParameter("hoursTotal");
    int hoursTotal = Integer.parseInt(hoursTotalStr);
    String projectStartDate = request.getParameter("projectStartDate");
    String projectEndDate = request.getParameter("projectEndDate");
    // Retrieve "email" String object from HTTP session
    String ownerEmail = (String) session.getAttribute("email");
    String projectDescription = request.getParameter("projectEndDate");


   /* if (projectName.equals("")) {
      return "redirect:/dashboard";
    } else {*/
      // Make "projectNew" object and assign new values
      Project projectNew = new Project(projectName, projectCategory, hoursTotal, projectStartDate, projectEndDate,
          ownerEmail, projectDescription);

    session.setAttribute("projectNew", projectNew);
    /*model.addAttribute("projectNew", projectNew);// ????*/

      // Work + data is delegated to login service
      projectService.createProject(projectNew);

    // Go to page
    return "redirect:/dashboard";
  }
}
