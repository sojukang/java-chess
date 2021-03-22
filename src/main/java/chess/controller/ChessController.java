package chess.controller;

import chess.domain.Game;
import chess.domain.position.Position;
import chess.view.InputView;
import chess.view.OutputView;
import chess.view.dto.InputPositionDTO;

public class ChessController {
    public void run() {
        if (willNotPlayGame()) {
            return;
        }
        Game game = new Game();
        OutputView.printWayToMove();
        OutputView.display(game.getPieces());

        play(game);
        printScoreIfWanted(game);
    }

    private boolean willNotPlayGame() {
        try {
            return InputView.willNotPlayGame();
        } catch (IllegalArgumentException e) {
            OutputView.printError(e.getMessage());
            return willNotPlayGame();
        }
    }

    private void play(Game game) {
        while (game.isNotEnd()) {
            playOneTurn(game);
        }
    }

    private void playOneTurn(Game game) {
        try {
            tryOneTurn(game);
        } catch (IllegalArgumentException e) {
            OutputView.printError(e.getMessage());
            playOneTurn(game);
        }
    }

    private void tryOneTurn(Game game) {
        OutputView.printCurrentPlayer(game.currentPlayer());
        InputPositionDTO positionInputs = InputView.requestPositions();
        game.move(Position.from(positionInputs.from()), Position.from(positionInputs.to()));
        OutputView.display(game.getPieces());
    }

    private void printScoreIfWanted(Game game) {
        if (willWatchScore()) {
            OutputView.printScore(game.score());
        }
    }

    private boolean willWatchScore() {
        try {
            return InputView.willWatchScore();
        } catch (IllegalArgumentException e) {
            OutputView.printError(e.getMessage());
            return willWatchScore();
        }
    }
}
