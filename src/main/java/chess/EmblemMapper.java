package chess;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import chess.model.Position;
import chess.model.piece.Piece;

public class EmblemMapper {
    private static final Map<String, String> emblemToFullNameTable = new HashMap<>();

    static {
        emblemToFullNameTable.put("p", "whitePawn");
        emblemToFullNameTable.put("r", "whiteRook");
        emblemToFullNameTable.put("n", "whiteKnight");
        emblemToFullNameTable.put("b", "whiteBishop");
        emblemToFullNameTable.put("q", "whiteQueen");
        emblemToFullNameTable.put("k", "whiteKing");

        emblemToFullNameTable.put("P", "blackPawn");
        emblemToFullNameTable.put("R", "blackRook");
        emblemToFullNameTable.put("N", "blackKnight");
        emblemToFullNameTable.put("B", "blackBishop");
        emblemToFullNameTable.put("Q", "blackQueen");
        emblemToFullNameTable.put("K", "blackKing");

        emblemToFullNameTable.put(".", "empty");
    }

    public static String eachPieceEmblem(Map<Position, Piece> piecesByPositions, Position position) {
        if (Objects.isNull(piecesByPositions.get(position))) {
            return "empty";
        }

        return fullNameFrom(piecesByPositions.get(position).getEmblem());
    }

    private static String fullNameFrom(String emblem) {
        return emblemToFullNameTable.get(emblem);
    }

    public static String emblemFrom(String fullName) {
        return getKey(emblemToFullNameTable, fullName);
    }

    private static <K, V> K getKey(Map<K, V> map, V value) {
        for (K key : map.keySet()) {
            if (value.equals(map.get(key))) {
                return key;
            }
        }
        return null;
    }
}
