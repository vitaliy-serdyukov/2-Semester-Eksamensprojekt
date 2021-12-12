package com.example.repositories;

import com.example.domain.CustomException;
import com.example.domain.models.Project;


import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ProjectRepository {


  //For inserting a project into the DB.
  public void writeProject(Project project) throws CustomException {
    try {
      Connection con = DBManager.getConnection();
      String SQL = "INSERT INTO projects (project_name, category, project_hours_total, project_start_date," +
          " project_end_date, owner_email, description)" +
          " VALUES (?, ?, ?, ?, ?, ?, ?)";
      PreparedStatement ps = con.prepareStatement(SQL);
      ps.setString(1, project.getProjectName());
      ps.setString(2, project.getCategory());
      ps.setInt(3, project.getHoursTotal());
      ps.setObject(4, project.getStartDate());
      ps.setObject(5, project.getEndDate());
      ps.setString(6, project.getOwnerEmail());
      ps.setString(7, project.getDescription());
      ps.executeUpdate();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
   }


  public ArrayList<Project> readProjectsUser(String email) {
    ArrayList<Project> projectsTemp = new ArrayList<>();
    Project tmp = null;
    try {
      Connection con = DBManager.getConnection();
      String SQL = "SELECT * FROM projects WHERE owner_email = ?";
      PreparedStatement ps = con.prepareStatement(SQL);
      ps.setString(1, email);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        tmp = new Project(rs.getInt(1),
            rs.getString(2),
            rs.getString(3),
            rs.getInt(4),
            rs.getObject(5, LocalDate.class),
            rs.getObject(6, LocalDate.class),
            rs.getString(7),
            rs.getString(8));
        projectsTemp.add(tmp);
      }

    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
    return projectsTemp;
  }


  public Project readProjectInfo(String projectName) {
    Project tmp = null;
    try {
      Connection con = DBManager.getConnection();
      String SQL = "SELECT * FROM projects WHERE project_name= ?";
      PreparedStatement ps = con.prepareStatement(SQL);
      ps.setString(1, projectName);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        tmp = new Project(
            rs.getInt(1),
            rs.getString(2),
            rs.getString(3),
            rs.getInt(4),
            rs.getObject(5, LocalDate.class),
            rs.getObject(6, LocalDate.class),
            rs.getString(7),
            rs.getString(8));
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
    return tmp;
  }


  public void rewriteProject(Project project) {

    try {
      Connection con = DBManager.getConnection();

      PreparedStatement ps = con.prepareStatement("UPDATE projects SET project_name = ?, category = ?," +
          " project_hours_total = ?, project_start_date = ?, project_end_date = ?, description = ? WHERE project_id = ?");
      ps.setString(1, project.getProjectName());
      ps.setObject(2, project.getCategory());
      ps.setInt(3, project.getHoursTotal());
      ps.setObject(4, project.getStartDate());
      ps.setObject(5, project.getEndDate());
      ps.setString(6, project.getDescription());
      ps.setInt(7, project.getProjectID());
      ps.executeUpdate();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  public void deleteProjectFromDB(String projectName) {
    try {
      Connection con = DBManager.getConnection();
      String SQL = "DELETE FROM projects WHERE project_name = ?";
      PreparedStatement ps = con.prepareStatement(SQL);
      ps.setString(1, projectName);
      ps.executeUpdate();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }
}