package chess.dao;

import java.sql.Connection;

public interface GameDao {
    Connection getConnection();
}
