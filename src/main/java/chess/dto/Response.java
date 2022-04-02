package chess.dto;

import java.util.Map;

import chess.model.piece.Piece;

public class Response {
    private final Map<String, Piece> pieceMap;

    public Response(Map<String, Piece> pieceMap) {
        this.pieceMap = pieceMap;
    }

    public Map<String, Piece> getPieceMap() {
        return pieceMap;
    }
}
