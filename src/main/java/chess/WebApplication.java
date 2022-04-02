package chess;

import static spark.Spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import chess.model.Board;
import chess.model.Position;
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

        post("/move", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            board.move(Position.of(req.queryParams("start")), Position.of(req.queryParams("target")));

            model.put("pieces", StringMapByBoardValues(board));
            return render(model, "index.html");
        });

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

    private static Map<String, String> StringMapByBoardValues(Board board) {
        Map<Position, Piece> values = board.getValues();
        return values.keySet().stream().collect(Collectors.toUnmodifiableMap(
            Position::getString, key -> values.get(key).getEmblem()));
    }

    private static String render(Map<String, Object> model, String templatePath) {
        return new HandlebarsTemplateEngine().render(new ModelAndView(model, templatePath));
    }
}
