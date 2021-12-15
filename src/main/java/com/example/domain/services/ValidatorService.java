package com.example.domain.services;


import java.time.LocalDate;

public class ValidatorService {

  public boolean isValidName(String name) {
    // validate name for backspace or empty String input
    if (name == null || name.trim().equals("")) {
      return false;
    }
    return true;
  }

  public boolean isValidEndDate (LocalDate startDate, LocalDate endDate) {


    if (endDate.isAfter(startDate)){
      return true;
    }
    return false;
  }

  public boolean isValidStartDateProject(LocalDate startDate){
    LocalDate today = LocalDate.now();
    if (startDate.isBefore(today)){
      return false;
    }
    return true;
  }

  public boolean isValidStartDateSubproject(LocalDate projectStartDate, LocalDate supbprogectStartDate ){
    if (supbprogectStartDate.isBefore(projectStartDate)){
      return false;
    }
    return true;
  }
}