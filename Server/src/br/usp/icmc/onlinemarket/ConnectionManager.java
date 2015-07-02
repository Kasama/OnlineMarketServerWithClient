package br.usp.icmc.onlinemarket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ConnectionManager {

	static ConnectionManager instance = null;
	static ServerSocket serverSocket = null;
	ArrayList<Socket> activeConnections;
	private boolean listening;

	private ConnectionManager(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		activeConnections = new ArrayList<>();
	}

	public static ConnectionManager getInstance() throws IOException {

		if (instance == null) {
			instance = new ConnectionManager(5050);
		}
		return instance;

	}

	public static ConnectionManager getInstance(int port) throws IOException {

		if (instance == null) {
			instance = new ConnectionManager(port);
		}
		return instance;

	}

	public void listen() throws IOException {
		listening = true;
		Thread t = new Thread(
			() -> {
				while (listening) {
					try {
						Socket s = serverSocket.accept();
						Thread thread = new Thread(new CommandParser(s));
						thread.setDaemon(true);
						thread.start();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		);
		t.setDaemon(true);
		t.start();
	}

	public void stopListening() {
		listening = false;
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
