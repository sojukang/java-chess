package chess.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class UserDAO {
    public Connection getConnection() {
         String url = "jdbc:mysql://localhost:13306/slipp";
        String id = "slipp";
        String pw = "password";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, id, pw);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
