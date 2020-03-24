package domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BoardTest {

    @Test
    @DisplayName("게임판을 만드는 테스트")
    void create_Board() {
        Board board1 = Board.getInstance();
        Board board2 = new Board(new Chess[8][8]);
        assertThat(board1).isEqualTo(board2);
    }
}
