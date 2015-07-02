package br.usp.icmc.onlinemarketclient;

import javafx.scene.control.ButtonType;

public class Alert extends javafx.scene.control.Alert {
	public Alert(AlertType alertType) {
		super(alertType);
		this.getDialogPane().getStylesheets()
			.add("style.css");
	}

	public Alert(
		AlertType alertType, String contentText,
		ButtonType... buttons
	) {
		super(alertType, contentText, buttons);
		this.getDialogPane().getStylesheets()
			.add("style.css");
	}
}
