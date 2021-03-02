package com.solomko.repository;

import com.solomko.SQL.SqlHelper;
import com.solomko.domain.Order;
import com.solomko.domain.Product;
import com.solomko.domain.User;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderRepository {
    private static Connection connection = SqlHelper.getConnection();

    public Order getOrder(User user) {
        Order order = new Order();
        try {
            PreparedStatement ps = connection.prepareStatement("" +
                    "SELECT ORDERS.ID, USER.ID, USER.LOGIN, ORDERS.TOTAL_PRICE \n" +
                    "FROM ORDERS \n" +
                    "INNER JOIN USER\n" +
                    "ON USER.ID=ORDERS.USER_ID\n" +
                    "WHERE ORDERS.USER_ID=?;");
            ps.setInt(1, user.getUserId());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                order.setId(rs.getInt(1));
                order.setUserID(rs.getInt(2));
                order.setTotalPrice(rs.getDouble(4));

                User user1 = new User();
                user1.setUserId(rs.getInt(2));
                user1.setName(rs.getString(3));

                order.setUser(user1);

            } else {
                order = new Order(user);
                order = save(order);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }
    public Order save(Order order) {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("INSERT INTO ORDERS(USER_ID) VALUES (?)");
            ps.setInt(1, order.getUser().getUserId());
            ps.execute();

            ps = connection.prepareStatement("SELECT * from ORDERS where USER_ID=?;");
            ps.setInt(1, order.getUser().getUserId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                order.setId(rs.getInt("ID"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return order;
    }
    public Order updateTotalPrice(Double totalPrice, Order order) {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("UPDATE ORDERS SET TOTAL_PRICE = ? WHERE ID=?;");
            ps.setDouble(1, totalPrice);
            ps.setInt(2, order.getId());
            ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return order;
    }
    public void addProduct(Product product, Order order) {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("INSERT INTO ORDER_GOOD(ORDER_ID, GOOD_ID) VALUES (?,?)");
            ps.setInt(1, order.getId());
            ps.setInt(2, product.getId());
            ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void removeProduct(int productId, Order order) {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("" +
                    "DELETE FROM ORDER_GOOD " +
                    "WHERE ORDER_ID=? AND GOOD_ID=? " +
                    "LIMIT 1");
            ps.setInt(1, order.getId());
            ps.setInt(2, productId);
            ps.execute();


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public List<Product> getProductsByOrder(Order order) {
        List<Product> products = new ArrayList<>();
        ResultSet rs = null;
        try {
            PreparedStatement ps = connection.prepareStatement("" +
                    "SELECT GOOD.ID, GOOD.TITLE, GOOD.PRICE " +
                    "FROM ORDER_GOOD " +
                    "INNER JOIN GOOD " +
                    "ON ORDER_GOOD.GOOD_ID=GOOD.ID " +
                    "WHERE ORDER_GOOD.ORDER_ID=?");
            ps.setInt(1, order.getId());
            rs = ps.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt(1));
                product.setName(rs.getString(2));
                product.setPrice(rs.getDouble(3));

                products.add(product);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return products;
    }
}
