package com.example.domain.models;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;


public class Project {

  private int projectID;
  private String projectName;
  private String category;

  private int hoursTotal;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate startDate;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate endDate;

  private String ownerEmail;
  private String description;
  private User user;
  private ArrayList<Subproject> subprojects;



  public Project(int projectID, String projectName, String category, int hoursTotal, LocalDate startDate,
                 LocalDate endDate, String ownerEmail, String description) {
    this.projectID = projectID;
    this.projectName = projectName;
    this.category = category;
    this.hoursTotal = hoursTotal;
    this.startDate = startDate;
    this.endDate = endDate;
    this.ownerEmail = ownerEmail;
    this.description = description;
    subprojects = new ArrayList<>();
  }

  public Project(String projectName, String category, int hoursTotal, LocalDate startDate, LocalDate endDate,
                 String ownerEmail, String description) {
    this.projectName = projectName;
    this.category = category;
    this.hoursTotal = hoursTotal;
    this.startDate = startDate;
    this.endDate = endDate;
    this.ownerEmail = ownerEmail;
    this.description = description;
    subprojects = new ArrayList<>();
  }

  public Project() {
  }

 /* public void addSubprojects(Subproject subproject) {
    this.subprojects.add(subproject);
  }*/

  public void addSubprojects(ArrayList<Subproject> subprojects) {
    this.subprojects = subprojects;
  }



  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public ArrayList<Subproject> getSubprojects() {
    return subprojects;
  }

  public void setSubprojects(ArrayList<Subproject> subprojects) {
    this.subprojects = subprojects;
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