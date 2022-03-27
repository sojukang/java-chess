package chess.dto;

import chess.vo.Command;
import chess.vo.Position;

public class NotMoveRequest implements Request {

    private static final String ERROR_UNSUPPORTED_OPERATION = "[ERROR] 지원하지 않는 기능입니다.";

    private final Command command;

    public NotMoveRequest(Command command) {
        this.command = command;
    }

    @Override
    public Command getCommand() {
        return command;
    }

    @Override
    public Position getSource() {
        throw new UnsupportedOperationException(ERROR_UNSUPPORTED_OPERATION);
    }

    @Override
    public Position getTarget() {
        throw new UnsupportedOperationException(ERROR_UNSUPPORTED_OPERATION);
    }
}
