package chess;

import static spark.Spark.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import chess.dao.BoardDao;
import chess.dao.RoomDao;
import chess.dao.TurnDao;
import chess.model.Board;
import chess.model.File;
import chess.model.Position;
import chess.model.Rank;
import chess.model.TurnDecider;
import chess.model.boardinitializer.defaultInitializer;
import chess.model.piece.Piece;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

public class WebApplication {
    private static String roomId;
    private static Board board = new Board(new TurnDecider(), new defaultInitializer());

    public static void main(String[] args) {
        staticFiles.location("/");

        index();
        start(board);
        game(board);
        save(board);
        move(board);
        status(board);
        end(board);
        save(board);
    }

    private static void save(Board board) {
        post("/save", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            Map<String, String> boardStringMap = stringMapByBoardValues(board);
            TurnDao turnDao = new TurnDao();
            BoardDao boardDao = new BoardDao();

            turnDao.save(roomId, board.getCurrentTurnColor());
            boardDao.save(boardStringMap, roomId);
            model.put("pieces", boardStringMap);
            return render(model, "game.html");
        });
    }

    private static void game(Board board) {
        post("/game", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            Player playerWhite = new Player(req.queryParams("idPlayerWhite"));
            Player playerBlack = new Player(req.queryParams("idPlayerBlack"));
            roomId = playerWhite.getId() + playerBlack.getId();

            RoomDao roomDao = new RoomDao();
            if (Objects.isNull(roomDao.findById(roomId))) {
                roomDao.save(new Room(roomId, List.of(playerWhite, playerBlack)));
            }

            BoardDao boardDao = new BoardDao();
            Map<String, String> stringMap = boardDao.findById(roomId);
            if (Objects.isNull(stringMap)) {
                model.put("pieces", stringMapByBoardValues(board));
            } else {
                model.put("pieces", stringMap);
            }
            model.put("roomId", roomId);
            return render(model, "game.html");
        });
    }

    private static void index() {
        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return render(model, "index.html");
        });
    }

    private static void start(Board board) {
        get("/start", (req, res) -> {
            Map<String, Object> model = new HashMap<>();

            model.put("pieces", stringMapByBoardValues(board));
            return render(model, "game.html");
        });
    }

    private static void move(Board board) {
        post("/move", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            try {
                board.move(Position.of(req.queryParams("start")), Position.of(req.queryParams("target")));
                model.put("pieces", stringMapByBoardValues(board));
                if (board.isFinished()) {
                    return finishWhenKingCaptured(board, model);
                }

                return render(model, "game.html");

            } catch (RuntimeException e) {
                model.put("pieces", stringMapByBoardValues(board));
                model.put("error", e.getMessage());
                return render(model, "game.html");
            }
        });
    }

    private static String finishWhenKingCaptured(Board board, Map<String, Object> model) {
        model.put("pieces", stringMapByBoardValues(board));
        model.put("score", board.calculateScore());
        model.put("winnerColor", board.getCurrentTurnColor());
        board.init(new TurnDecider(), new defaultInitializer());
        return render(model, "finish.html");
    }

    private static void status(Board board) {
        get("/status", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("pieces", stringMapByBoardValues(board));
            model.put("status", board.calculateScore());
            return render(model, "game.html");
        });
    }

    private static void end(Board board) {
        get("/end", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            board.init(new TurnDecider(), new defaultInitializer());
            return render(model, "index.html");
        });
    }

    private static Map<String, String> stringMapByBoardValues(Board board) {
        Map<Position, Piece> values = board.getValues();
        Map<String, String> result = new HashMap<>();
        for (Rank rank : Rank.reverseValues()) {
            for (File file : File.values()) {
                result.put(Position.of(rank, file).getString(),
                    eachPieceEmblem(values, Position.of(rank, file)));
            }
        }
        return result;
    }

    private static String render(Map<String, Object> model, String templatePath) {
        return new HandlebarsTemplateEngine().render(new ModelAndView(model, templatePath));
    }

    private static String eachPieceEmblem(Map<Position, Piece> piecesByPositions, Position position) {
        if (Objects.isNull(piecesByPositions.get(position))) {
            return "empty";
        }

        return emblemMapper(piecesByPositions.get(position).getEmblem());
    }

    private static String emblemMapper(String emblem) {
        Map<String, String> mappingTable = new HashMap<>();
        mappingTable.put("p", "whitePawn");
        mappingTable.put("r", "whiteRook");
        mappingTable.put("n", "whiteKnight");
        mappingTable.put("b", "whiteBishop");
        mappingTable.put("q", "whiteQueen");
        mappingTable.put("k", "whiteKing");

        mappingTable.put("P", "blackPawn");
        mappingTable.put("R", "blackRook");
        mappingTable.put("N", "blackKnight");
        mappingTable.put("B", "blackBishop");
        mappingTable.put("Q", "blackQueen");
        mappingTable.put("K", "blackKing");

        return mappingTable.get(emblem);
    }
}
