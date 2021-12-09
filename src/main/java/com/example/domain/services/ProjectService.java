package com.example.domain.services;

import com.example.domain.LoginSampleException;
import com.example.domain.models.Project;
import com.example.repositories.ProjectRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ProjectService {

  private final ProjectRepository projectRepository = new ProjectRepository();

  public void createProject(Project project) throws LoginSampleException {
    projectRepository.writeProject(project);
  }

  public ArrayList<Project> findAllProjectsUser(String email) {
    return projectRepository.readProjectsUser(email);
  }

  public Project showProjectInfo(String projectName) {
    return projectRepository.readProjectInfo(projectName);
  }

  public void updateProject(Project project) throws LoginSampleException
  {
    projectRepository.rewriteProject(project);
  }

  public void deleteProject(String projectName) { ////hvorfor kræver kaldet på deleteProject at deleteProject er static??
    projectRepository.deleteProjectFromDB(projectName);
  }



  public int calculateTotalHoursPerDay (Project project){
    return projectRepository.getHoursTeam(project.getProjectID());
      }

  public double calculateDaysNeeded(Project project) { // days needed
    double totalHoursTeam = (double) projectRepository.getHoursTeam(project.getProjectID());
    double dayAmountTemp =  project.getHoursTotal() / totalHoursTeam;
    BigDecimal bd = new BigDecimal(dayAmountTemp).setScale(2, RoundingMode.HALF_UP);
    return bd.doubleValue();
  }

  public LocalDate countDateEnd(Project project) {
    double dayAmount = calculateDaysNeeded(project);

    LocalDate dateEnd = project.getStartDate();
    int addedDays = 0;
    while (addedDays < dayAmount) {
      dateEnd = dateEnd.plusDays(1);
      if (!(dateEnd.getDayOfWeek() == DayOfWeek.SATURDAY || dateEnd.getDayOfWeek() == DayOfWeek.SUNDAY)) {
        ++addedDays;
      }
    }
    return dateEnd;
  }

  public double countDaysExpected (Project project){ // days between 2 dates

    Date date1 = java.sql.Date.valueOf(project.getStartDate());
    Date date2 = java.sql.Date.valueOf(project.getEndDate());
    Calendar cal1 = Calendar.getInstance();
    Calendar cal2 = Calendar.getInstance();
    cal1.setTime(date1);
    cal2.setTime(date2);

    int numberDaysExpected = 0;

    while (cal1.before(cal2)) {
      if ((Calendar.SATURDAY != cal1.get(Calendar.DAY_OF_WEEK))
          &&(Calendar.SUNDAY != cal1.get(Calendar.DAY_OF_WEEK))) {
        numberDaysExpected++;
      }
      cal1.add(Calendar.DATE,1);
    }
    return (double) numberDaysExpected;

  }

  public boolean isTimeEnough (Project project){
    double numberDaysExpected = countDaysExpected(project);
    double numberDaysNeeded = calculateDaysNeeded(project);
       if (numberDaysNeeded > numberDaysExpected ) {
      return false;
    }
    return true;
  }

  public double calculateSpeedDaily(Project project){
    double daysExpected = countDaysExpected(project);
    int hoursExpected = project.getHoursTotal();
    double speed =  hoursExpected/daysExpected;
    BigDecimal bd = new BigDecimal(speed).setScale(2, RoundingMode.HALF_UP);
    return bd.doubleValue();
  }
}

