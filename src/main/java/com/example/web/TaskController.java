package com.example.web;

import com.example.domain.CustomException;
import com.example.domain.models.Subproject;
import com.example.domain.models.Task;
import com.example.domain.services.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
public class TaskController {

      private final TaskService taskService = new TaskService();


    @GetMapping("/createTask")
    public String createTask(Model model){
     Task taskNew = new Task();
     model.addAttribute("taskNew", taskNew);// fjernes senere???
     return "createtask";
    }


    // method for "Add Task" fields and button on "createtask"
    @PostMapping("/addTask")
    public String saveTask(HttpServletRequest request, RedirectAttributes redirectAttrs)  {
      //Retrieve request from session
      HttpSession session = request.getSession();
      Subproject subprojectSelected = (Subproject) session.getAttribute("subprojectSelected");

      int subprojectID = subprojectSelected.getSubprojectID();

      String taskName = request.getParameter("taskName");
      String hoursTotalStr = request.getParameter("hoursTotal");
      int hoursTotal = Integer.parseInt(hoursTotalStr);

      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

      String subprojectStartDateStr = request.getParameter("startDate");
      //convert String to LocalDate
      LocalDate taskStartDate = LocalDate.parse(subprojectStartDateStr, formatter);

      String subprojectEndDateStr = request.getParameter("endDate");
      //convert String to LocalDate
      LocalDate taskEndDate = LocalDate.parse(subprojectEndDateStr, formatter);

      String taskDescription = request.getParameter("description");
/*
 if (taskName.equals("")) {
      return "redirect:/dashboard";
    } else {
*/
      // Make "taskNew" object and assign new values
      Task taskNew = new Task(subprojectID, taskName, hoursTotal, taskStartDate,
          taskEndDate, taskDescription);

      // Work + data is delegated to login service
      taskService.createTask(taskNew);
      redirectAttrs.addAttribute("subprojectName", subprojectSelected.getSubprojectName());
      // Go to page
      return "redirect:/showSubproject/{subprojectName}";
    }


    @GetMapping("/showTask/{taskName}")
    public String showSubproject(HttpServletRequest request, Model model,
                                 @PathVariable(value = "taskName") String taskName) {
      HttpSession session = request.getSession();

      Task taskSelected = taskService.showTaskInfo(taskName);
      session.setAttribute("taskSelected", taskSelected);
      model.addAttribute("taskSelected", taskSelected);

      return "showtask";
    }



  @GetMapping("/editTask/{taskName}")
  public String editTask(HttpServletRequest request, Model model, @PathVariable(value = "taskName") String taskName) { //TO DO path in browser
    HttpSession session = request.getSession();
    Task taskSelected = taskService.showTaskInfo(taskName);
    session.setAttribute("taskSelected", taskSelected);
    model.addAttribute("taskSelected", taskSelected);
    return "taskedit";
  }

  @PostMapping("/updateTask")
  public String updateSubproject(HttpServletRequest request) throws CustomException {
    //Retrieve request from session

    HttpSession session = request.getSession();

    // Retrieve values from HTML form via WebRequest
    Task taskToUpdate = (Task) session.getAttribute("taskSelected");

    Task taskUpdated = new Task(
        (taskToUpdate.getSubprojectID()),
        (request.getParameter("taskName")),
        (Integer.parseInt(request.getParameter("hoursTotal"))),
        (LocalDate.parse(request.getParameter("startDate"), DateTimeFormatter.ofPattern("yyyy-MM-dd"))),
        (LocalDate.parse(request.getParameter("endDate"), DateTimeFormatter.ofPattern("yyyy-MM-dd"))),
        (request.getParameter("description")));

    taskService.updateTask(taskUpdated);


    // Go to page
    return "redirect:/dashboard";   // TO DO, evt return to dashboard select
  }

    @GetMapping("/deleteTask/{taskName}")
    public String deleteTask(HttpServletRequest request, RedirectAttributes redirectAttrs, @PathVariable(value = "taskName") String taskName) {
      taskService.deleteTask(taskName);

      HttpSession session = request.getSession();
      Subproject subprojectSelected = (Subproject) session.getAttribute("subprojectSelected");

      redirectAttrs.addAttribute("subprojectName", subprojectSelected.getSubprojectName());
      return "redirect:/showSubproject/{subprojectName}";
    }


  @GetMapping("/returnToSubproject")
  public String returnToSubproject(HttpServletRequest request, RedirectAttributes redirectAttrs) {
    HttpSession session = request.getSession();
    Subproject subprojectSelected = (Subproject) session.getAttribute("subprojectSelected");
    redirectAttrs.addAttribute("subprojectName", subprojectSelected.getSubprojectName());
    return "redirect:/showSubproject/{subprojectName}";
  }
}