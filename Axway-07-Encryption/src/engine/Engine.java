package engine;

import java.util.Scanner;

import actions.FileActions;
import broker.CommandBroker;
import commands.DecryptCommand;
import commands.EncryptCommand;

public class Engine {

	static Scanner scan = new Scanner(System.in);

	public void run() {

		System.out.println("Enter a number pointing to operation you want to execute.");
		System.out.println("1 - encrypt");
		System.out.println("2 - decrypt");
		System.out.println("3 - exit");

		String decision = scan.nextLine();
		CommandBroker broker = new CommandBroker();
		FileActions actions = new FileActions();

		switch (decision) {
		case "1":
			EncryptCommand encrypt = new EncryptCommand(actions);
			broker.registerCommand(encrypt);
			broker.execute();
			run();
			break;

		case "2":
			DecryptCommand decrypt = new DecryptCommand(actions);
			broker.registerCommand(decrypt);
			broker.execute();
			run();
			break;

		case "3":
			System.out.println("The program has ended.");
			return;

		default:
			System.out.println("Invalid action, please enter a valid action.");
			run();

		}

	}
}
