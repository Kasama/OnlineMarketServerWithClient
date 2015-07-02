package br.usp.icmc.onlinemarket;

import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

public class Product extends Observable {

	long id;
	String name;
	double price;
	String bestBefore;
	long amount;
	long provider;

	Vector<Observer> obs;

	public Product(
		long id, String name, double price, String bestBefore, long amount, long provider
	) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.bestBefore = bestBefore;
		this.amount = amount;
		this.provider = provider;
		this.obs = new Vector<>();
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}

	public String getBestBefore() {
		return bestBefore;
	}

	public boolean isUnavailable(){
		return !(amount > 0);
	}

	public boolean isAvailable(){
		return amount > 0;
	}

	public long getAmount() {
		return amount;
	}

	public void increaseAmount(int amount){
		this.amount += amount;
	}

	public void decreaseAmount(int amount){
		this.amount -= amount;
	}

	public Vector<Observer> getObservers(){
		return obs;
	}

	@Override
	public synchronized void addObserver(Observer o) {
		super.addObserver(o);
		obs.add(o);
	}

	@Override
	public synchronized void deleteObserver(Observer o) {
		super.deleteObserver(o);
		obs.remove(o);
	}

	@Override
	public synchronized void deleteObservers() {
		super.deleteObservers();
		obs.removeAllElements();
	}

	@Override
	public void notifyObservers(Object arg) {
//		super.notifyObservers(arg);
		obs.forEach(
			o -> o.update(this, arg)
		);
		System.out.println("notified");
	}

	public void notifyProvider(User provider){
		if (provider.getId() != this.getProvider())
			return;
		provider.update(this, new Integer(2));
	}

	public long getProvider() {
		return provider;
	}
}
