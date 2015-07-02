package br.usp.icmc.onlinemarket;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.sun.istack.internal.NotNull;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class DataManager {

	private ArrayList<User> userTable;
	private ArrayList<Product> productTable;
	File usersFile;
	File productsFile;

	public DataManager(
		@NotNull
		File directory
	) throws
	  IllegalArgumentException {

		if (directory.exists() && !directory.isDirectory())
			throw new IllegalArgumentException();
		else
			directory.mkdir();
		File usersFile;
		File productsFile;

		usersFile = new File(directory.getPath() + "/users.csv");
		productsFile = new File(directory.getPath() + "/products.csv");

		if (!usersFile.exists()) {
			try {
				usersFile.createNewFile();
			} catch (IOException e) {
				System.err.println(
					"Could not create file '" + usersFile.getAbsolutePath() +
					"'. maybe you don't have permission to write in this " +
					"folder"
				);
				System.exit(1);
			}
		}
		if (!productsFile.exists()) {
			try {
				productsFile.createNewFile();
			} catch (IOException e) {
				System.err.println(
					"Could not create file " + productsFile.getName() +
					". maybe you don't have permission to write in this folder"
				);
				System.exit(1);
			}
		}

		this.usersFile = usersFile;
		this.productsFile = productsFile;
		userTable = new ArrayList<>();
		productTable = new ArrayList<>();

		loadFromCsv(usersFile, productsFile);

	}

	private synchronized void loadFromCsv(File usersFile, File productsFile) {

		try {
			CSVReader csvReader = new CSVReader(new FileReader(usersFile));

			csvReader.forEach(
				tokens -> userTable.add(
					new User(
						tokens[0],
						tokens[1],
						tokens[2],
						tokens[3],
						tokens[4],
						Long.parseLong(tokens[5]),
						Long.parseLong(tokens[6]),
						tokens[7]
					)
				)
			);

			csvReader = new CSVReader(new FileReader(productsFile));

			csvReader.forEach(
				tokens -> productTable.add(
					new Product(
						Long.parseLong(tokens[0]),
						tokens[1],
						Double.parseDouble(tokens[2]),
						tokens[3],
						Long.parseLong(tokens[4]),
						Long.parseLong(tokens[5])
					)
				)
			);

			for (Product product : productTable) {
				File file = new File(
					productsFile.getParent() + "/products/" + product.getId() +
					".csv"
				);
				if (file.exists()) {
					csvReader = new CSVReader(new FileReader(file));
					csvReader.forEach(
						tokens -> product.addObserver(
							this.getUserById(Integer.parseInt(tokens[0]))
						)
					);
				}
			}

		} catch (IOException e) {
			System.err.println(
				"something went wrong when reading from files," +
				" check if you have read access in this directory"
			);
			System.exit(1);
		}
	}

	public User getUserById(long id) {
		return userTable.stream()
			.filter(u -> u.getId() == id)
			.findFirst()
			.orElse(null);
	}

	public User verifyLogin(String userName, String passHash) {

		return userTable.stream()
			.filter(user -> user.getUserName().equals(userName))
			.filter(user -> user.getPasswordMd5().equals(passHash))
			.findFirst()
			.orElse(null);

	}

	public synchronized void writeToCsv() {

		try {

			CSVWriter csvWriter;

			List<String[]> toWrite;

			for (Product product : productTable) {
				File productsFolder = new File(
					productsFile.getParent()
					+ "/products"
				);
				productsFolder.mkdir();
				File file = new File(
					productsFolder.getPath() + "/" + product.getId() +
					".csv"
				);
				if (!file.exists()) {
					file.createNewFile();
				}
				csvWriter = new CSVWriter(new FileWriter(file));

				Vector<Observer> u = product.getObservers();
				List<String[]> l = u.stream()
					.map(
						obs -> (User) obs
					)
					.map(
						usr -> {
							String[] str = new String[1];
							str[0] = String.valueOf(usr.getId());
							return str;
						}
					)
					.collect(Collectors.toList());
				csvWriter.writeAll(l);
				csvWriter.flush();

			}

			toWrite = userTable.stream()
				.map(
					user -> {
						String[] str = new String[8];
						str[0] = user.getUserName();
						str[1] = user.getName();
						str[2] = user.getEmail();
						str[3] = user.getAddress();
						str[4] = user.getPasswordMd5();
						str[5] = Long.toString(user.getPhoneNumber());
						str[6] = Long.toString(user.getId());
						str[7] = user.getType();
						return str;
					}
				).collect(Collectors.toList());

			csvWriter = new CSVWriter(new FileWriter(usersFile));
			csvWriter.writeAll(toWrite);
			csvWriter.flush();

			toWrite = productTable.stream()
				.map(
					product -> {
						String[] str = new String[6];
						str[0] = String.valueOf(product.getId());
						str[1] = product.getName();
						str[2] = String.valueOf(product.getPrice());
						str[3] = product.getBestBefore();
						str[4] = String.valueOf(product.getAmount());
						str[5] = String.valueOf(product.getProvider());
						return str;
					}
				).collect(Collectors.toList());
			csvWriter = new CSVWriter(new FileWriter(productsFile));
			csvWriter.writeAll(toWrite);
			csvWriter.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public synchronized boolean addUser(
		String userName, String name, String email, String address,
		String passwordMd5, long phoneNumber, long id, String type
	) {
		Optional<User> opUser = userTable.stream()
			.filter(user -> user.getId() == id)
			.findFirst();
		if (opUser.isPresent()) return false;

		userTable.add(
			new User(
				userName,
				name,
				email,
				address,
				passwordMd5,
				phoneNumber,
				id,
				type
			)
		);
		writeToCsv();

		return true;
	}

	public List<Product> getAvailableProducts() {
		return productTable.stream()
			.filter(Product::isAvailable)
			.collect(Collectors.toList());
	}

	public List<Product> getUnavailableProducts() {
		return productTable.stream()
			.filter(Product::isUnavailable)
			.collect(Collectors.toList());
	}

	public List<Product> getAllProducts() {
		return productTable;
	}

	public User getUserByUsername(String username) {
		return userTable.stream()
			.filter(u -> u.getUserName().equals(username))
			.findFirst()
			.orElse(null);
	}

	public Product getProductById(long id) {
		return productTable.stream()
			.filter(u -> u.getId() == id)
			.findFirst()
			.orElse(null);
	}

	public synchronized boolean addProduct(
		Long id, String name, Double price, String bestBefore,
		Long amount, Long provider
	) {
		Optional<Product> opProduct = productTable.stream()
			.filter(product -> product.getId() == id)
			.findFirst();
		if (opProduct.isPresent()) return false;

		productTable.add(
			new Product(
				id,
				name,
				price,
				bestBefore,
				amount,
				provider
			)
		);
		writeToCsv();
		return true;
	}

	public void increaseAmountOfProduct(int id, int amount) {
		Product p = getProductById(id);
		p.increaseAmount(amount);
		writeToCsv();
	}

	public void decreaseAmountOfProduct(int id, int amount) {
		Product p = getProductById(id);
		p.decreaseAmount(amount);
		writeToCsv();
	}

	public void addObserverToProduct(long id, User user) {
		getProductById(id).addObserver(user);
		writeToCsv();
	}
}