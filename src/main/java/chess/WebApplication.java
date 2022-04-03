package chess;

import static spark.Spark.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import chess.dao.PlayerDao;
import chess.dao.RoomDao;
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
    public static void main(String[] args) {
        staticFiles.location("/");
        Board board = new Board(new TurnDecider(), new defaultInitializer());

        index();
        start(board);
        move(board);
        status(board);
        end(board);

        post("/save", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            Player playerWhite = new Player(req.queryParams("idPlayerWhite"));
            Player playerBlack = new Player(req.queryParams("idPlayerBlack"));

            RoomDao roomDao = new RoomDao();
            roomDao.save(new Room(playerWhite.getId() + playerBlack.getId(),
            List.of(playerWhite, playerBlack)));

            PlayerDao playerDao = new PlayerDao();
            playerDao.save(playerWhite);
            playerDao.save(playerBlack);

            model.put("pieces", StringMapByBoardValues(board));
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

            model.put("pieces", StringMapByBoardValues(board));
            return render(model, "game.html");
        });
    }

    private static void move(Board board) {
        post("/move", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            try {
                board.move(Position.of(req.queryParams("start")), Position.of(req.queryParams("target")));
                model.put("pieces", StringMapByBoardValues(board));
                if (board.isFinished()) {
                    return finishWhenKingCaptured(board, model);
                }

                return render(model, "game.html");

            } catch (RuntimeException e) {
                model.put("pieces", StringMapByBoardValues(board));
                model.put("error", e.getMessage());
                return render(model, "game.html");
            }
        });
    }

    private static String finishWhenKingCaptured(Board board, Map<String, Object> model) {
        model.put("pieces", StringMapByBoardValues(board));
        model.put("score", board.calculateScore());
        model.put("winnerColor", board.getWinnerColor());
        board.init(new TurnDecider(), new defaultInitializer());
        return render(model, "finish.html");
    }

    private static void status(Board board) {
        get("/status", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("pieces", StringMapByBoardValues(board));
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

    private static Map<String, String> StringMapByBoardValues(Board board) {
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
