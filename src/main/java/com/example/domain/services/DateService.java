package com.example.domain.services;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class DateService {

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private final LocalDate today = LocalDate.now();

  public LocalDate getToday() {
    return today;
  }

  public boolean isValidEndDate (LocalDate startDate, LocalDate endDate) {
    if (endDate.isAfter(startDate)){
      return true;
    }
    return false;
  }


  
}

