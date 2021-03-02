package com.solomko.repository;

import com.solomko.SQL.SqlHelper;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

@Repository
public class ProductRepository {
    private static Connection connection = SqlHelper.getConnection();

    public Map<String, Double> getProducts() {
        Map<String, Double> products = new HashMap<>();
        ResultSet rs = null;

        try {
            Statement st = connection.createStatement();
            rs = st.executeQuery("" +
                    "Select * from good");
            while (rs.next()) {
                String productTitle = rs.getString("title");
                String productPrice = rs.getString("price");
                products.put(productTitle, Double.parseDouble(productPrice));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return products;
    }


    /**
     *
     * @param productTitle
     * @return
     */
    public int getProductIdByTitle(String productTitle) {
        int productId = -1;
        ResultSet rs = null;

        try {
            PreparedStatement ps = connection.prepareStatement("" +
                    "Select * from good");
            rs = ps.executeQuery();
            while (rs.next()) {
                String productNameSql = rs.getString("title");
                if (productTitle.equals(productNameSql)) {
                    productId = rs.getInt("id");
                    break;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return productId;
    }
}