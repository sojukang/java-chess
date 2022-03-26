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
            printPiecesInOneRank(piecesByPositions, rank);
        }
    }

    private static void printPiecesInOneRank(Map<Position, Piece> piecesByPositions, Rank rank) {
        StringBuilder builder = new StringBuilder();
        for (File file : File.values()) {
            builder.append(piecesByPositions.get(new Position(rank, file)).getEmblem());
        }
        out.println(builder);
    }

    public static void printScore(double score) {
        out.println("플레이어의 점수는 " + score + "점 입니다.");
    }
}
