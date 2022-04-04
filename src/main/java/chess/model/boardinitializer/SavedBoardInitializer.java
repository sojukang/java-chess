package chess.model.boardinitializer;

import java.util.HashMap;
import java.util.Map;

import chess.EmblemMapper;
import chess.model.Board;
import chess.model.File;
import chess.model.Position;
import chess.model.Rank;
import chess.model.TurnDecider;
import chess.model.piece.Piece;
import chess.model.piece.PieceCache;

public class SavedBoardInitializer implements BoardInitializer{
    private final Map<String, String> boardStringMap;

    public SavedBoardInitializer(Map<String, String> boardStringMap) {
        this.boardStringMap = boardStringMap;
    }

    @Override
    public Map<Position, Piece> apply() {
        Map<Position, Piece> result = new HashMap<>();
        for (Map.Entry<String, String> entry : boardStringMap.entrySet()) {
            if (entry.getValue().equals("empty")) {
                continue;
            }
            result.put(Position.of(entry.getKey()), PieceCache.of(EmblemMapper.emblemFrom(entry.getValue())));
        }
        return result;
    }

    private static Map<String, String> stringMapByBoardValues(Map<Position, Piece> map) {
        Map<String, String> result = new HashMap<>();
        for (Rank rank : Rank.reverseValues()) {
            for (File file : File.values()) {
                result.put(Position.of(rank, file).getString(),
                    EmblemMapper.eachPieceEmblem(map, Position.of(rank, file)));
            }
        }
        return result;
    }

    public static void main(String[] args) {
        DefaultInitializer initializer = new DefaultInitializer();
        Map<Position, Piece> map = initializer.apply();
        Map<String, String> stringStringMap = stringMapByBoardValues(map);
        System.out.println(stringStringMap);
        SavedBoardInitializer savedBoardInitializer = new SavedBoardInitializer(stringStringMap);
        System.out.println(savedBoardInitializer.apply());
        System.out.println(PieceCache.of(EmblemMapper.emblemFrom("whitePawn")));

        System.out.println(EmblemMapper.emblemFrom("whitePawn"));

        System.out.println(PieceCache.of("p"));
        System.out.println(PieceCache.of("P"));
        System.out.println(PieceCache.of("p"));
        System.out.println(PieceCache.of("p"));
        System.out.println(PieceCache.of("P"));
    }
}
