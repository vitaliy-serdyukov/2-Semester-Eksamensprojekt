package com.example.domain.services;

import com.example.domain.LoginSampleException;
import com.example.domain.models.Project;
import com.example.repositories.ProjectRepository;

public class ProjectService {

  private ProjectRepository projectRepository = new ProjectRepository();

  public void createProject(Project project) throws LoginSampleException {
    projectRepository.writeProject(project);
  }
}
