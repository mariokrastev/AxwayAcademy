package ftpClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.Scanner;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

/**
 * Creating a FTP program that executes the following commands from user input:
 * Downloading file, Uploading file, Deleting file and listing all the files.
 * 
 * @author MarioKrastev
 * 
 */

public class FtpClientHomework {

	/**
	 * Creating a method for downloading a specified file.
	 * 
	 */

	private static void downloadFile(String fileName, FTPClient client, OutputStream out) {
		try {
			out = new FileOutputStream(new File(fileName));

			boolean success;
			success = client.retrieveFile(fileName, out);
			System.out.println(success ? "Downloading file [" + fileName + "] was OK"
					: "Downloading failed! There is no file with name " + fileName);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Creating a method for deleting a specified file.
	 * 
	 */
	private static void deleteFile(String fileName, FTPClient client) {

		try {

			boolean success;
			success = client.deleteFile(fileName);
			System.out.println(success ? "Deleting file [" + fileName + "] was OK"
					: "Deleting failed! There is no file with name " + fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creating a method for listing all the files.
	 * 
	 */

	private static void listFiles(FTPClient client) {
		try {
			FTPFile[] files = client.listFiles();

			if (files.length < 1) {
				System.out.println("The folder is empty.");
			} else {
				System.out.println("All files listed successfully.");
				for (FTPFile ftpFile : files) {
					System.out.println(ftpFile.getName());
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creating a method for uploading a specified file.
	 * 
	 */
	private static void uploadFile(String fileName, FTPClient client, FileInputStream fis) {
		try {

			fis = new FileInputStream(new File(fileName));
			client.storeFile(fileName, fis);
			System.out.println("The file is uploaded successfully.");

		} catch (FileNotFoundException fnfe) {
			System.out.println("The file you want to upload doesnt exist.");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}

			} catch (IOException e) {
				e.printStackTrace();

			}
		}
	}

	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);

		// login credentials

		System.out.println("Enter IP address");
		String server = input.nextLine();
		System.out.println("Enter username");
		String username = input.nextLine();
		System.out.println("Enter password");
		String password = input.nextLine();
		System.out.println("Enter port");
		int port = Integer.parseInt(input.nextLine());

		// create an instance of the client

		FTPClient client = new FTPClient();
		OutputStream out = null;
		FileInputStream fis = null;

		try {

			// connect to the server and authenticate

			client.connect(server, port);
			client.login(username, password);

			// using infinite loop with 'if' statement to take care about different cases about user input.

			while (true) {

				// input information for the user

				System.out.println(
						"Enter a action you want to do! Available actions: 1 - Download file, 2 - Upload file, 3 - Delete file, 4 - List files, "
								+ "5 - Exit the program.");
				System.out.println("Just enter the number pointing to action you want to do.");

				// we need some case for end the program, so using 'if' statement is fine.
				String action = input.nextLine();
				if (action.equals("5")) {
					System.out.println("The program has ended");
					break;
				}

				String fileName = null;

				// constructing switch-case for different commands
				switch (action) {

				case "1":
					System.out.println("Please enter the full name and the format of the file you want to download.");
					fileName = input.nextLine();

					downloadFile(fileName, client, out);

					break;

				case "2":
					System.out.println("Please enter the full name and the format of the file you want to upload.");
					fileName = input.nextLine();

					uploadFile(fileName, client, fis);

					break;

				case "3":
					System.out.println("Please enter the full name and the format of the file you want to delete.");
					fileName = input.nextLine();

					deleteFile(fileName, client);
					break;

				case "4":

					listFiles(client);
					break;

				// using default value to ensure that everything except numbers from 1 to 5 will be invalid.
					
				default:
					System.out.println(
							"Invalid number! Please enter a valid number from 1 to 5, pointing to the action you want to do.");
					System.out.println(
							"Available actions: 1 - Download file, 2 - Upload file, 3 - Delete file, 4 - List all files, 5 - End program.");
					// again reading the user input
					action = input.nextLine();

				}
			}
		} catch (SocketException e) {
			System.out.println("Problem establishing FTP connection");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Problem establishing FTP connection");
			e.printStackTrace();
		} finally {

			// closing streams and disconnect

			try {
				if (out != null) {
					out.close();
				}
				if (input != null) {
					input.close();
				}

				if (client != null) {
					client.disconnect();
				}
				if (client.isConnected()) {
					client.disconnect();
				}
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				System.out.println("Problem disconnecting");
				e.printStackTrace();
			}
		}

	}

}
