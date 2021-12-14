package com.example.web;

import com.example.domain.exceptions.ProjectInputException;
import com.example.domain.models.Project;
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

  @GetMapping("/createTeammate")
  public String addTeammate(HttpServletRequest request, Model model/*,
                            @PathVariable(value = "projectName") String projectName*/) {

    HttpSession session = request.getSession();
  /*  Project projectSelected = new ProjectService().showProjectInfo(projectName);*/
    Project projectSelected = (Project) session.getAttribute("projectSelected");
    session.setAttribute("projectSelected", projectSelected);
    model.addAttribute("projectSelected", projectSelected);

    return "teammate/addteammate";

  }

  @PostMapping("/addTeammate")

  public String addTeammate(HttpServletRequest request, RedirectAttributes redirectAttrs) throws ProjectInputException
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

  @PostMapping("/deleteTeammate{projectName}")
  public String deleteProject(Model model, @PathVariable(value = "projectName") String projectName,
                              HttpServletRequest request, RedirectAttributes redirectAttrs) {

    HttpSession session = request.getSession();
    Project projectSelected = (Project) session.getAttribute("projectSelected");

    String teammateEmail = request.getParameter("teammateEmail");
    int projectID = projectSelected.getProjectID();

    teammateService.deleteTeammate(teammateEmail, projectID);

    redirectAttrs.addAttribute("projectName", projectSelected.getProjectName());
    return "redirect:/showProject/{projectName}";
    }

}