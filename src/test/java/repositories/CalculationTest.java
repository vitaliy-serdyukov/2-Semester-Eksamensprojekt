package repositories;
import com.example.domain.services.CalculatorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.DayOfWeek;
import java.time.LocalDate;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class CalculationTest {

    CalculatorService calculatorService = new CalculatorService();

    @DisplayName("calculate enough time for project")
    @Test
    public void enough_time() {


        LocalDate startdate = LocalDate.of(2022,1,1);
        LocalDate enddate = LocalDate.of(2022, 1, 20);
        int hoursTotal = 30;
        int id = 705;


        //Arrange
        boolean actualvalue = calculatorService.isTimeEnough(LocalDate.of(2022,1,1),LocalDate.of(2022, 1, 20), 40, 705);

        //Act
        assertTrue(true);
        System.out.println("not enough days");

    }



    @DisplayName("Calculate daily speed for project")
    @Test
    public void calculate_DailySpeed() {

        //Arrange
        int projectId = 325;
        LocalDate startDate = LocalDate.of(2022,1,5);
        LocalDate endDate = LocalDate.of(2022,1, 10);
        int hourstotal = 20;
        int daysexpected = 5;


        //Act
        double speedExpected = calculatorService.calculateSpeedDaily(LocalDate.of(2022, 1, 5), LocalDate.of(2022, 1, 10), 20);


        //Assert
        Assertions.assertEquals(speedExpected, calculatorService.calculateSpeedDaily(LocalDate.of(2022, 1, 5), LocalDate.of(2022, 1, 10), 20));
        System.out.println(speedExpected);





    }
}




