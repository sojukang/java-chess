package chess.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import chess.Player;
import chess.Room;

public class RoomDao {

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

    public void save(Room room) {
        Connection connection = getConnection();
        String sql = "insert into room (id, id_white_player, id_black_player) values (?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, room.getId());
            statement.setString(2, room.getIdPlayerWhite());
            statement.setString(3, room.getIdPlayerBlack());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Room findById(String id) {
        Connection connection = getConnection();
        String sql = "select id, id_white_player, id_black_player from room where id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }
            return new Room(resultSet.getString("id"),
                List.of(new Player(resultSet.getString("id_white_player")),
                    new Player(resultSet.getString("id_black_player"))));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void delete(String roomId) {
        Connection connection = getConnection();
        String sql = "delete from room where id = " + "'" + roomId + "'";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
