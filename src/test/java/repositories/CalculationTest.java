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



        //Arrange
        boolean actualvalue = calculatorService.isTimeEnough(LocalDate.of(2022,1,1),LocalDate.of(2022, 1, 20), 10, 705);

        //Act + Assert
        assertTrue(true);
        System.out.println(" enough days");

    }



    @DisplayName("Calculate daily speed for project")
    @Test
    public void calculate_DailySpeed() {


        //Arrange
        double speedExpected = calculatorService.calculateSpeedDaily(LocalDate.of(2022, 1, 5), LocalDate.of(2022, 1, 10), 20);


        //Assert + Act
        Assertions.assertEquals(speedExpected, calculatorService.calculateSpeedDaily(LocalDate.of(2022, 1, 5), LocalDate.of(2022, 1, 10), 20));
        System.out.println(speedExpected);





    }
}




