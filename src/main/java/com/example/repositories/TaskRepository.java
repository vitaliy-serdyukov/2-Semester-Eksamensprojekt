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
      String SQL = "SELECT * FROM tasks WHERE (subproject_id='" + subprojectID + "')";
      PreparedStatement ps = con.prepareStatement(SQL);
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
      String SQL = "SELECT * FROM tasks WHERE (task_name='" + taskName + "')";
      PreparedStatement ps = con.prepareStatement(SQL);
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

  public void deleteTaskFromDB(String taskName) {
    try {
      Connection con = DBManager.getConnection();
      String SQL = "DELETE FROM tasks WHERE (task_name='" + taskName + "')";
      PreparedStatement ps = con.prepareStatement(SQL);
      ps.executeUpdate();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }
}