package br.usp.icmc.onlinemarketclient;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.io.IOException;


public class SignUpController extends Dialog<String[]> {

	@FXML
	private TextField id;
	@FXML
    private TextField username;
    @FXML
    private TextField name;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField passwordAuthenticate;
    @FXML
    private TextField phone;
    @FXML
    private TextField email;
    @FXML
    private TextField address;
    @FXML
    private RadioButton customerButton;
    @FXML
    private RadioButton providerButton;


	public SignUpController() {
		FXMLLoader loader = new FXMLLoader(
			getClass().getResource("SignUp.fxml")
		);
		loader.setController(this);
		this.getDialogPane().getStylesheets()
			.add("style.css");

		ButtonType buttonCancel = new ButtonType(
			"Cancel", ButtonBar.ButtonData.CANCEL_CLOSE
		);
		ButtonType buttonOK = new ButtonType(
			"Confirm", ButtonBar.ButtonData.OK_DONE
		);
		Parent root;
		try {
			root = loader.load();
			this.getDialogPane().setContent(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.getDialogPane().getButtonTypes().addAll(
			buttonCancel, buttonOK
		);
		this.setResultConverter(
			param -> {
				String[] s = new String[8];

				s[0] = username.getText();
				s[1] = name.getText();
				s[2] = address.getText();
				s[3] = phone.getText();
				s[4] = email.getText();
				s[5] = id.getText();
				s[6] = password.getText();
				if (customerButton.isSelected() && !providerButton.isSelected()){
					s[7] = "customer";
				} else if (!customerButton.isSelected() && providerButton
					.isSelected()){
					s[7] = "provider";
				}else{
					s[7] = "customerAndProvider";
				}

				return s;
			}

		);
	}




}

