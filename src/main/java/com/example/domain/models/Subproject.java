package com.example.domain.models;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Subproject {

  private int subprojectID;
  private int projectID; // changes here +  in repo?  private String subprojectName;
  private String subprojectName;
  private int hoursTotal;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate startDate;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate endDate;

  private String description;
  private Project project;
  private ArrayList<Task> tasks;

  public Subproject() {
  }

  public Subproject(int projectID, String subprojectName, int hoursTotal, LocalDate startDate, LocalDate endDate,
                    String description, Project project) {
    this.projectID = projectID;
    this.subprojectName = subprojectName;
    this.hoursTotal = hoursTotal;
    this.startDate = startDate;
    this.endDate = endDate;
    this.description = description;
    this.project = project;
    tasks = new ArrayList<>();
  }

  public Subproject(int projectID, String subprojectName, int hoursTotal, LocalDate startDate, LocalDate endDate,
                    String description){
    this(projectID, subprojectName, hoursTotal, startDate, endDate,description, null);
  }

  public Subproject(int subprojectID, int projectID, String subprojectName, int hoursTotal, LocalDate startDate,
                    LocalDate endDate, String description, Project project) {
    this.subprojectID = subprojectID;
    this.projectID = projectID;
    this.subprojectName = subprojectName;
    this.hoursTotal = hoursTotal;
    this.startDate = startDate;
    this.endDate = endDate;
    this.description = description;
    this.project = project;
    tasks = new ArrayList<>();
  }

  public Subproject(int subprojectID, int projectID, String subprojectName, int hoursTotal, LocalDate startDate, LocalDate endDate,
                    String description){
    this(subprojectID, projectID, subprojectName, hoursTotal, startDate, endDate,description, null);
  }

  public void addProject(Project project) {
    this.project = project;
  }

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public void addTasks(ArrayList<Task> tasks) {
    this.tasks = tasks;
  }

  public ArrayList<Task> getTasks() {
    return tasks;
  }

  public void setTasks(ArrayList<Task> tasks) {
    this.tasks = tasks;
  }

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


  public int getHoursTakenSubproject() {
    int result = 0;

    for(Task task : tasks) {
      result += task.getHoursTotal();
    }
    tasks.clear();
    return result;
  }

  public int getHoursLeftSubproject(){
    int timeLeftSubproject = this.getHoursTotal() - this.getHoursTakenSubproject();
    return timeLeftSubproject;
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
