package chess.model.piece;

import static chess.model.PieceColor.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import chess.model.File;
import chess.model.MoveType;
import chess.model.Path;
import chess.model.PieceColor;
import chess.model.Position;
import chess.model.Rank;

public abstract class Piece {

    private final PieceColor pieceColor;

    public Piece(PieceColor pieceColor) {
        this.pieceColor = pieceColor;
    }

    public abstract boolean isMovable(Path path, MoveType moveType);

    public String getEmblem() {
        if (pieceColor.isWhite()) {
            return getConcreteEmblem().toLowerCase();
        }
        return getConcreteEmblem();
    }

    abstract String getConcreteEmblem();

    public boolean isSameColor(PieceColor PieceColor) {
        return this.pieceColor == PieceColor;
    }

    public abstract double getScore();

    public abstract boolean isKnight();

    public abstract boolean isKing();

    public abstract boolean isPawn();

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Piece piece = (Piece)o;
        return pieceColor == piece.pieceColor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor);
    }
}
