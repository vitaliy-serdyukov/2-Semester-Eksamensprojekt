package com.example.repositories;

import com.example.domain.LoginSampleException;
import com.example.domain.models.Project;
import com.example.domain.models.User;

import java.sql.*;

public class ProjectRepository {


  //For inserting a project into the DB.
  public void writeProject (Project project) throws LoginSampleException {
    try {
      Connection con = DBManager.getConnection();
      String SQL = "INSERT INTO projects (project_name, category, project_hours_total, project_start_date," +
          " project_end_date, owner_email, description)" +
          " VALUES (?, ?, ?, ?, ?, ?, ?)";
      PreparedStatement ps = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
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

  //For reading a project from DB
  public ResultSet dbRead() {
    ResultSet resSet = null;
    String select = "/*SELECT email, password, first_name, last_name, age, address, phone_number FROM users*/";
    try {
      PreparedStatement ps = DBManager.getConnection().prepareStatement(select);
      resSet = ps.executeQuery();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return resSet;
  }

}
