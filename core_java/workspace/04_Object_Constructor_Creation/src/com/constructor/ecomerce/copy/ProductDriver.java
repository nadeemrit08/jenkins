package com.constructor.ecomerce.copy;

class Product {

	int productid;
	String productName;
	double price;
	int quantity;
	String category;
	boolean inStock;
	float rating;
	long sellerContact;

	// Constructor 1
	public Product(int productid, String productName, double price) {
		this.productid = productid;
		this.productName = productName;
		this.price = price;
	}

	// Constructor 2
	public Product(int productid, String productName, double price, int quantity, String category) {

		this(productid, productName, price);

		this.quantity = quantity;
		this.category = category;
	}

	// Constructor 3
	public Product(int productid, String productName, double price, int quantity, String category, boolean inStock,
			float rating, long sellerContact) {

		this(productid, productName, price, quantity, category);

		this.inStock = inStock;
		this.rating = rating;
		this.sellerContact = sellerContact;
	}

	public void display() {
		System.out.println("------------------------------");
		System.out.println("Product ID      : " + productid);
		System.out.println("Product Name    : " + productName);
		System.out.println("Price           : " + price);
		System.out.println("Quantity        : " + quantity);
		System.out.println("Category        : " + category);
		System.out.println("In Stock        : " + inStock);
		System.out.println("Rating          : " + rating);
		System.out.println("Seller Contact  : " + sellerContact);
	}
}

public class ProductDriver {

	public static void main(String[] args) {

		Product product1 = new Product(101, "Pen", 10);

		Product product2 = new Product(102, "Cup", 120, 4, "Kitchen");

		Product product3 = new Product(103, "Laptop", 50000, 5, "Electronics", true, 4.5f, 8859245475L);

		product1.display();
		product2.display();
		product3.display();
	}
}