package chess.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import chess.Player;

public class PlayerDao {

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

    public void save(Player player) {
        Connection connection = getConnection();
        String sql = "insert into player (id) values (?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, player.getId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Player findById(String id) {
        Connection connection = getConnection();
        String sql = "select id from player where id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }
            return new Player(
                resultSet.getString("id"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Player> findAll() {
        Connection connection = getConnection();
        String sql = "select id from player";
        List<Player> players = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                players.add(new Player(resultSet.getString("id")));
                return players;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // public Player findWithRoleById(String id) {
    //     Connection connection = getConnection();
    //     String sql = "" +
    //         "select id" +
    //         " from player join role on member.id = role.member_id" +
    //         " where id = ?";
    //     try {
    //         PreparedStatement statement = connection.prepareStatement(sql);
    //         statement.setString(1, id);
    //         ResultSet resultSet = statement.executeQuery();
    //         if (!resultSet.next()) {
    //             return null;
    //         }
    //         return new Player(
    //             resultSet.getString("id"));
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }
    //     return null;
    // }
}
