package com.example.web;

import com.example.domain.exceptions.*;
import com.example.domain.models.Subproject;
import com.example.domain.models.Task;
import com.example.domain.services.CalculatorService;
import com.example.domain.services.TaskService;
import com.example.domain.services.TeammateService;
import com.example.domain.services.ValidatorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
    public String createTask(HttpServletRequest request, Model model){

    Task taskNew = new Task();
    model.addAttribute("taskNew", taskNew);

    HttpSession session = request.getSession();
    Subproject subprojectSelected = (Subproject) session.getAttribute("subprojectSelected");

    LocalDate minStartDateTask = subprojectSelected.getStartDate();
    model.addAttribute("minStartDateTask", minStartDateTask);

    LocalDate maxEndDateTask = subprojectSelected.getEndDate();
    model.addAttribute("maxEndDateTask", maxEndDateTask);

    int hoursLeftSubproject = subprojectSelected.getHoursLeftSubproject();
    model.addAttribute("hoursLeftSubproject", hoursLeftSubproject);

    return "task/createtask";
    }


    // method for "Add Task" fields and button on "createtask"
    @PostMapping("/addTask")
    public String saveTask(HttpServletRequest request, RedirectAttributes redirectAttrs) throws TaskInputException {
      //Retrieve request from session
      HttpSession session = request.getSession();
      Subproject subprojectSelected = (Subproject) session.getAttribute("subprojectSelected");

      int subprojectID = subprojectSelected.getSubprojectID();

      String taskName = request.getParameter("taskName");

      // validate task name for backspace or empty String input
      if (!new ValidatorService().isValidName(taskName)){
        throw new TaskInputException("The task name cannot be empty, please, try again");
      }

      String hoursTotalStr = request.getParameter("hoursTotal");
      int hoursTotal = Integer.parseInt(hoursTotalStr);

      int hoursLeftSubproject = subprojectSelected.getHoursLeftSubproject();
        if (hoursLeftSubproject < hoursTotal) {
        throw new TaskInputException("It was unable to create a task, because the entered amount of hours" +
            " exceeds available amount of hours in project left ");
      }

      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

      String subprojectStartDateStr = request.getParameter("startDate");
      //convert String to LocalDate
      LocalDate taskStartDate = LocalDate.parse(subprojectStartDateStr, formatter);

      String subprojectEndDateStr = request.getParameter("endDate");
      //convert String to LocalDate
      LocalDate taskEndDate = LocalDate.parse(subprojectEndDateStr, formatter);

      // validate end date
      if (!new ValidatorService().isValidEndDate(taskStartDate, taskEndDate)){
        throw new TaskInputException("Entered end date is wrong, please, choose end date as minimum" +
            "as the next day after start date");
      }

      String taskDescription = request.getParameter("description");

      // Make "taskNew" object and assign new values
      Task taskNew = new Task(subprojectID, taskName, hoursTotal, taskStartDate,
          taskEndDate, taskDescription);

      // Work + data is delegated to task service
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
      return "task/showtask";
    }



  @GetMapping("/editTask/{taskName}")
  public String editTask(HttpServletRequest request, Model model, @PathVariable(value = "taskName") String taskName)
      throws TaskUpdateException {
    HttpSession session = request.getSession();
    TeammateService teammateService = new TeammateService();
    CalculatorService calculatorService = new CalculatorService();

    Subproject subprojectSelected = (Subproject) session.getAttribute("subprojectSelected");
    Task taskSelected = (Task) session.getAttribute("taskSelected");
    model.addAttribute("taskSelected", taskSelected);

    int teammatesAmount = teammateService.countTeammates(subprojectSelected.getProjectID());
    model.addAttribute("teammatesAmount", teammatesAmount);

    if (teammatesAmount < 1){
      throw new TaskUpdateException("The project must have at least one team member");
    }

    int totalHoursTeam = teammateService.calculateTotalHoursPerDay(subprojectSelected.getProjectID());
    model.addAttribute("totalHoursTeam", totalHoursTeam);

    double dayAmountNeeded = calculatorService.calculateDaysNeeded(taskSelected.getHoursTotal(),
        subprojectSelected.getProjectID());
    model.addAttribute("dayAmountNeeded", dayAmountNeeded);

    double dayAmountExpected = calculatorService.countDaysExpected(taskSelected.getStartDate(),
        taskSelected.getEndDate());
    model.addAttribute("dayAmountExpected", dayAmountExpected);

    LocalDate realEndDate = calculatorService.countDateEnd(taskSelected.getStartDate(),
        taskSelected.getHoursTotal(), subprojectSelected.getProjectID());
    model.addAttribute("realEndDate", realEndDate);

    boolean isEnough = calculatorService.isTimeEnough(taskSelected.getStartDate(), taskSelected.getEndDate(),
        taskSelected.getHoursTotal(), subprojectSelected.getProjectID());
    model.addAttribute("isEnough", isEnough);

    double neededSpeed = calculatorService.calculateSpeedDaily(taskSelected.getStartDate(), taskSelected.getEndDate(),
        taskSelected.getHoursTotal());
    model.addAttribute("neededSpeed", neededSpeed);

    return "task/edittask";
  }

  @PostMapping("/updateTask")
  public String updateSubproject(HttpServletRequest request, RedirectAttributes redirectAttrs)
      throws TaskUpdateException {

    //Retrieve request from session
    HttpSession session = request.getSession();

    // Retrieve values from HTML form via HTTPServlet request
    Subproject subprojectSelected = (Subproject) session.getAttribute("subprojectSelected");
    Task taskSelected = (Task) session.getAttribute("taskSelected");

    Task taskUpdated = new Task(
        (taskSelected.getSubprojectID()),
        (request.getParameter("taskName")),
        (Integer.parseInt(request.getParameter("hoursTotal"))),
        (LocalDate.parse(request.getParameter("startDate"), DateTimeFormatter.ofPattern("yyyy-MM-dd"))),
        (LocalDate.parse(request.getParameter("endDate"), DateTimeFormatter.ofPattern("yyyy-MM-dd"))),
        (request.getParameter("description")));

    // validate task name for backspace or empty String input and validate end date
    ValidatorService validatorService = new ValidatorService();
    if (!validatorService.isValidStartDateTask(subprojectSelected.getStartDate(), taskUpdated.getStartDate())||
        !validatorService.isValidName(taskUpdated.getTaskName()) ||
        !validatorService.isValidEndDate(taskUpdated.getStartDate(), taskUpdated.getEndDate())){
      throw new TaskUpdateException("Either task name or end date is wrong..." +
          "Name may not be empty and the end date has to be as minimum as the next day after start date." +
          "Please, try again");
    }

    taskService.updateTask(taskUpdated, taskSelected.getTaskName());
    taskSelected = taskUpdated;
    session.setAttribute("taskSelected",  taskSelected);


    redirectAttrs.addAttribute("taskName", taskUpdated.getTaskName());
    return "redirect:/showTask/{taskName}";
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

  @GetMapping("/returnToTask")
  public String returnToProject(HttpServletRequest request, RedirectAttributes redirectAttrs) {
    HttpSession session = request.getSession();
    Task taskSelected = (Task) session.getAttribute("taskSelected");
    redirectAttrs.addAttribute("taskName", taskSelected.getTaskName());
    return "redirect:/showTask/{taskName}";
  }


  @ExceptionHandler(TaskInputException.class)
  public String handleInputError(Model model, Exception exception) {
    model.addAttribute("message", exception.getMessage());
    return "task/taskInputExceptionPage";
  }

  @ExceptionHandler(TaskUpdateException.class)
  public String handleUpdateError(Model model, Exception exception) {
    model.addAttribute("message", exception.getMessage());
    return "task/taskUpdateExceptionPage";
  }
}