package com.example.domain.services;

import com.example.repositories.TeammateRepository;

import java.util.ArrayList;

public class TeammateService {

  private final TeammateRepository teammateRepository = new TeammateRepository();

  public void writeTeammate (int projectID, String teammateEmail){
    teammateRepository.noteTeammates(projectID,teammateEmail);
  }

  public ArrayList<String> readTeammates(int projectID){
    return teammateRepository.readTeammatesProject(projectID);
  }

}
