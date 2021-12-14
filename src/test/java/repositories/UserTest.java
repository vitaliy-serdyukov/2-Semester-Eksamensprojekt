package repositories;
import com.example.domain.models.User;
import com.example.repositories.UserRepository;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

 public class UserTest {

    UserRepository userRepository = new UserRepository();


    @DisplayName("check if user is returned")
    public void getReturnedUser() {
        // Assert
        // Normally mock data would be inserted here.
        String userMail = "anna@3";
        String expectedUserCompanyName = "a";

        //Act
        User user = userRepository.returnUserByEmail(userMail);

        //Assert
        assertEquals(expectedUserCompanyName, user.getCompanyName());



    }
        @DisplayName("test should fail because of an email that do exist")
        public void assertThrows(User user){

        //Arrange
        String userMailWrong = "James@500";

        // Assert + Act
       assertThrows(userRepository.returnUserByEmail(userMailWrong));


     }


}
