package com.example.domain.services;

import com.example.repositories.TeammateRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class CalculatorService {

  private final TeammateRepository teammateRepository = new TeammateRepository();

  public double calculateDaysNeeded(int hoursTotal, int projectID) { // days needed
    double totalHoursTeam = (double) teammateRepository.getHoursTeam(projectID);
    double dayAmountTemp =  hoursTotal / totalHoursTeam;
    return Math.round(dayAmountTemp * 100.0) / 100.0;
  }

  public LocalDate countDateEnd(LocalDate startDate, int hoursTotal, int projectID) {
    double dayAmount = calculateDaysNeeded(hoursTotal,projectID);

    LocalDate dateEnd = startDate;
    int addedDays = 0;
    while (addedDays < dayAmount) {
      dateEnd = dateEnd.plusDays(1);
      if (!(dateEnd.getDayOfWeek() == DayOfWeek.SATURDAY || dateEnd.getDayOfWeek() == DayOfWeek.SUNDAY)) {
        ++addedDays;
      }
    }
    return dateEnd;
  }

  public double countDaysExpected (LocalDate startDate, LocalDate endDate){ // days between 2 dates

    Date date1 = java.sql.Date.valueOf(startDate);
    Date date2 = java.sql.Date.valueOf(endDate);
    Calendar cal1 = Calendar.getInstance();
    Calendar cal2 = Calendar.getInstance();
    cal1.setTime(date1);
    cal2.setTime(date2);

    int numberDaysExpected = 0;

    while (cal1.before(cal2)) {
      if ((Calendar.SATURDAY != cal1.get(Calendar.DAY_OF_WEEK))
          &&(Calendar.SUNDAY != cal1.get(Calendar.DAY_OF_WEEK))) {
        numberDaysExpected++;
      }
      cal1.add(Calendar.DATE,1);
    }
    return numberDaysExpected;

  }

  public boolean isTimeEnough (LocalDate startDate, LocalDate endDate, int hoursTotal, int projectID){
    double numberDaysExpected = countDaysExpected(startDate, endDate);
    double numberDaysNeeded = calculateDaysNeeded(hoursTotal, projectID);
    if (numberDaysNeeded > numberDaysExpected ) {
      return false;
    }
    return true;
  }

  public double calculateSpeedDaily(LocalDate startDate, LocalDate endDate, int hoursTotal){
    double daysExpected = countDaysExpected(startDate, endDate) ;// days between 2 dates
    double speed = hoursTotal / daysExpected; // hoursTotal is an amount of "expected" hours
    return Math.round(speed * 100) / 100.0;
  }
}