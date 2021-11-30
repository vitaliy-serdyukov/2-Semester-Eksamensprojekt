package com.example.domain.models;


import com.example.domain.services.ProjectService;

import java.util.ArrayList;

public class User {

  private String email;
  private String password;
  private String firstName;
  private String lastName;
  private String companyName;
  private String phoneNumber;
  private ArrayList<Project> userProjects = new ProjectService().findAllProjectsUser(email);// to use List or LinkedList here?

  public User() {
  }

  public User(String email, String password, String firstName, String lastName, String companyName, String phoneNumber, ArrayList<Project> userProjects) {
    this.email = email;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.companyName = companyName;
    this.phoneNumber = phoneNumber;
    this.userProjects = userProjects;
  }

  public User(String email, String password, String firstName, String lastName, String companyName, String phoneNumber) {
    this.email = email;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.companyName = companyName;
    this.phoneNumber = phoneNumber;

  }

  public User(String email, String password) {
    this.email = email;
    this.password = password;
  }

  public ArrayList<Project> getUserProjects() {
    return userProjects;
  }

  public void setUserProjects(ArrayList<Project> userProjects) {
    this.userProjects = userProjects;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  @Override
  public String toString() {
    return "User{" +
        "email='" + email + '\'' +
        ", password='" + password + '\'' +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", companyName='" + companyName + '\'' +
        ", phoneNumber='" + phoneNumber + '\'' +
        ", userProjects=" + userProjects +
        '}';
  }
}
