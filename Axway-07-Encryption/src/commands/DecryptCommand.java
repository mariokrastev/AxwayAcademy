package commands;

import actions.FileActions;
import interfaces.Command;

public class DecryptCommand implements Command {

	private FileActions action;

	public DecryptCommand(FileActions action) {
		this.action = action;
	}

	@Override
	public void execute() {
		action.fileDecrypt();

	}
}
