package br.usp.icmc.onlinemarketclient;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ObservableProduct {
	StringProperty ID;
	StringProperty name;
	StringProperty price;
	StringProperty bestBefore;
	StringProperty provider;
	StringProperty status;
	StringProperty amount;

	public ObservableProduct(String[] tokens) {
		ID = new SimpleStringProperty(tokens[0]);
		name = new SimpleStringProperty(tokens[1]);
		price = new SimpleStringProperty(tokens[2]);
		bestBefore = new SimpleStringProperty(tokens[3]);
		provider = new SimpleStringProperty(tokens[4]);
		status = new SimpleStringProperty(tokens[5]);
		amount = new SimpleStringProperty(tokens[6]);
	}

	public String getID() {
		return ID.get();
	}

	public StringProperty IDProperty() {
		return ID;
	}

	public void setID(String ID) {
		this.ID.set(ID);
	}

	public String getName() {
		return name.get();
	}

	public StringProperty nameProperty() {
		return name;
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public String getPrice() {
		return price.get();
	}

	public StringProperty priceProperty() {
		return price;
	}

	public void setPrice(String price) {
		this.price.set(price);
	}

	public String getBestBefore() {
		return bestBefore.get();
	}

	public StringProperty bestBeforeProperty() {
		return bestBefore;
	}

	public void setBestBefore(String bestBefore) {
		this.bestBefore.set(bestBefore);
	}

	public String getProvider() {
		return provider.get();
	}

	public StringProperty providerProperty() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider.set(provider);
	}

	public String getStatus() {
		return status.get();
	}

	public StringProperty statusProperty() {
		return status;
	}

	public void setStatus(String status) {
		this.status.set(status);
	}

	public String getAmount() {
		return amount.get();
	}

	public StringProperty amountProperty() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount.set(amount);
	}
}

