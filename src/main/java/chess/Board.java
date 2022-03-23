package chess;

import static chess.PieceColor.*;
import static chess.PieceType.*;
import static chess.Rank.*;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> values;

    public Board() {
        this.values = initBoard();
    }

    private Map<Position, Piece> initBoard() {
        Map<Position, Piece> result = new HashMap<>();

        putPawnLines(result);

        putBackLinePieces(result, BLACK);
        putBackLinePieces(result, WHITE);

        return result;
    }

    private void putPawnLines(Map<Position, Piece> result) {
        putPawnLine(result, BLACK, TWO);
        putPawnLine(result, WHITE, SEVEN);
    }

    private void putPawnLine(Map<Position, Piece> result, PieceColor color, Rank rank) {
        for (File file : File.values()) {
            result.put(new Position(rank, file), new Piece(PAWN, color));
        }
    }

    private void putBackLinePieces(Map<Position, Piece> result, PieceColor color) {
        Rank backLineRank = ONE;
        if (color == WHITE) {
            backLineRank = EIGHT;
        }

        ListIterator<PieceType> backLinePieceTypesIterator = List.of(ROOK, KNIGHT, BISHOP, QUEEN, KING, BISHOP, KNIGHT, ROOK)
            .listIterator();

        for (File file : File.values()) {
            result.put(new Position(backLineRank, file), new Piece(backLinePieceTypesIterator.next(), color));
        }
    }

    public Map<Position, Piece> getValues() {
        return values;
    }
}
