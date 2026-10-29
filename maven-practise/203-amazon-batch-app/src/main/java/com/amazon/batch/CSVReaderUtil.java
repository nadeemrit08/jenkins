package com.amazon.batch;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CSVReaderUtil {

	public static List<Product> readProducts(String filePath) throws Exception {

		List<Product> products = new ArrayList<>();

		BufferedReader br = new BufferedReader(new FileReader(filePath));

		String line;
		
		// Skip the first line (header)
		br.readLine();

		while ((line = br.readLine()) != null) {

			String[] data = line.split(",");

			Product p = new Product(

					Integer.parseInt(data[0]), data[1], data[2], data[3], Double.parseDouble(data[4]),
					Double.parseDouble(data[5]), data[6], Integer.parseInt(data[7])

			);

			products.add(p);

		}

		br.close();

		return products;

	}

}
