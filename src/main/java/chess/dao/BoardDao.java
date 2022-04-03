package chess.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public class BoardDao {

    private static final String URL = "jdbc:mysql://localhost:13306/chess";
    private static final String USER = "user";
    private static final String PASSWORD = "password";

    public Connection getConnection() {
        loadDriver();
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private Connection loadDriver() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void save(Map<String, String> boardMap, String roomId) {
        delete(roomId);
        Connection connection = getConnection();
        String sql = "insert into board (room_id, position, piece) values (?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            for (Map.Entry<String, String> entry : boardMap.entrySet()) {
                statement.setString(1, roomId);
                statement.setString(2, entry.getKey());
                statement.setString(3, entry.getValue());
                statement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(String roomId) {
        Connection connection = getConnection();
        String sql = "delete from board where room_id = " + "'" + roomId + "'";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
