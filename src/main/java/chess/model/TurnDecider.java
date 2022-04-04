package chess.model;

import chess.model.piece.Piece;

public class TurnDecider {

    private PieceColor currentColor;
    private boolean isFinished = false;

    public TurnDecider() {
        currentColor = PieceColor.WHITE;
    }

    public TurnDecider(PieceColor pieceColor) {
        currentColor = pieceColor;
    }

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

    public PieceColor getCurrentTurnColor() {
        return currentColor;
    }
}
