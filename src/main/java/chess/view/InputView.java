package chess.view;

import static camp.nextstep.edu.missionutils.Console.*;

import java.util.List;

import chess.dto.MoveRequest;
import chess.dto.NotMoveRequest;
import chess.dto.Request;
import chess.model.Position;
import chess.vo.Command;

public class InputView {

    private InputView() {
    }

    public static Command inputCommandInStart() {
        return Command.startEnd(readLine());
    }

    public static Request inputCommandInGaming() {
        String input = readLine();
        Command command = Command.MoveStatusEnd(input);
        if (command == Command.END || command == Command.STATUS) {
            return new NotMoveRequest(command);
        }

        List<String> inputs = List.of(input.split(" "));
        return new MoveRequest(command, Position.of(inputs.get(1)), Position.of(inputs.get(2)));
    }

}
