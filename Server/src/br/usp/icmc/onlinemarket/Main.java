package br.usp.icmc.onlinemarket;

import java.io.IOException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args){
		try {
			ConnectionManager cm;
			if (args.length == 1){
				cm = ConnectionManager.getInstance(Integer.parseInt(args[0]));
			}else{
                cm = ConnectionManager.getInstance();
			}
			cm.listen();
			new Scanner(System.in).next();
		} catch (IOException e) {
			System.err.println("Impossible to host a connection");
		}
	}

}
