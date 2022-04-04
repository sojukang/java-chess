package chess.model.boardinitializer;

import java.util.HashMap;
import java.util.Map;

import chess.EmblemMapper;
import chess.model.Position;
import chess.model.piece.Piece;
import chess.model.piece.PieceCache;

public class SavedBoardInitializer implements BoardInitializer {
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
}
