package chess.model.PieceArrangement;

import java.util.Map;

import chess.model.Position;
import chess.model.piece.Piece;

public interface PieceArrangement {
    Map<Position, Piece> apply();
}