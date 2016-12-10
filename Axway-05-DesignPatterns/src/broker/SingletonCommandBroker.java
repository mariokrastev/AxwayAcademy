package broker;

import java.util.ArrayList;

import interfaces.Command;

/**
 * This class is used to handle commands.
 * 
 * @author MarioKrastev
 */

public class SingletonCommandBroker {

	/**
	 * A list of all the commands passed to the broker.
	 */

	private ArrayList<Command> commands = new ArrayList<Command>();

	/**
	 * Creating by SingletonPattern a static variable to be sure that only one
	 * instance will be used.
	 */

	private static SingletonCommandBroker singletonBroker = null;

	/**
	 * This constructor exists only to ensure that objects would not be
	 * instantiated.
	 */

	private SingletonCommandBroker() {

	}

	/**
	 * Used to return an instance of the object.
	 * 
	 * @return the one and only single instance of the object
	 */

	public static SingletonCommandBroker getInstance() {
		if (singletonBroker == null) {
			singletonBroker = new SingletonCommandBroker();
		}
		return singletonBroker;
	}

	/**
	 * Adds a command to the list.
	 * 
	 * @param c - the command that will be added to the list.
	 */
	
	public void registerCommand(Command command) {
		commands.add(command);
	}

	/**
	 * Executes all commands in the list
	 */
	
	public void execute() {
		for (Command command : commands) {
			command.execute();
		}
	}

}
