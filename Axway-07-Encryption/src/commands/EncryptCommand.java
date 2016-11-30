package commands;

import actions.FileActions;
import interfaces.Command;

public class EncryptCommand implements Command {

	private FileActions action;

	public EncryptCommand(FileActions action) {
		this.action = action;
	}

	@Override
	public void execute() {
		action.fileEncrypt();

	}
}
