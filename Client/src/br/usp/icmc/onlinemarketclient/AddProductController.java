package br.usp.icmc.onlinemarketclient;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;

import java.io.IOException;


public class AddProductController extends Dialog<String[]> {

    @FXML
    private TextField name;
    @FXML
    private TextField id;
    @FXML
    private TextField price;
    @FXML
    private TextField bestBefore;
    @FXML
    private TextField amount;

    public AddProductController(){
	    FXMLLoader loader = new FXMLLoader(
		    getClass().getResource("AddProduct.fxml")
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
			    String[] s = new String[5];

			    s[0] = id.getText();
			    s[1] = name.getText();
			    s[2] = price.getText();
			    s[3] = bestBefore.getText();
			    s[4] = amount.getText();

			    return s;
		    }

	    );
    }



}
