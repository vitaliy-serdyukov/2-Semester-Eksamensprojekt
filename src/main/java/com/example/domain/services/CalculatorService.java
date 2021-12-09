//package com.example.domain.services;
//
//import com.example.domain.models.Project;
//
//import java.time.DayOfWeek;
//import java.time.LocalDate;
//
//public class CalculatorService {
//
//  public int calculateAmountOfDays(Project project, int hrPerPerson, int amountOfPersons) {
//    int dayAmount = project.getHoursTotal() / (hrPerPerson * amountOfPersons);
//    return dayAmount;
//  }
//
//
//  public void countDateEnd(Project project, int dayAmount) {
//
//    LocalDate dateEnd = project.getStartDate();
//    int addedDays = 0;
//    while (addedDays < dayAmount) {
//      dateEnd = dateEnd.plusDays(1);
//      if (!(dateEnd.getDayOfWeek() == DayOfWeek.SATURDAY || dateEnd.getDayOfWeek() == DayOfWeek.SUNDAY)) {
//        ++addedDays;
//      }
//    }
//    project.setEndDate(dateEnd);
//  }
//}
//  public boolean isTimeEnough (Project project, int hrPerPerson, int amountOfPersons){
//
//
//    int hoursAvailable = (project.getEndDate() - project.getEndDate()) * hrPerPerson * amountOfPersons;
//
//  }
//
//
//}
//*/