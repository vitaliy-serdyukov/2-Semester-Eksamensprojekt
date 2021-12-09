package com.example.domain.services;

import com.example.repositories.TeammateRepository;

import java.util.ArrayList;

public class TeammateService {

  private final TeammateRepository teammateRepository = new TeammateRepository();

  public void writeTeammate (int projectID, String teammateEmail, int teammateHours){
    teammateRepository.noteTeammates(projectID,teammateEmail,teammateHours);
  }

  public ArrayList<String> readTeammates(int projectID){
    return teammateRepository.readTeammatesProject(projectID);
  }


  public void deleteTeammate(String teammateEmail, int projectID) {
    teammateRepository.removeTeammate(teammateEmail, projectID);
    }



  public  int countTeammates (int projectID){
     return teammateRepository.countTeammates(projectID);
  }
}

