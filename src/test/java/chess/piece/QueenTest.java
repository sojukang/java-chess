package chess.piece;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import chess.model.piece.Queen;
import chess.vo.File;
import chess.vo.MoveType;
import chess.vo.PieceColor;
import chess.vo.Position;
import chess.vo.Rank;

public class QueenTest {

    @ParameterizedTest
    @CsvSource(value = {"FIVE:E", "FIVE:C"}, delimiter = ':')
    @DisplayName("퀸은 상하좌우, 대각선 이동만 가능하다")
    void isMovable(Rank rank, File file) {

        //given
        Queen queen = new Queen(PieceColor.WHITE);

        //when
        boolean actual = queen.isMovable(new Position(Rank.THREE, File.C), new Position(rank, file), MoveType.EMPTY);

        //then
        assertThat(actual).isTrue();
    }

    @ParameterizedTest
    @CsvSource(value = {"FIVE:D", "ONE:B"}, delimiter = ':')
    @DisplayName("퀸은 상하좌우, 대각선 이동이 아니면 false를 반환한다")
    void cantMovable(Rank rank, File file) {
        //given
        Queen queen = new Queen(PieceColor.WHITE);

        //when
        boolean actual = queen.isMovable(new Position(Rank.THREE, File.C), new Position(rank, file), MoveType.EMPTY);

        //then
        assertThat(actual).isFalse();
    }
}
