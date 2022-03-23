package chess;

import java.util.Objects;

public class Position {

    private final Rank rank;
    private final File file;

    public Position(Rank rank, File file) {
        this.rank = rank;
        this.file = file;
    }

    /**
     * a1
     */
    public Position(String rankFile) {
        this.rank = Rank.of(rankFile.substring(1, 2));
        this.file = File.of(rankFile.substring(0, 1));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Position position = (Position)o;
        return rank == position.rank && file == position.file;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rank, file);
    }
}