package engine;

import java.util.Scanner;

import actions.FileActions;

import broker.SingletonCommandBroker;

import commands.ReadCommand;
import commands.WriteCommand;

/**
 * A help class with static method that waits for user input and creates objects
 * ReadCommand, WriteCommand and SingletonCommandBroker and executes commands
 * through the CommandBroker.
 * 
 */

public class Engine {

	/**
	 * Static method that starts our application.
	 * 
	 */

	public static void run() {

		FileActions action = new FileActions();
		Scanner input = new Scanner(System.in);

		// user input
		System.out.println("Please enter the name of existing file you want to read from.");
		String fileName = input.nextLine();

		ReadCommand read = new ReadCommand(fileName, action);
		WriteCommand write = new WriteCommand(fileName, action);

		// creating SingletonCommandBroker object and registering read and write
		// commands
		SingletonCommandBroker commandBroker = SingletonCommandBroker.getInstance();
		commandBroker.registerCommand(read);
		commandBroker.registerCommand(write);

		// executes commands through the command broker
		commandBroker.execute();

		// closing Scanner
		if (input != null) {
			input.close();
		}
	}

}
