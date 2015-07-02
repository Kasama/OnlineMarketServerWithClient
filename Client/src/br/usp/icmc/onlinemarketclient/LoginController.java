package br.usp.icmc.onlinemarketclient;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;


public class LoginController {
	@FXML
	private TextField username;
	@FXML
	private PasswordField password;
	@FXML
	private TextField port;
	@FXML
	private TextField ip;
	@FXML
	private Button logIn;
	@FXML
	private Button signUp;

	private ConnectionController connection;

	public LoginController() {
		connection = ConnectionController.getInstance();
	}

	@FXML
	public void logIn() {

		connection.createConnection(
			ip.getText(), Integer.parseInt(port.getText())
		);
		connection.println(
			"<" + "login" + "|" + username.getText() + "|" +
			password.getText() + ">"
		);
		String[] resp = connection.parseServerResponse(
			connection.getMessageFromServer()
		);
		String token = resp[0];
		if (token.equals("")) {
			new Alert(
				Alert.AlertType.ERROR, "Incorrect login information",
				ButtonType.OK
			).show();
		} else {
			switchToOnlineMarket(token);
		}
	}

	private void switchToOnlineMarket(String token) {
		FXMLLoader loader = new FXMLLoader(
			(getClass().getResource("OnlineMarket.fxml"))
		);

		Parent rt = null;
		try {
			rt = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		OnlineMarketController controller = loader.getController();
		controller.setArguments(token);
		controller.initComponents();

		Scene signUpScene = new Scene(rt);
		Stage st = (Stage) signUp.getScene().getWindow();
		st.setScene(signUpScene);
		st.show();
	}


	@FXML
	public void signUp(ActionEvent event) {

		connection.createConnection(
			ip.getText(), Integer.parseInt(port.getText())
		);
		Dialog<String[]> dialog = new SignUpController();
		String[] s = dialog.showAndWait().orElse(null);
		if (s[0].equals(""))
			return;
		connection.println(connection.generateOutputString(s, "newuser"));
		String str = connection.getMessageFromServer();
		String[] resp = connection.parseServerResponse(str);
		if (!Boolean.parseBoolean(resp[0])) {
			new Alert(Alert.AlertType.WARNING, resp[1], ButtonType.OK)
				.show();
		}
	}
}
