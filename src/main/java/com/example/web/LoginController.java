package com.example.web;

import com.example.domain.exceptions.LoginRegisterException;
import com.example.domain.models.Project;
import com.example.domain.models.User;
import com.example.domain.services.LoginService;
import com.example.domain.services.ProjectService;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
  public class LoginController {


    private final LoginService loginService = new LoginService();


    // main page "index"
    @GetMapping("/")
    public String index() { return "login/index";
    }


    // "signup" page
    @GetMapping("/signup")
    public String signup() {return "login/signup";}

    // gathering data from login form
    @PostMapping("/login")
    public String loginUser(HttpServletRequest request) throws LoginRegisterException {
      //Retrieve request from session
      HttpSession session = request.getSession();
      //Retrieve values from HTML form via HTTPServletRequest
      String email = request.getParameter("email");
      String password = request.getParameter("password");
      // delegate work + data to login service
      User user = new User(email, password);
      boolean isExists = loginService.checkIfUserExists(user);
      if (isExists) {
        // Set email in session
        session.setAttribute("email", email);

        // Go to next page after login
        return "redirect:/frontpage";
      } else {
        throw new LoginRegisterException("User is not exists, please try again");
      }
    }

    // users main page after login
    @GetMapping("/frontpage")
    public String userPage(Model model, HttpServletRequest request) {
      HttpSession session = request.getSession();
      String email = (String) session.getAttribute("email");

      User userLogged = loginService.findUserByEmail(email);

      // add attribute to session
      session.setAttribute("userLogged", userLogged);

   /* Call arraylist and sort the projects by users email*/
      ArrayList<Project> projectsUserLogged = new ProjectService().findAllProjectsUser(email); // DATABASE-agtig kodning???


      //  Assign model attribute to arraylist med  projects
      model.addAttribute("projectsUserLogged", projectsUserLogged);

      return "login/frontpage";
    }


    // method for "Log out" button
    @GetMapping("/logout")
    public String logoutUser(HttpSession session) {
      session.invalidate();
      return "redirect:/";
    }


    @PostMapping("/register")
    public String createUser(WebRequest request, Model model) throws LoginRegisterException {

      //Retrieve values from HTML form via WebRequest
      String email = request.getParameter("email");
      String password1 = request.getParameter("password1");
      String password2 = request.getParameter("password2");
      String firstName = request.getParameter("firstname");
      String lastName = request.getParameter("lastname");
      String companyName = request.getParameter("companyname");
      String phoneNumber = request.getParameter("phonenumber");

      if (!(password1.equals(password2))) {
        // If passwords don't match, an exception is thrown
        throw new LoginRegisterException ("The two passwords did not match");
      } else {
        // If passwords match, work + data is delegated to login service
        User user = new User(email, password1, firstName, lastName, companyName, phoneNumber);
        if (new LoginService().checkIfUserExistsRegister(user)) {
          throw new LoginRegisterException ("There was already a user with this email..\n Please, choose a different one");
        } else {
          loginService.writeUser(user);
          request.setAttribute("user", user, WebRequest.SCOPE_SESSION);
          model.addAttribute("user", user);
        }
      }
      return "login/index";
    }


  // method for "Profile" link in navigation bar
  @GetMapping("/profile")
  public String showProfile(HttpServletRequest request, Model model) {
    HttpSession session = request.getSession();
    User userLogged = (User) session.getAttribute("userLogged");
    model.addAttribute("userLogged", userLogged);

    return "login/profile";
  }




    @ExceptionHandler(LoginRegisterException.class)
    public String handleError(Model model, Exception exception) {
      model.addAttribute("message", exception.getMessage());
      return "login/exceptionpageLoginRegister";

    }
  }
