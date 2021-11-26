package com.example.domain.models;

import java.time.LocalDate;
import java.util.Date;

public class Project {

  private int projectID;
  private String projectName;
  private String category;
  private int hoursTotal;
  private LocalDate startDate;
  private LocalDate endDate;
  private String ownerEmail;
  private String description;

  public Project(int projectID, String projectName, String category, int hoursTotal, LocalDate startDate, LocalDate endDate, String ownerEmail, String description) {
    this.projectID = projectID;
    this.projectName = projectName;
    this.category = category;
    this.hoursTotal = hoursTotal;
    this.startDate = startDate;
    this.endDate = endDate;
    this.ownerEmail = ownerEmail;
    this.description = description;
  }

  public Project(String projectName, String category, int hoursTotal, LocalDate startDate, LocalDate endDate, String ownerEmail, String description) {
    this.projectName = projectName;
    this.category = category;
    this.hoursTotal = hoursTotal;
    this.startDate = startDate;
    this.endDate = endDate;
    this.ownerEmail = ownerEmail;
    this.description = description;
  }

  public Project() {
  }

  public int getProjectID() {
    return projectID;
  }

  public void setProjectID(int projectID) {
    this.projectID = projectID;
  }

  public String getProjectName() {
    return projectName;
  }

  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
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

  public String getOwnerEmail() {
    return ownerEmail;
  }

  public void setOwnerEmail(String ownerEmail) {
    this.ownerEmail = ownerEmail;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public String toString() {
    return "Project{" +
        "projectID=" + projectID +
        ", projectName='" + projectName + '\'' +
        ", category='" + category + '\'' +
        ", hoursTotal=" + hoursTotal +
        ", startDate='" + startDate + '\'' +
        ", endDate='" + endDate + '\'' +
        ", ownerEmail='" + ownerEmail + '\'' +
        ", description='" + description + '\'' +
        '}';
  }
}
