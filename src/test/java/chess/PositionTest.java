package chess;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class PositionTest {

    @Test
    @DisplayName("위치 값의 동일성을 테스트한다.")
    void position_equals() {
        //given
        Position position = new Position(Rank.ONE, File.A);

        //then
        assertThat(position).isEqualTo(new Position(Rank.ONE, File.A));
    }

    @ParameterizedTest
    @ValueSource(strings = {"a9", "i7", "a0", "A1"})
    @DisplayName("위치 범위를 벗어나면 예외를 던진다.")
    void position_exception() {
        assertThatThrownBy(() -> new Position("a9"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("[ERROR]");
    }
}