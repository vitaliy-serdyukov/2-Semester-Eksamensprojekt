package com.example.domain.models;

public class SubProject {

  private int subProjectID;
  private String subProjectName;
  private int hoursTotal;
  private String startDate;
  private String endDate;
  private String description;

  public SubProject(int subProjectID, String subProjectName, int hoursTotal, String startDate, String endDate, String description) {
    this.subProjectID = subProjectID;
    this.subProjectName = subProjectName;
    this.hoursTotal = hoursTotal;
    this.startDate = startDate;
    this.endDate = endDate;
    this.description = description;
  }

  public int getSubProjectID() {
    return subProjectID;
  }

  public void setSubProjectID(int subProjectID) {
    this.subProjectID = subProjectID;
  }

  public String getSubProjectName() {
    return subProjectName;
  }

  public void setSubProjectName(String subProjectName) {
    this.subProjectName = subProjectName;
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
    return "SubProject{" +
        "subProjectID=" + subProjectID +
        ", subProjectName='" + subProjectName + '\'' +
        ", hoursTotal=" + hoursTotal +
        ", startDate='" + startDate + '\'' +
        ", endDate='" + endDate + '\'' +
        ", description='" + description + '\'' +
        '}';
  }
}
