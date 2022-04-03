package chess.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import chess.model.PieceColor;

public class TurnDao {

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

    public void save(String roomId, PieceColor color) {
        Connection connection = getConnection();
        delete(roomId);
        String sql = "insert into turn (room_id, color) values (?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, roomId);
            statement.setString(2, color.toString());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(String roomId) {
        Connection connection = getConnection();
        String sql = "delete from turn where room_id = " + "'" + roomId + "'";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
