package com.example.repositories;

import com.example.domain.models.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class TaskRepository {

  //For inserting a task into the DB
  public void writeTask(Task task) {
    try {
      Connection con = DBManager.getConnection();
      String SQL = "INSERT INTO tasks (subproject_id, task_name, task_hours_total, task_start_date," +
          " task_end_date, task_description)" + " VALUES (?, ?, ?, ?, ?, ?)";
      PreparedStatement ps = con.prepareStatement(SQL);
      ps.setInt(1, task.getSubprojectID());
      ps.setString(2, task.getTaskName());
      ps.setInt(3, task.getHoursTotal());
      ps.setObject(4, task.getStartDate());
      ps.setObject(5, task.getEndDate());
      ps.setString(6, task.getDescription());
      ps.executeUpdate();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  public ArrayList<Task> readTasksSubproject(int subprojectID) {
    ArrayList<Task> tasksTemp = new ArrayList<>();
    Task tmp = null;
    try {
      Connection con = DBManager.getConnection();
      String SQL = "SELECT * FROM tasks WHERE subproject_id = ?";
      PreparedStatement ps = con.prepareStatement(SQL);
      ps.setInt(1, subprojectID);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        tmp = new Task(
            rs.getInt(1),
            rs.getString(2),
            rs.getInt(3),
            rs.getObject(4, LocalDate.class),
            rs.getObject(5, LocalDate.class),
            rs.getString(6));
        tasksTemp.add(tmp);
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
    return tasksTemp;
  }

  public Task readTaskInfo(String taskName) {
    Task tmp = null;
    try {
      Connection con = DBManager.getConnection();
      String SQL = "SELECT * FROM tasks WHERE task_name = ?";
      PreparedStatement ps = con.prepareStatement(SQL);
      ps.setString(1, taskName);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        tmp = new Task(
            rs.getInt(1),
            rs.getString(2),
            rs.getInt(3),
            rs.getObject(4, LocalDate.class),
            rs.getObject(5, LocalDate.class),
            rs.getString(6));
      }
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
    return tmp;
  }

  public void rewriteTask(Task taskUpdated, String oldTaskName) {
    try {
      Connection con = DBManager.getConnection();

      PreparedStatement ps = con.prepareStatement("UPDATE tasks SET task_name = ?, " +
          " task_hours_total = ?, task_start_date = ?, task_end_date= ?, task_description = ?" +
          " WHERE task_name = ? AND subproject_id = ?");
      ps.setString(1, taskUpdated.getTaskName());
      ps.setInt(2, taskUpdated.getHoursTotal());
      ps.setObject(3, taskUpdated.getStartDate());
      ps.setObject(4, taskUpdated.getEndDate());
      ps.setString(5, taskUpdated.getDescription());
      ps.setString(6, oldTaskName); // ???
      ps.setInt(7,taskUpdated.getSubprojectID());
      ps.executeUpdate();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  public void deleteTaskFromDB(String taskName) {
    try {
      Connection con = DBManager.getConnection();
      String SQL = "DELETE FROM tasks WHERE task_name = ?";
      PreparedStatement ps = con.prepareStatement(SQL);
      ps.setString(1, taskName);
      ps.executeUpdate();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }
}