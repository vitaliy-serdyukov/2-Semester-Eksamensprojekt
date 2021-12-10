package com.example.domain.services;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class DateService {

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  LocalDate today = LocalDate.now();

  public LocalDate getToday() {
    return today;
  }
}
