package com.example.domain.models;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Task {

  private int subprojectID;
  private String taskName;
  private int hoursTotal;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate startDate;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate endDate;

  private String description;


  public Task(int subprojectID, String taskName, int hoursTotal, LocalDate startDate, LocalDate endDate, String description) {
    this.subprojectID = subprojectID;
    this.taskName = taskName;
    this.hoursTotal = hoursTotal;
    this.startDate = startDate;
    this.endDate = endDate;
    this.description = description;
  }

  public Task() {
  }

  public Task(String taskName, int hoursTotal, LocalDate startDate, LocalDate endDate, String description) {
    this.taskName = taskName;
    this.hoursTotal = hoursTotal;
    this.startDate = startDate;
    this.endDate = endDate;
    this.description = description;
  }

  public int getSubprojectID() {
    return subprojectID;
  }

  public void setSubprojectID(int subprojectID) {
    this.subprojectID = subprojectID;
  }

  public String getTaskName() {
    return taskName;
  }

  public void setTaskName(String taskName) {
    this.taskName = taskName;
  }

  public int getHoursTotal() {
    return hoursTotal;
  }

  public void setHoursTotal(int hoursTotal) {
    this.hoursTotal = hoursTotal;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public String toString() {
    return "Task{" +
        "subprojectID=" + subprojectID +
        ", taskName='" + taskName + '\'' +
        ", hoursTotal=" + hoursTotal +
        ", startDate='" + startDate + '\'' +
        ", endDate='" + endDate + '\'' +
        ", description='" + description + '\'' +
        '}';
  }
}
