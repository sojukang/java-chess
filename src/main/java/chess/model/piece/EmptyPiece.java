package chess.model.piece;

import chess.model.MoveType;
import chess.model.Path;
import chess.model.PieceColor;

public class EmptyPiece extends Piece {

    private static final String EMBLEM = ".";
    private static final double SCORE = 0;

    public EmptyPiece(PieceColor pieceColor) {
        super(pieceColor);
    }

    @Override
    public boolean isMovable(Path path, MoveType moveType) {
        return false;
    }

    @Override
    public String getConcreteEmblem() {
        return EMBLEM;
    }

    @Override
    public double getScore() {
        return SCORE;
    }

    @Override
    public boolean isKnight() {
        return false;
    }

    @Override
    public boolean isKing() {
        return false;
    }

    @Override
    public boolean isPawn() {
        return false;
    }
}