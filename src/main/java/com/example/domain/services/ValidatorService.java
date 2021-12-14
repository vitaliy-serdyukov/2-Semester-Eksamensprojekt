package com.example.domain.services;



public class ValidatorService {

  public boolean isValidName(String name) {
    // validate name for backspace or empty String input
    if (name == null || name.trim().equals("")) {
      return false;
    }
    return true;
  }
}

