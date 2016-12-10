package actions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.Properties;

import engine.Engine;

/**
 * In this class is the implementation of read and write methods and a help
 * method for the output file name.
 * 
 * @author MarioKrastev
 *
 */
public class FileActions {

	/**
	 * Constants:
	 * 
	 * @param DATE_FORMAT
	 *            - formatted date for the name of the output file.
	 * @param PROPERTIES_FILE
	 *            - the name of the file where the pair words will be stored.
	 */

	private static final String DATE_FORMAT = new SimpleDateFormat("yyyyMMdd").format(new Date());
	private static final String PROPERTIES_FILE = "WordsPairs.txt";

	/**
	 * A help method for renaming the new output file.
	 * 
	 * @param fileName
	 *            - the output file with old name.
	 * @return the new name of the file.
	 */
	public String setNewFileName(String fileName) {
		String newFileName = fileName + "_modified_" + DATE_FORMAT + ".txt";
		return newFileName;
	}

	/**
	 * A implementation of read method. Reads from properties file the pair
	 * words to be replaced and replaces the words in clients file.
	 * 
	 * @param fileName
	 * @return
	 */
	public StringBuilder read(String fileName) {
		InputStream input = null;
		Properties properties = new Properties();
		File propsFile = new File(PROPERTIES_FILE);

		try {

			if (propsFile.exists() && !(propsFile.isDirectory())) {
				input = new FileInputStream(propsFile);
				properties.load(input);
			} else {
				System.out.println("Properties file does not exists.");
			}
		} catch (IOException e) {
			System.out.println("Error reading properties file.");
			System.out.println(e.getMessage());
		}

		File file = new File(fileName);
		BufferedReader reader = null;
		StringBuilder builder = new StringBuilder();
		String inputLine;

		try {
			if (file.exists() && !(file.isDirectory())) {

				reader = new BufferedReader(new FileReader(file));

				while ((inputLine = reader.readLine()) != null) {
					Enumeration words = properties.keys();

					String wordToBeReplaced;
					String newWord;

					while ((wordToBeReplaced = words.nextElement().toString()) != null) {
						if (inputLine.contains(wordToBeReplaced)) {
							newWord = properties.getProperty(wordToBeReplaced);
							inputLine = inputLine.replaceAll(wordToBeReplaced, newWord);
						}
						if (!(words.hasMoreElements())) {
							break;
						}
					}

					builder.append(inputLine + "\n");
				}
			}
		} catch (NoSuchElementException e) {
			System.out.println("Cannot find element.");
		} catch (FileNotFoundException e) {
			System.out.println("Cannot find file.");
		} catch (IOException e) {
			System.out.println("Cannot read from file.");
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}

			} catch (IOException e) {
				System.out.println("Cannot close reader stream.");
			}
		}
		return builder;
	}

	/**
	 * A method for writing to the new file. Using our help method here, we
	 * guarantee that the new file will be with the required new name.
	 * 
	 * @param text
	 * @param fileName
	 * @param newFileName
	 */
	public void write(StringBuilder text, String fileName, String newFileName) {
		File file = new File(fileName);
		File fileToBeWritten = new File(newFileName);
		BufferedWriter writer = null;
		try {
			if (file.exists() && (!file.isDirectory()) && !(fileToBeWritten.exists())) {
				writer = new BufferedWriter(new FileWriter(fileToBeWritten));
				writer.write(text.toString());
				System.out.println("Writed to file successfully.");
			} else {
				System.out.println("The file with name " + file.getName() + " does not exists.");
				
			}
		} catch (IOException e) {
			System.out.println("Cannot read from file.");
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				System.out.println("Cannot close writer stream.");
			}
		}
	}

}
