package com.amazon.batch;

public class Product {

	private int id;
	private String name;
	private String category;
	private String brand;
	private double price;
	private double rating;
	private String color;
	private int quantity;

	public Product(int id, String name, String category, String brand, double price, double rating, String color,
			int quantity) {

		this.id = id;
		this.name = name;
		this.category = category;
		this.brand = brand;
		this.price = price;
		this.rating = rating;
		this.color = color;
		this.quantity = quantity;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getCategory() {
		return category;
	}

	public String getBrand() {
		return brand;
	}

	public double getPrice() {
		return price;
	}

	public double getRating() {
		return rating;
	}

	public String getColor() {
		return color;
	}

	public int getQuantity() {
		return quantity;
	}
}
