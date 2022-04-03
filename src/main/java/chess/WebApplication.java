package chess;

import static spark.Spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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

        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return render(model, "index.html");
        });

        get("/start", (req, res) -> {
            Map<String, Object> model = new HashMap<>();

            model.put("pieces", StringMapByBoardValues(board));
            return render(model, "index.html");
        });

        move(board);

        get("/status", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("pieces", StringMapByBoardValues(board));
            model.put("status", board.calculateScore());
            return render(model, "index.html");
        });

        get("/end", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            board.init(new TurnDecider(), new defaultInitializer());
            return render(model, "index.html");
        });
    }

    private static void move(Board board) {
        post("/move", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            try {
                board.move(Position.of(req.queryParams("start")), Position.of(req.queryParams("target")));

                model.put("pieces", StringMapByBoardValues(board));
                return render(model, "index.html");
            } catch (RuntimeException e) {
                model.put("pieces", StringMapByBoardValues(board));
                model.put("error", e.getMessage());
                return render(model, "index.html");
            }
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
        mappingTable.put("p", "blackPawn");
        mappingTable.put("r", "blackRook");
        mappingTable.put("n", "blackKnight");
        mappingTable.put("b", "blackBishop");
        mappingTable.put("q", "blackQueen");
        mappingTable.put("k", "blackKing");

        mappingTable.put("P", "whitePawn");
        mappingTable.put("R", "whiteRook");
        mappingTable.put("N", "whiteKnight");
        mappingTable.put("B", "whiteBishop");
        mappingTable.put("Q", "whiteQueen");
        mappingTable.put("K", "whiteKing");

        return mappingTable.get(emblem);
    }
}
