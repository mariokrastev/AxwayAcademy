package httpClient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Creating a program that executes a REST request.
 * 
 * @author MarioKrastev
 */

public class HttpClientHomework {

	/**
	 * Declaring two constants: For the file that we want to write our output
	 * and for the URL we want to get the Response status and code.
	 * 
	 */

	private static final String OUTPUT_FILE_NAME = "output.txt";
	private static final String URL = "http://www.google.com";

	public static void main(String[] args) {

		getHTTPConnection();

	}

	/**
	 * A method to get Response and Status code for google.com and write the needed information into file.
	 */
	private static void getHTTPConnection() {
		File outputFile = new File(OUTPUT_FILE_NAME);
		FileWriter fw = null;
		BufferedWriter bw = null;
		BufferedReader br = null;
		InputStream is = null;
		InputStreamReader ir = null;
		HttpURLConnection connection = null;

		try {

			// initialize the resource

			URL url = new URL(URL);
			connection = (HttpURLConnection) url.openConnection();

			// set request method

			connection.setRequestMethod("GET");

			// execute the request and get response code

			int statusCode = connection.getResponseCode();

			StringBuilder sb = new StringBuilder();
			fw = new FileWriter(outputFile);
			bw = new BufferedWriter(fw);

			// append the status code and response message in StringBuilder

			sb.append("Status code: " + statusCode + "\n");
			sb.append("Response message: " + connection.getResponseMessage());
			sb.append("\n" + "RESPONSE:" + "\n");

			// initialize streams and append the Response in StringBuilder

			is = connection.getInputStream();
			ir = new InputStreamReader(is);
			br = new BufferedReader(ir);

			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}

			/*
			 * writing the status code, the response and response message into a
			 * file and printing a message
			 */

			bw.write(sb.toString());
			bw.flush();
			System.out.println("Status code, Response message and Response successfully written to file.");

		} catch (MalformedURLException e) {
			System.out.println("Cannot form URL");
		} catch (IOException e) {
			System.out.println("Problems opening connection.");
			e.printStackTrace();
		} finally {
			/*
			 * closing all streams
			 */
			try {
				if (connection != null) {
					connection.disconnect();
				}
				if (bw != null) {
					bw.close();
				}
				if (fw != null) {
					fw.close();
				}
				if (is != null) {
					is.close();
				}
				if (ir != null) {
					ir.close();
				}
				if (br != null) {
					br.close();
				}
			} catch (IOException ioe) {
				System.out.println("Cannot close streams.");
			}
		}
	}

}
