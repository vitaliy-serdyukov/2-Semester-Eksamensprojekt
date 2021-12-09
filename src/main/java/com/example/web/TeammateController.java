package com.example.web;

import com.example.domain.models.Project;
import com.example.domain.models.Subproject;
import com.example.domain.services.ProjectService;
import com.example.domain.services.SubprojectService;
import com.example.domain.services.TeammateService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
public class TeammateController {

  private final TeammateService teammateService = new TeammateService();

  @GetMapping("/createTeammate/{projectName}")
  public String addTeammate(HttpServletRequest request, Model model,
                            @PathVariable(value = "projectName") String projectName) {

    HttpSession session = request.getSession();
    Project projectSelected = new ProjectService().showProjectInfo(projectName);
    session.setAttribute("projectSelected", projectSelected);
    model.addAttribute("projectSelected", projectSelected);

    return "addteammate";

  }

  @PostMapping("/addTeammate")
  public String addTeammate(HttpServletRequest request, RedirectAttributes redirectAttrs)
                           /* @PathVariable(value = "projectName") String projectName)*/ {



    HttpSession session = request.getSession();
    Project projectSelected = (Project) session.getAttribute("projectSelected");

    int projectID = projectSelected.getProjectID();
    String teammateEmail = request.getParameter("teammateEmail");
    int teammateHours = Integer.parseInt(request.getParameter("teammateHours"));

    teammateService.writeTeammate(projectID, teammateEmail, teammateHours);


    redirectAttrs.addAttribute("projectName", projectSelected.getProjectName());
    // Go to page
    return "redirect:/showProject/{projectName}";
  }

  @GetMapping("/deleteTeammate/{projectName}")
  public String deleteProject(Model model, @PathVariable(value = "projectName") String projectName,
                              HttpServletRequest request) {
    HttpSession session = request.getSession();
    Project projectSelected = (Project) session.getAttribute("projectSelected");

    int projectID = projectSelected.getProjectID();


    String teammateEmail = request.getParameter("teammateEmail");

    model.addAttribute("teammateEmail", teammateEmail);

    teammateService.deleteTeammate(teammateEmail, projectID);
    return "redirect:/dashboard";
  }
}