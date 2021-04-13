package chess.piece;

import chess.domain.Point;
import chess.domain.piece.PieceType;
import chess.domain.piece.kind.Empty;
import chess.domain.piece.kind.Pawn;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static chess.domain.piece.Color.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class PawnTest {
    @DisplayName("Pawn 생성")
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7})
    void create(int column) {
        Pawn blackPawn = new Pawn(BLACK);
        assertThat(PieceType.findPiece(1, column)).isEqualTo(blackPawn);
        Pawn whitePawn = new Pawn(WHITE);
        assertThat(PieceType.findPiece(6, column)).isEqualTo(whitePawn);
    }

    @DisplayName("검은 Pawn의 불가능한 방향 확인")
    @Test
    void checkBlackPawnImpossibleMove() {
        Point source = Point.of(1, 3);
        Pawn blackPawn = new Pawn(BLACK);

        Point emptyPoint = Point.of(0, 3);
        Empty empty = new Empty(NOTHING);

        assertThatThrownBy(
                () -> blackPawn.validateMovableRoute(source, emptyPoint, empty)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("하얀 Pawn의 불가능한 방향 확인")
    @Test
    void checkWhitePawnImpossibleMove() {
        Point source = Point.of(6, 3);
        Pawn whitePawn = new Pawn(WHITE);

        Point emptyPoint = Point.of(7, 3);
        Empty empty = new Empty(NOTHING);

        assertThatThrownBy(
                () -> whitePawn.validateMovableRoute(source, emptyPoint, empty)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("대각선 이동 시 기물이 없는 경우")
    @Test
    void checkRightDiagonalMove() {
        Pawn whitePawn = new Pawn(WHITE);
        Point whitePawnPoint = Point.of(6, 3);

        Empty empty = new Empty(NOTHING);
        Point emptyPoint = Point.of(5, 4);

        Pawn blackPawn = new Pawn(BLACK);
        Point blackPawnPoint = Point.of(5, 4);

        Empty empty2 = new Empty(NOTHING);
        Point emptyPoint2 = Point.of(4, 3);

        assertThatThrownBy(
                () -> whitePawn.validateMovableRoute(whitePawnPoint, emptyPoint, empty)
        ).isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(
                () -> blackPawn.validateMovableRoute(blackPawnPoint, emptyPoint2, empty2)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("폰 첫 이동 아니면 두 칸 이동 불가 확인")
    @Test
    void checkInitialPawnImpossibleMove() {
        Pawn whitePawn = new Pawn(WHITE);
        Point whitePawnPoint = Point.of(2, 3);

        Empty empty = new Empty(NOTHING);
        Point emptyPoint = Point.of(4, 3);

        assertThatThrownBy(
                () -> whitePawn.validateMovableRoute(whitePawnPoint, emptyPoint, empty)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("폰 첫 이동 시 두 칸 이동 가능 확인")
    @Test
    void checkInitialPawnPossibleMove() {
        Pawn whitePawn = new Pawn(WHITE);
        Point whitePawnPoint = Point.of(6, 3);

        Empty empty = new Empty(NOTHING);
        Point emptyPoint = Point.of(4, 3);

        assertDoesNotThrow(() -> whitePawn.validateMovableRoute(whitePawnPoint, emptyPoint, empty));
    }
}