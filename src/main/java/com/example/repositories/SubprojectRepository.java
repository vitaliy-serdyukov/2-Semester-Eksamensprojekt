package com.example.repositories;


import com.example.domain.LoginSampleException;
import com.example.domain.models.Project;

import com.example.domain.models.Subproject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class SubprojectRepository {

  //For inserting a subproject into the DB
  public void writeSubproject(Subproject subproject) {
    try {
      Connection con = DBManager.getConnection();
      String SQL = "INSERT INTO subprojects (project_id, subproject_name, subproject_hours_total, subproject_start_date," +
          " subproject_end_date, subproject_description)" + " VALUES (?, ?, ?, ?, ?, ?)";
      PreparedStatement ps = con.prepareStatement(SQL);
      ps.setInt(1, subproject.getProjectID());
      ps.setString(2, subproject.getSubprojectName());
      ps.setInt(3, subproject.getHoursTotal());
      ps.setObject(4, subproject.getStartDate());
      ps.setObject(5, subproject.getEndDate());
      ps.setString(6, subproject.getDescription());
      ps.executeUpdate();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }


  public ArrayList<Subproject> readSubprojectsProject(int projectID) {
    ArrayList<Subproject> subprojectsTemp = new ArrayList<>();
    Subproject tmp = null;
    try {
      Connection con = DBManager.getConnection();
      String SQL = "SELECT * FROM subprojects WHERE (project_id='" + projectID + "')";
      PreparedStatement ps = con.prepareStatement(SQL);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        tmp = new Subproject(
            rs.getInt(1),
            rs.getInt(2),
            rs.getString(3),
            rs.getInt(4),
            rs.getObject(5, LocalDate.class),
            rs.getObject(6, LocalDate.class),
            rs.getString(7));
        subprojectsTemp.add(tmp);
      }

    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
    return subprojectsTemp;
  }

  public Subproject readSubprojectInfo(String subprojectName) {
    Subproject tmp = null;
    try {
      Connection con = DBManager.getConnection();
      String SQL = "SELECT * FROM subprojects WHERE (subproject_name='" + subprojectName + "')";
      PreparedStatement ps = con.prepareStatement(SQL);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        tmp = new Subproject(
            rs.getInt(1),
            rs.getInt(2),
            rs.getString(3),
            rs.getInt(4),
            rs.getObject(5, LocalDate.class),
            rs.getObject(6, LocalDate.class),
            rs.getString(7));
      }

    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
    return tmp;
  }

  public void rewriteSubProject(Subproject subProject)  {
    try {
      Connection con = DBManager.getConnection();

      PreparedStatement ps = con.prepareStatement("UPDATE subprojects SET subproject_name = ?, " +
          " subproject_hours_total = ?, subproject_start_date = ?, subproject_end_date = ?, subproject_description = ? WHERE subproject_id = ?");
      ps.setString(1, subProject.getSubprojectName());
      ps.setInt(2, subProject.getHoursTotal());
      ps.setObject(3, subProject.getStartDate());
      ps.setObject(4, subProject.getEndDate());
      ps.setString(5, subProject.getDescription());
      ps.setInt(6, subProject.getSubprojectID());
      ps.executeUpdate();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  public void deleteSubprojectFromDB(String subprojectName) {
    try {
      Connection con = DBManager.getConnection();
      String SQL = "DELETE FROM subprojects WHERE (subproject_name='" + subprojectName + "')";
      PreparedStatement ps = con.prepareStatement(SQL);
      ps.executeUpdate();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }
}
