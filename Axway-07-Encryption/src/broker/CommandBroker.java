package broker;

import java.util.ArrayList;

import interfaces.Command;

public class CommandBroker {
	
	/**
	 * A list of all the commands passed to the broker.
	 */
	private ArrayList<Command>  commands = new ArrayList<Command>();
	
	/**
	 * Adds a command to the list.
	 * 
	 * @param c - the command that is to be added to the list
	 */
	public void registerCommand(Command c) {
		commands.add(c);
	}
	
	/**
	 * Executes all commands in the list.
	 */
	public void execute() {
		for (Command command : commands) {
			command.execute();
		}
		commands.clear();
	}			

}
