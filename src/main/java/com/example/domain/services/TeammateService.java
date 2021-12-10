package com.example.domain.services;

import com.example.domain.models.Project;
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
<<<<<<< HEAD
<<<<<<< HEAD
=======

>>>>>>> 8c24c095e0cf18d0025729cffecb16e14e9befea



=======
>>>>>>> VitaliysBranch
  public  int countTeammates (int projectID){
     return teammateRepository.countTeammates(projectID);
  }

  public int calculateTotalHoursPerDay(int projectID) {
    return teammateRepository.getHoursTeam(projectID);
  }
}

