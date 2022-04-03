package chess;

import java.util.List;

public class Room {
    private final String id;
    private final List<Player> players;

    public Room(String id, List<Player> players) {
        this.id = id;
        this.players = players;
    }

    public String getId() {
        return id;
    }

    public String getIdPlayerWhite() {
        return players.get(0).getId();
    }

    public String getIdPlayerBlack() {
        return players.get(1).getId();
    }
}
