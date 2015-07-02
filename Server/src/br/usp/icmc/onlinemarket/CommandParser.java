package br.usp.icmc.onlinemarket;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class CommandParser implements Runnable {
	Scanner in;
	PrintWriter out;
	Market market;

	public CommandParser(Socket s) {
		try {
			in = new Scanner(s.getInputStream());
			out = new PrintWriter(s.getOutputStream(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		market = Market.getInstance();

	}

	@Override
	public void run() {

		String s;
		String[] tokens;

		while (in.hasNext()) {
			s = in.nextLine();
			if (s.startsWith("<") && s.endsWith(">")) {
				tokens = s.substring(
					s.indexOf("<") + 1, s.lastIndexOf(">")
				).split("\\|");
				handleCommand(tokens);
			}
		}

	}

	private void handleCommand(String[] tokens) {

		switch (tokens[0]) {
			case "newuser":
				handleNewUser(tokens);
				break;
			case "login":
				handleLogin(tokens);
				break;
			case "request":
				handleRequest(tokens);
				break;
			case "subscribe":
				handleSubscribe(tokens);
				break;
			case "buy":
				handleBuy(tokens);
				break;
			case "addProduct":
				handleAddProduct(tokens);
				break;
		}

	}

	private String generateOutputString(String[] string) {
		StringBuilder sb = new StringBuilder();
		sb.append("<");
		for (int i = 0; i < string.length; i++) {
			sb.append(string[i]);

			if (i == string.length - 1) sb.append(">");
			else sb.append("|");
		}
		return sb.toString();
	}

	private void handleAddProduct(String[] tokens) {
		final int TOKEN = 1;
		final int ID = 2;
		final int NAME = 3;
		final int PRICE = 4;
		final int BESTBEFORE = 5;
		final int AMOUNT = 6;
		final int nItems = (tokens.length - 2) / 5;

		String[] productID = new String[nItems];
		String[] name = new String[nItems];
		String[] price = new String[nItems];
		String[] bestBefore = new String[nItems];
		String[] amount = new String[nItems];
		int j = 0;
		for (int i = 2; i < tokens.length; i += 5) {
			productID[j] = tokens[i];
			name[j] = tokens[i + 1];
			price[j] = tokens[i + 2];
			bestBefore[j] = tokens[i + 3];
			amount[j] = tokens[i + 4];
			j++;
		}
		String[] received = market.addProduct(
			tokens[TOKEN], productID, name, price, bestBefore, amount
		);

		out.println(generateOutputString(received));
	}

	private void handleBuy(String[] tokens) {
		final int TOKEN = 1;
		final int nItems = (tokens.length - 2) / 2;
		String[] productID;
		productID = new String[nItems];
		String[] amount;
		amount = new String[nItems];
		int i = 0, j = 0;
		for (int k = 2; k < tokens.length; k++) {
			if (k % 2 == 0) productID[i++] = tokens[k];
			else amount[j++] = tokens[k];
		}

		String[] received = market.buyProduct(tokens[TOKEN], productID,
		                                      amount);
		System.out.println(generateOutputString(received));
		out.println(generateOutputString(received));
	}

	private void handleSubscribe(String[] tokens) {
		final int TOKEN = 1;
		final int ID = 2;

		String[] received = market.subscribe(tokens[TOKEN], tokens[ID]);

		out.println(generateOutputString(received));
	}

	private void handleRequest(String[] tokens) {
		final int MODE = 1;

		String[] received = market.request(tokens[MODE]);

		out.println(generateOutputString(received));
	}

	private void handleNewUser(String[] tokens) {
		final int USERNAME = 1;
		final int NAME = 2;
		final int ADDRESS = 3;
		final int TELEPHONE = 4;
		final int EMAIL = 5;
		final int ID = 6;
		final int PASSWORD = 7;
		final int TYPE = 8;

		String[] received = market.signUp(
			tokens[USERNAME], tokens[NAME], tokens[ADDRESS],
			Long.parseLong(tokens[TELEPHONE]),
			tokens[EMAIL], Long.parseLong(tokens[ID]), tokens[PASSWORD],
			tokens[TYPE]
		);

		out.println(generateOutputString(received));
	}

	private void handleLogin(String[] tokens) {
		final int USER = 1;
		final int PASSWORD = 2;

		String[] received = market.login(tokens[USER], tokens[PASSWORD]);

		out.println(generateOutputString(received));
	}
}
