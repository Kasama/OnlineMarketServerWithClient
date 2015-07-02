package br.usp.icmc.onlinemarketclient;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert;


public class OnlineMarketController {

	@FXML
	private TextField search;
	@FXML
	private Button addProductButton;
	@FXML
	private Button refreshButton;
	@FXML
	private Button subscribeButton;
	@FXML
	private Button buyButton;
	@FXML
	private TableColumn<ObservableProduct, String> tableProductId;
	@FXML
	private TableColumn<ObservableProduct, String> tableProductName;
	@FXML
	private TableColumn<ObservableProduct, String> tableProductPrice;
	@FXML
	private TableColumn<ObservableProduct, String> tableProductBestBefore;
	@FXML
	private TableColumn<ObservableProduct, String> tableProductProvider;
	@FXML
	private TableColumn<ObservableProduct, String> tableProductStatus;
	@FXML
	private TableColumn<ObservableProduct, String> tableProductAmount;
	@FXML
	private TableView<ObservableProduct> table;

	private ObservableList<ObservableProduct> productList;
	private ConnectionController connection;
	private String token;

	public void setArguments(String token) {
		this.token = token;
	}

	@FXML
	public void addProductButtonOnAction(ActionEvent event) {
		Dialog<String[]> add = new AddProductController();
		String[] toAdd = add.showAndWait().orElse(null);
		String[] toSend = new String[toAdd.length + 1];
		toSend[0] = token;
		System.arraycopy(toAdd, 0, toSend, 1, toAdd.length);
		connection.println(
			connection.generateOutputString(
				toSend, "addProduct"
			)
		);
		String[] res = connection.parseServerResponse(
			connection.getMessageFromServer()
		);
		if (res[0].equals(String.valueOf(true))) {
			new Alert(
				Alert.AlertType.INFORMATION,
				"Product added successfully",
				ButtonType.OK
			).show();
		} else {
			new Alert(
				Alert.AlertType.INFORMATION,
				res[1],
				ButtonType.OK
			).show();
		}
		refreshButtonOnAction(null);
	}

	@FXML
	public void refreshButtonOnAction(ActionEvent event) {
		productList = FXCollections.observableArrayList();
		connection = ConnectionController.getInstance();
		connection.println("<request|all>");
		String[] resp = connection.parseServerResponse(
			connection.getMessageFromServer()
		);
		int nItems = resp.length / 7;
		for (int i = 0; i < nItems; i++) {
			String[] toObs = new String[7];
			System.arraycopy(resp, (i * 7), toObs, 0, 7);
			productList.add(new ObservableProduct(toObs));
		}
		table.setItems(productList);
	}

	@FXML
	public void subButtonOnAction(ActionEvent event) {
		//TODO remove from sub once done
		String id;
		id = table.getSelectionModel().getSelectedItem().getID();
		connection.println("<subscribe|" + token + "|" + id + ">");
		String[] res = connection
			.parseServerResponse(connection.getMessageFromServer());
		if (res[0].equals(String.valueOf(true))) {
			new Alert(
				Alert.AlertType.INFORMATION,
				"Subscribed to product " + id,
				ButtonType.OK
			).show();
		} else {
			new Alert(
				Alert.AlertType.INFORMATION,
				"Could not subscribe, the product may alredy be available" +
				id,
				ButtonType.OK
			).show();
		}
	}

	@FXML
	public void buyButtonOnAction(ActionEvent event) {
		String id;
		String amount;

		id = table.getSelectionModel().getSelectedItem().getID();

		TextInputDialog input;
		input = new TextInputDialog("amount");
		input.setHeaderText(
			"Input an amount of '" + table.getSelectionModel()
				.getSelectedItem().getName() + "' to buy"
		);
		input.setTitle("input amount");
		input.setContentText("amount");
		amount = input.showAndWait().orElse("");
		if (amount.equals(""))
			return;
		try {
			int am = Integer.parseInt(amount);
		} catch (Exception e) {
			return;
		}
		connection.println("<buy|" + token + "|" + id + "|" + amount + ">");
		String[] m = connection.parseServerResponse(
			connection.getMessageFromServer()
		);
		if (m[0].equals(String.valueOf(false))) {
			StringBuilder sb = new StringBuilder();
			for (int i = 1; i < m.length; i++) {
				sb.append(m[i]);
				if (i != m.length - 1) sb.append(", ");
			}
			new Alert(
				Alert.AlertType.WARNING,
				"Some items could not be bought: \"" +
				sb.toString() + "\"",
				ButtonType.OK
			).show();
		}
		refreshButtonOnAction(null);
	}

	public void initComponents() {
		refreshButtonOnAction(null);
		tableProductStatus
			.setCellValueFactory(v -> v.getValue().statusProperty());
		tableProductProvider
			.setCellValueFactory(v -> v.getValue().providerProperty());
		tableProductAmount
			.setCellValueFactory(v -> v.getValue().amountProperty());
		tableProductBestBefore
			.setCellValueFactory(v -> v.getValue().bestBeforeProperty());
		tableProductId.setCellValueFactory(v -> v.getValue().IDProperty());
		tableProductPrice
			.setCellValueFactory(v -> v.getValue().priceProperty());
		tableProductName.setCellValueFactory(v -> v.getValue().nameProperty());
	}

}
