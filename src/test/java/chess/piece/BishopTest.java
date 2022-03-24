package chess.piece;

import static chess.File.C;
import static chess.Rank.THREE;
import static org.assertj.core.api.Assertions.assertThat;

import chess.File;
import chess.PieceColor;
import chess.Position;
import chess.Rank;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class BishopTest {


    @DisplayName("비숍은 오로지 대각선 방향으로만 이동이 가능하다")
    @ParameterizedTest
    @CsvSource(value = {"FOUR:D", "FIVE:E", "FOUR:B", "TWO:B"}, delimiter = ':')
    void isMovable(Rank rank, File file) {
        //given
        Bishop bishop = new Bishop(PieceColor.WHITE);
        Position source = new Position(THREE, C);
        Position target = new Position(rank, file);

        //when
        boolean actual = bishop.isMovable(source, target);

        //then
        assertThat(actual).isTrue();
    }

    @ParameterizedTest
    @CsvSource(value = {"THREE:D", "FOUR:E"}, delimiter = ':')
    @DisplayName("비숍은 대각선이 아니면 이동이 불가능하다")
    void cantMovable(Rank rank, File file) {
        //given
        Bishop bishop = new Bishop(PieceColor.WHITE);
        Position source = new Position(THREE, C);
        Position target = new Position(rank, file);

        //when
        boolean actual = bishop.isMovable(source, target);

        //then
        assertThat(actual).isFalse();
    }

}