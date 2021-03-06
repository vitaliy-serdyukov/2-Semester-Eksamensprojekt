package com.example.domain.services;

import com.example.domain.models.Subproject;
import com.example.repositories.SubprojectRepository;

import java.io.EOFException;
import java.util.ArrayList;

public class SubprojectService {

  private final SubprojectRepository subprojectRepository = new SubprojectRepository();

  public void createSubproject(Subproject subproject) {
    subprojectRepository.writeSubproject(subproject);
  }

  public ArrayList<Subproject> findAllSubprojectsInProject(int projectID) {
    return subprojectRepository.readSubprojectsProject(projectID);
  }

  public Subproject showSubprojectInfo(String subprojectName) {
    return subprojectRepository.readSubprojectInfo(subprojectName);
  }

  public void updateSubproject(Subproject subProject)  {
    subprojectRepository.rewriteSubProject(subProject);
  }

  public void deleteSubproject(String subprojectName) { ////hvorfor kræver kaldet på deleteProject at deleteProject er static??
    subprojectRepository.deleteSubprojectFromDB(subprojectName);
  }
}
