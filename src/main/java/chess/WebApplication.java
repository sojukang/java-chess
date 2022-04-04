package chess;

import static spark.Spark.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import chess.model.Position;
import chess.model.TurnDecider;
import chess.model.boardinitializer.DefaultInitializer;
import chess.service.BoardService;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

public class WebApplication {
    public static void main(String[] args) {
        staticFiles.location("/");
        BoardService boardService = new BoardService();

        index();
        start(boardService);
        game(boardService);
        save(boardService);
        move(boardService);
        status(boardService);
        init(boardService);
        end(boardService);
        save(boardService);
    }

    private static void index() {
        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return render(model, "index.html");
        });
    }

    private static void init(BoardService boardService) {
        post("/init", (req, res) -> {
            boardService.delete();
            return render(Map.of(), "index.html");
        });
    }

    private static void game(BoardService boardService) {
        post("/game", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            Player playerWhite = new Player(req.queryParams("idPlayerWhite"));
            Player playerBlack = new Player(req.queryParams("idPlayerBlack"));
            boardService.setId(playerWhite.getId() + playerBlack.getId());

            if (Objects.isNull(boardService.findRoomById())) {
                boardService.saveRoom(List.of(playerWhite, playerBlack));
            }

            boardService.initBySavedData(new TurnDecider(), new DefaultInitializer());

            model.put("pieces", boardService.getBoardStringMap());
            model.put("color", boardService.getCurrentTurnColor());
            return render(model, "game.html");
        });
    }

    private static void save(BoardService boardService) {
        post("/save", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            boardService.save();
            model.put("pieces", boardService.getBoardStringMap());
            return render(model, "game.html");
        });
    }

    private static void start(BoardService boardService) {
        get("/start", (req, res) -> {
            Map<String, Object> model = new HashMap<>();

            model.put("pieces", boardService.getBoardStringMap());
            return render(model, "game.html");
        });
    }

    private static void move(BoardService boardService) {
        post("/move", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            try {
                boardService.move(Position.of(req.queryParams("start")), Position.of(req.queryParams("target")));
                model.put("pieces", boardService.getBoardStringMap());
                if (boardService.isFinished()) {
                    return finishWhenKingCaptured(boardService, model);
                }
                model.put("color", boardService.getCurrentTurnColor());
                return render(model, "game.html");

            } catch (RuntimeException e) {
                model.put("pieces", boardService.getBoardStringMap());
                model.put("error", e.getMessage());
                return render(model, "game.html");
            }
        });
    }

    private static String finishWhenKingCaptured(BoardService boardService, Map<String, Object> model) {
        model.put("pieces", boardService.getBoardStringMap());
        model.put("score", boardService.calculateScore());
        model.put("winnerColor", boardService.getCurrentTurnColor());
        boardService.init(new TurnDecider(), new DefaultInitializer());
        return render(model, "finish.html");
    }

    private static void status(BoardService boardService) {
        get("/status", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("pieces", boardService.getBoardStringMap());
            model.put("status", boardService.calculateScore());
            model.put("color", boardService.getCurrentTurnColor());
            return render(model, "game.html");
        });
    }

    private static void end(BoardService boardService) {
        get("/end", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            boardService.init(new TurnDecider(), new DefaultInitializer());
            return render(model, "index.html");
        });
    }

    private static String render(Map<String, Object> model, String templatePath) {
        return new HandlebarsTemplateEngine().render(new ModelAndView(model, templatePath));
    }
}
