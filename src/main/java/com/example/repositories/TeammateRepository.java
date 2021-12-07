package com.example.repositories;

import com.example.domain.models.Project;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class TeammateRepository {

  public void noteTeammates (int projectID, String teammateEmail){
    System.out.println(projectID);
    System.out.println(teammateEmail);
    try {
      Connection con = DBManager.getConnection();
      String SQL = "INSERT INTO teammates (project_id, member_email) VALUES (?, ?)";
      PreparedStatement ps = con.prepareStatement(SQL);
      ps.setInt(1, projectID);
      ps.setString(2, teammateEmail);
      ps.executeUpdate();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  public ArrayList<String> readTeammatesProject(int projectID) {
    ArrayList<String> teammatesTemp = new ArrayList<>();
    String tmp = null;
    try {
      Connection con = DBManager.getConnection();
      String SQL = "SELECT * FROM teammates WHERE (project_id='" + projectID + "')";
      PreparedStatement ps = con.prepareStatement(SQL);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        tmp = (rs.getString(2));
        teammatesTemp.add(tmp);
      }

    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
    return teammatesTemp;
  }

}
