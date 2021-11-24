package com.example.domain.models;

public class Task {

  private String taskName;
  private int hoursTotal;
  private String startDate;
  private String endDate;
  private String description;

  public Task(String taskName, int hoursTotal, String startDate, String endDate, String description) {
    this.taskName = taskName;
    this.hoursTotal = hoursTotal;
    this.startDate = startDate;
    this.endDate = endDate;
    this.description = description;
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

  public String getStartDate() {
    return startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public String getEndDate() {
    return endDate;
  }

  public void setEndDate(String endDate) {
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
        "taskName='" + taskName + '\'' +
        ", hoursTotal=" + hoursTotal +
        ", startDate='" + startDate + '\'' +
        ", endDate='" + endDate + '\'' +
        ", description='" + description + '\'' +
        '}';
  }
}
