package br.usp.icmc.onlinemarketclient;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ConnectionController {

	static ConnectionController instance = null;
	Socket s;
	Scanner in;
	PrintWriter out;

	private ConnectionController(){

	}

	public String generateOutputString(String[] string, String command) {
		StringBuilder sb = new StringBuilder();
		sb.append("<");
		sb.append(command);
		sb.append("|");
		for (int i = 0; i < string.length; i++) {
			sb.append(string[i]);

			if (i == string.length - 1) sb.append(">");
			else sb.append("|");
		}
		return sb.toString();
	}


	public static ConnectionController getInstance(){
		if (instance == null)
			instance = new ConnectionController();
		return instance;
	}

	public void createConnection(String ip, int port) {
		try {
			if (s != null && s.isConnected())
				s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		s = null;
		try {
			s = new Socket(ip, port);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (s != null) {
			try {
				out = new PrintWriter(s.getOutputStream(), true);
				in = new Scanner(s.getInputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public String getMessageFromServer() {
		return in.nextLine();
	}

	public String[] parseServerResponse(String s) {
		return s.substring(s.indexOf("<") + 1, s.lastIndexOf(">")).split
			("\\|");
	}

	public void println(String text) {
		out.println(text);
	}
}
