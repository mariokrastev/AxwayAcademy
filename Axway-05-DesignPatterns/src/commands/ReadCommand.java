package commands;

import actions.FileActions;
import interfaces.Command;

/**
 * A command that starts the read() method of the FileActions object.
 * 
 * @author MarioKrastev
 *
 */
public class ReadCommand implements Command {

	/**
	 * FileActions object is passed to the command and the command executes
	 * whatever method is needed from the object.
	 */

	private String fileName;
	private FileActions action;

	/**
	 * Constructor.
	 * 
	 * @param fileName
	 *            - name of file
	 * @param action
	 *            - FileAction object
	 */
	public ReadCommand(String fileName, FileActions action) {
		this.fileName = fileName;
		this.action = action;
	}

	/**
	 * Executes the command
	 */
	@Override
	public void execute() {
		action.read(fileName);

	}
}
