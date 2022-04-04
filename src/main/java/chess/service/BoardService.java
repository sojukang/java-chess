package chess.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chess.EmblemMapper;
import chess.Player;
import chess.Room;
import chess.dao.BoardDao;
import chess.dao.RoomDao;
import chess.dao.TurnDao;
import chess.model.Board;
import chess.model.File;
import chess.model.PieceColor;
import chess.model.Position;
import chess.model.Rank;
import chess.model.TurnDecider;
import chess.model.boardinitializer.BoardInitializer;
import chess.model.boardinitializer.SavedBoardInitializer;
import chess.model.piece.Piece;

public class BoardService {
    private Board board;
    private String roomId;

    public void initBySavedData(TurnDecider turnDecider, BoardInitializer initializer) {
        if (findSavedBoardStringMap().isEmpty()) {
            init(turnDecider, initializer);
            return;
        }
        init(getSavedTurnDecider(), new SavedBoardInitializer(findSavedBoardStringMap()));
    }

    public void init(TurnDecider turnDecider, BoardInitializer initializer) {
        board = new Board(turnDecider, initializer);
    }

    public Map<String, String> findSavedBoardStringMap() {
        return new BoardDao().findById(roomId);
    }

    public Room findRoomById() {
        RoomDao roomDao = new RoomDao();
        return roomDao.findById(roomId);
    }

    public void save() {
        TurnDao turnDao = new TurnDao();
        BoardDao boardDao = new BoardDao();

        turnDao.save(roomId, board.getCurrentTurnColor());
        boardDao.save(getBoardStringMap(), roomId);
    }

    public Map<String, String> getBoardStringMap() {
        Map<Position, Piece> values = board.getValues();
        System.out.println("[board]" + values);
        Map<String, String> result = new HashMap<>();
        for (Rank rank : Rank.reverseValues()) {
            for (File file : File.values()) {
                result.put(Position.of(rank, file).getString(),
                    EmblemMapper.eachPieceEmblem(values, Position.of(rank, file)));
            }
        }
        return result;
    }

    public double calculateScore() {
        return board.calculateScore();
    }

    public TurnDecider getSavedTurnDecider() {
        return new TurnDao().findById(roomId);
    }

    public void setId(String roomId) {
        this.roomId = roomId;
    }

    public void saveRoom(List<Player> players) {
        RoomDao roomDao = new RoomDao();
        roomDao.save(new Room(roomId, players));
    }

    public void move(Position source, Position target) {
        board.move(source, target);
    }

    public boolean isFinished() {
        return board.isFinished();
    }

    public PieceColor getCurrentTurnColor() {
        return board.getCurrentTurnColor();
    }

    public void delete() {
        new TurnDao().delete(roomId);
        new BoardDao().delete(roomId);
        new RoomDao().delete(roomId);
    }
}
