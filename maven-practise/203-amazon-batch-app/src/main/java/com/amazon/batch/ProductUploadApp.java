package com.amazon.batch;

import java.util.List;

public class ProductUploadApp {

	public static void main(String[] args) {

		try {

			String filePath = "products.csv";

			List<Product> products = CSVReaderUtil.readProducts(filePath);

			ProductDAO dao = new ProductDAO();

			dao.saveProducts(products);

			System.out.println("Upload Completed Successfully");

		}

		catch (Exception e) {

			e.printStackTrace();

		}

	}

}
