package com.example.repositories;

import java.sql.*;
import java.util.ArrayList;

public class TeammateRepository {

  // write teammates to DB
  public void noteTeammates (int projectID, String teammateEmail, int teammateHours){
    try {
      Connection con = DBManager.getConnection();
      String SQL = "INSERT INTO teammates (project_id, member_email, teammate_hours) VALUES (?,?,?)";
      PreparedStatement ps = con.prepareStatement(SQL);
      ps.setInt(1, projectID);
      ps.setString(2, teammateEmail);
      ps.setInt(3, teammateHours);
      ps.executeUpdate();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  // read teammates in project
  public ArrayList<String> readTeammatesProject(int projectID) {
    ArrayList<String> teammatesTemp = new ArrayList<>();
    String tmp = null;
    try {
      Connection con = DBManager.getConnection();
      String SQL = "SELECT * FROM teammates WHERE project_id = ?";
      PreparedStatement ps = con.prepareStatement(SQL);
      ps.setInt(1,projectID);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        tmp = (rs.getString(2));
        teammatesTemp.add(tmp);
      }
    } catch (SQLException | NumberFormatException ex ) {
      System.out.println(ex.getMessage());
    }
    return teammatesTemp;
  }

  // remove a teammate
  public void removeTeammate(String teammateEmail, int projectID) {
    try {
      Connection con = DBManager.getConnection();
      String SQL = "DELETE FROM teammates WHERE member_email = ? AND project_id = ?";
      PreparedStatement ps = con.prepareStatement(SQL);
      ps.setString(1, teammateEmail);
      ps.setInt(2,projectID);
      ps.executeUpdate();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  // count teammates in project
  public int countTeammates (int projectID){
   int amount;
    try {
      Connection con = DBManager.getConnection();
      String SQL = "SELECT COUNT(*) AS 'Number of teammates' FROM teammates WHERE project_id = ?";
      PreparedStatement ps = con.prepareStatement(SQL);
      ps.setInt(1,projectID);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        amount = rs.getInt(1);
        return amount;
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
     return 0;
  }

  // count sum of hours of teammates in project
  public int getHoursTeam(int projectID){
    int hoursTotal;
    try {
      Connection con = DBManager.getConnection();
      String SQL = "SELECT SUM(teammate_hours) FROM teammates WHERE project_id = ?";
      PreparedStatement ps = con.prepareStatement(SQL);
      ps.setInt(1,projectID);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        hoursTotal = rs.getInt(1);
        return hoursTotal;
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    return 0;
  }
}