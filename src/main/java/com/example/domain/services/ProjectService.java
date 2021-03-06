package com.example.domain.services;

import com.example.domain.exceptions.ProjectInputException;
import com.example.domain.models.Project;
import com.example.repositories.ProjectRepository;

import java.util.ArrayList;

public class ProjectService {

  private final ProjectRepository projectRepository = new ProjectRepository();

  public void createProject(Project project) throws ProjectInputException {
    projectRepository.writeProject(project);
  }

  public ArrayList<Project> findAllProjectsUser(String email) {
    return projectRepository.readProjectsUser(email);
  }

  public Project showProjectInfo(String projectName) {
    return projectRepository.readProjectInfo(projectName);
  }

  public void updateProject(Project project)  {
    projectRepository.rewriteProject(project);
  }

  public void deleteProject(String projectName) { ////hvorfor kræver kaldet på deleteProject at deleteProject er static??
    projectRepository.deleteProjectFromDB(projectName);
  }
}