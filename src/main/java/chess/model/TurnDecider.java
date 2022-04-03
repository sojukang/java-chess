package chess.model;

import chess.model.piece.Piece;

public class TurnDecider {

    private PieceColor currentColor = PieceColor.WHITE;
    private boolean isFinished = false;

    boolean isTurnOf(Piece Piece) {
        return isSameColor(Piece);
    }

    void nextState() {
        if (currentColor.isWhite()) {
            currentColor = PieceColor.BLACK;
            return;
        }
        currentColor = PieceColor.WHITE;
    }

    private boolean isSameColor(Piece sourcePiece) {
        return sourcePiece.isSameColor(currentColor);
    }

    boolean isFinished() {
        return isFinished;
    }

    void finish() {
        isFinished = true;
        nextState();
    }

    public PieceColor getWinnerColor() {
        if (!isFinished) {
            throw new IllegalStateException("[ERROR] 게임이 아직 끝나지 않았습니다.");
        }
        return currentColor;
    }
}
