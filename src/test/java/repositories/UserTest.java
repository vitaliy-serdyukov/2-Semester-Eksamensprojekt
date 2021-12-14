package repositories;
import com.example.domain.exceptions.ProjectInputException;
import com.example.domain.models.User;
import com.example.repositories.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {


    @Test
    @DisplayName("check if a user is created")
    public void creatuser_UserCredentials(){

        //Arrange
        UserRepository userRepo = new UserRepository();
        User user1 = new User("ems@1234", "1234", "emma", "r", "projekt", "12345");

        // Act
        userRepo.writeUser(user1);

        //Assert
        assertThrows(ProjectInputException.class, () -> userRepo.writeUser(user1));
    }

    @Test
    @DisplayName("check if user is returned by email")
        public void check_UserIsReturnedByEmail() {

        //Arrange
        UserRepository userRepo = new UserRepository();
        User user3 = new User("ems@123", "123");

        //Act
        userRepo.returnUserByEmail(user3.getEmail());

        //Assert
        assertThrows(ProjectInputException.class, ()-> userRepo.returnUserByEmail(user3.getEmail()));

        }

}
