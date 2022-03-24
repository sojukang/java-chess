package chess.piece;

import static org.assertj.core.api.Assertions.assertThat;

import chess.File;
import chess.PieceColor;
import chess.Position;
import chess.Rank;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class RookTest {

    @ParameterizedTest
    @CsvSource(value = {"THREE:A", "THREE:H", "ONE:C", "EIGHT:C"}, delimiter = ':')
    @DisplayName("Rook은 동일 Rank나 File에 있는 좌표로 이동할 수 있다")
    void isMovable(Rank rank, File file) {
        //given
        Rook rook = new Rook(PieceColor.WHITE);

        //when
        boolean actual = rook.isMovable(new Position(Rank.THREE, File.C), new Position(rank, file));

        //then
        assertThat(actual).isTrue();
    }

    @ParameterizedTest
    @CsvSource(value = {"ONE:A", "TWO:B", "FOUR:D"}, delimiter = ':')
    @DisplayName("Rook은 Rank 나 File 모두 동일하지 않다면 이동이 불가하다 (false 반환)")
    void cantMovable(Rank rank, File file) {
        //given
        Rook rook = new Rook(PieceColor.WHITE);

        //when
        boolean actual = rook.isMovable(new Position(Rank.THREE, File.C), new Position(rank, file));

        //then
        assertThat(actual).isFalse();
    }


}