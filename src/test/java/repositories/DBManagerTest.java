package repositories;

import com.example.repositories.DBManager;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import static org.junit.jupiter.api.Assertions.*;

class DBManagerTest {

    //genovervej

    @Test
    public void test_GetConnectionToDatabase(){
        Connection connection = DBManager.getConnection();
        assertNotNull(connection);
    }


}


