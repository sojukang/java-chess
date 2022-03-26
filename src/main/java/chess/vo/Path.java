package chess.vo;

public class Path {

    private final Position source;
    private final Position target;

    public Path(Position source, Position target) {
        this.source = source;
        this.target = target;
    }

    public boolean isUpStraight(int amount) {
        return isForward(source, target, amount);
    }

    public boolean isDownStraight(int amount) {
        return isForward(target, source, amount);
    }

    public boolean isUpDiagonal() {
        return isDiagonal() && source.rankDisplacement(target) == 1
            && source.fileDistance(target) == 1;
    }

    public boolean isDownDiagonal() {
        return isDiagonal() && target.rankDisplacement(source) == 1
            && source.fileDistance(target) == 1;
    }


    private boolean isForward(Position source, Position target, int amount) {
        return source.rankDisplacement(target) > 0
            && source.rankDisplacement(target) <= amount && source.isSameFile(target);
    }

    public boolean isSourceRankOf(Rank sourceRank) {
        return source.isRankOf(sourceRank);
    }

    public boolean isDiagonal() {
        return source.isDiagonal(target);
    }

    public boolean isAllDirectional() {
        return source.isAllDirectional(target);
    }

    public int fileDistance() {
        return source.fileDistance(target);
    }

    public int rankDistance() {
        return source.rankDistance(target);
    }

    public boolean isCross() {
        return source.isCross(target);
    }
}
