package com.example.domain.services;

import com.example.domain.models.Task;
import com.example.repositories.TaskRepository;

import java.util.ArrayList;

public class TaskService {

  private final TaskRepository taskRepository = new TaskRepository();

  public void createTask(Task task)  {
    taskRepository.writeTask(task);
  }

  public ArrayList<Task> findAllTasksInSubproject(int subprojectID) {
    return taskRepository.readTasksSubproject(subprojectID);
  }

  public Task showTaskInfo(String taskName) {
    return taskRepository.readTaskInfo(taskName);
  }

  public void deleteTask(String taskName) { ////hvorfor kræver kaldet på deleteProject at deleteProject er static??
    taskRepository.deleteTaskFromDB(taskName);
  }

  public void updateTask(Task taskUpdated, String oldTaskName) {
    taskRepository.rewriteTask(taskUpdated, oldTaskName);
  }
}
