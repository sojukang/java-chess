package chess.view;

import static java.lang.System.*;

import java.util.Map;

import chess.File;
import chess.Position;
import chess.Rank;
import chess.piece.Piece;

public class OutputView {

    private static final String NEXT_LINE = System.lineSeparator();

    public static void printInitMessage() {

        out.println(
            "> 체스 게임을 시작합니다." + NEXT_LINE +
                "> 게임 시작 : start" + NEXT_LINE +
                "> 게임 종료 : end" + NEXT_LINE +
                "> 게임 이동 : move source위치 target위치 - 예. move b2 b3"
        );
    }

    public static void printChessGameBoard(Map<Position, Piece> piecesByPositions) {
        for (Rank rank : Rank.reverseValues()) {
            for (File file : File.values()) {
                Position searchPosition = new Position(rank, file);
                Piece piece = piecesByPositions.get(searchPosition);
                out.print(piece.getEmblem());
            }
            out.println();
        }
    }

    public static void printScore(double score) {
        out.println("플레이어의 점수는 " + score + "점 입니다.");
    }
}
