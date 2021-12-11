package repositories;
import com.example.domain.services.CalculatorService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.DayOfWeek;
import java.time.LocalDate;
import static org.junit.Assert.assertTrue;


public class CalculationTest {

    @DisplayName("calculate enough time for project")
    @Test
    public boolean enough_time() {
        //double numberDaysExpected = countDaysExpected(startDate, endDate);
        //double numberDaysNeeded = calculateDaysNeeded(hoursTotal, projectID);
        //Arrange
        CalculatorService calculatorService = new CalculatorService();
        calculatorService.isTimeEnough(LocalDate.of(2022, 1, 1), LocalDate.of(2022, 1, 20), 30, 1);
        //Act
       // assertTrue(numberDaysNeeded > numberDaysExpected);
        System.out.println("not enough days");

        return false;
    }


    @DisplayName("Calculate end date for project")
    @Test
    public void count_endDate(LocalDate startDate, int hoursTotal, int projectID) {
        //Arrange
        CalculatorService calculatorService1 = new CalculatorService();
        LocalDate dateEnd = startDate;
        double dayAmount = (hoursTotal);
        int addedDays = 0;
        //Assert
        calculatorService1.countDateEnd(LocalDate.of(2022, 11, 1), 50, 1);
        dateEnd = dateEnd.plusDays(1);
        if (!(dateEnd.getDayOfWeek() == DayOfWeek.SATURDAY || dateEnd.getDayOfWeek() == DayOfWeek.SUNDAY)) {
            ++addedDays;

            assertTrue(addedDays < dayAmount);
            System.out.println("correct end date");
        }
    }
}




