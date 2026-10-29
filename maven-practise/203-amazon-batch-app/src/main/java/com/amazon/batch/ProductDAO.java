package com.amazon.batch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public class ProductDAO {

	public void saveProducts(List<Product> products) throws Exception {

		String sql = "INSERT INTO product VALUES(?,?,?,?,?,?,?,?)";

		Connection con = DBConnection.getConnection();

		con.setAutoCommit(false);

		PreparedStatement ps = con.prepareStatement(sql);

		int batchSize = 1000;

		int count = 0;

		try {

			for (Product p : products) {

				ps.setInt(1, p.getId());
				ps.setString(2, p.getName());
				ps.setString(3, p.getCategory());
				ps.setString(4, p.getBrand());
				ps.setDouble(5, p.getPrice());
				ps.setDouble(6, p.getRating());
				ps.setString(7, p.getColor());
				ps.setInt(8, p.getQuantity());

				ps.addBatch();

				count++;

				if (count % batchSize == 0) {

					ps.executeBatch();

					con.commit();

					System.out.println(count + " Records Uploaded");

				}

			}

			ps.executeBatch();

			con.commit();

			System.out.println("Remaining Records Uploaded");

		}

		catch (Exception e) {

			con.rollback();

			e.printStackTrace();

		}

		finally {

			ps.close();

			con.close();

		}

	}

}
