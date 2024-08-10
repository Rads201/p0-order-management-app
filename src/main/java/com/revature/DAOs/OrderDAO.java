package com.revature.DAOs;

import com.revature.models.Order;
import com.revature.utils.ConnectionUtil;
import java.sql.*;
import java.util.*;

public class OrderDAO {

    // Selecting all orders from the order table
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();

        try (Connection connection = ConnectionUtil.getConnection()){
            String sql = "SELECT * FROM Orders;";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order(resultSet.getInt("order_id"),
                        resultSet.getString("name"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("customer_id_fk"));
                orders.add(order);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return orders;
    }

    // Selecting all orders belonging to certain customer
    public List<Order> getAllOrdersByCustomerID(int customer_id) {
        List<Order> orders = new ArrayList<>();

        try (Connection connection = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM orders WHERE orders.customer_id_fk = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, customer_id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Order order = new Order(resultSet.getInt("order_id"),
                        resultSet.getString("name"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("customer_id_fk"));
                orders.add(order);
            }
            return orders;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public Order addOrder(Order order) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String sql = "INSERT INTO Orders (name, price, customer_id_fk) VALUES (?, ?, ?);";

            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, order.getName());
            preparedStatement.setDouble(2, order.getPrice());
            preparedStatement.setInt(3, order.getCustomer_id_fk());

            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                int generated_order_id = (int) resultSet.getLong(1);
                return new Order(generated_order_id, order.getName(), order.getPrice(), order.getCustomer_id_fk());
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Order getOrderByID(int order_id) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM Orders WHERE order_id = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, order_id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Order order = new Order(resultSet.getInt("order_id"),
                        resultSet.getString("name"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("customer_id_fk"));
                return order;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Order deleteOrder(int order_id) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            Order order = getOrderByID(order_id);
            if (order == null) {
                return null;
            }

            String sql = "DELETE FROM orders WHERE order_id = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, order_id);

            preparedStatement.executeUpdate();
            return order;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}
