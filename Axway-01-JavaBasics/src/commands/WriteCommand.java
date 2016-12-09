package commands;

import actions.FileActions;
import interfaces.Command;

public class WriteCommand implements Command {

	/**
	 * FileActions object is passed to the command and the command executes
	 * whatever method is needed from the object.
	 */

	private String fileName;
	private String fileToBeWrittenName;
	private StringBuilder text;
	private FileActions action;

	/**
	 * Constructor
	 * 
	 * @param fileName
	 *            - user input for the name of the file
	 * @param action
	 *            - FileActions object
	 */
	public WriteCommand(String fileName, FileActions action) {
		this.fileName = fileName;
		this.action = action;
		this.text = action.read(fileName);
		this.fileToBeWrittenName = action.setNewFileName(fileName);

	}

	/**
	 * Executes the command
	 */

	@Override
	public void execute() {
		action.write(text, fileName, fileToBeWrittenName);

	}

}
