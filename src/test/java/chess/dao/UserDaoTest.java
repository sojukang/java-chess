package chess.dao;

import java.sql.Connection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserDaoTest {

    @Test
    void connection() {
        //given
        UserDAO userDao = new UserDAO();
        Connection con = userDao.getConnection();

        //then
        Assertions.assertNotNull(con);
    }
}
