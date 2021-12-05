package com.example.domain.models;

import java.time.LocalDate;
import java.util.List;

public class Subproject {

  private int subprojectID;
  private int projectID; // changes here +  in repo?  private String subprojectName;
  private String subprojectName;
  private int hoursTotal;
  private LocalDate startDate;
  private LocalDate endDate;
  private String description;
/*  private Project project;*/
/*  private List<Task> tasksOneSubproject;*/

  public Subproject() {
  }

  public Subproject(int projectID, String subprojectName, int hoursTotal, LocalDate startDate, LocalDate endDate, String description) {
    this.projectID = projectID;
    this.subprojectName = subprojectName;
    this.hoursTotal = hoursTotal;
    this.startDate = startDate;
    this.endDate = endDate;
    this.description = description;
  }

  public Subproject(int subprojectID, int projectID, String subprojectName, int hoursTotal, LocalDate startDate, LocalDate endDate, String description) {
    this.subprojectID = subprojectID;
    this.projectID = projectID;
    this.subprojectName = subprojectName;
    this.hoursTotal = hoursTotal;

    this.startDate = startDate;
    this.endDate = endDate;
    this.description = description;
  }



 /* public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public List<Task> getTasksOneSubproject() {
    return tasksOneSubproject;
  }

  public void setTasksOneSubproject(List<Task> tasksOneSubproject) {
    this.tasksOneSubproject = tasksOneSubproject;
  }*/



  public int getSubprojectID() {
    return subprojectID;
  }

  public void setSubprojectID(int subprojectID) {
    this.subprojectID = subprojectID;
  }

  public int getProjectID() {
    return projectID;
  }

  public void setProjectID(int projectID) {
    this.projectID = projectID;
  }

  public String getSubprojectName() {
    return subprojectName;
  }

  public void setSubprojectName(String subprojectName) {
    this.subprojectName = subprojectName;
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
    return "SubProject{" +
        "subProjectID=" + subprojectID +
        ", subProjectName='" + subprojectName + '\'' +
        ", hoursTotal=" + hoursTotal +
        ", startDate='" + startDate + '\'' +
        ", endDate='" + endDate + '\'' +
        ", description='" + description + '\'' +
        '}';
  }
}
