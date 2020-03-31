package domain.pieces;

import domain.move.Direction;
import domain.point.MovePoint;
import domain.point.Point;
import domain.team.Team;
import java.util.List;
import java.util.Map;

public class Queen extends Piece {

    private static final String INITIAL = "Q";

    private static final double score = 9;
    private static final List<Direction> direction = Direction.getAllDirection();

    public Queen(Team team) {
        super(INITIAL, team);
    }

    @Override
    public boolean isMovable(Direction direction, Map<Point, Piece> pieces, MovePoint movePoint) {
        return Roles.isMovableUnlimitedCase(direction, pieces, movePoint);
    }

    @Override
    public boolean isNoneTeam() {
        return false;
    }

    @Override
    public List<Direction> getDirection(Team team) {
        return direction;
    }

    @Override
    public double getScore() {
        return score;
    }
}
