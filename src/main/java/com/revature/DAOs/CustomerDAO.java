package com.revature.DAOs;

import com.revature.models.Customer;
import com.revature.utils.ConnectionUtil;
import java.sql.*;
import java.util.*;

public class CustomerDAO {
    public Customer addCustomer(Customer customer) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String sql = "INSERT INTO Customers (name) VALUES (?);";

            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, customer.getName());

            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                int generated_customer_id = (int) resultSet.getLong(1);
                return new Customer(generated_customer_id, customer.getName());
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Customer updateCustomer(int customer_id, String name) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String sql = "UPDATE Customers SET name = ? WHERE customer_id = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, customer_id);

            preparedStatement.executeUpdate();
            return getCustomerByID(customer_id);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Customer getCustomerByID(int customer_id) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM Customers WHERE customer_id = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, customer_id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Customer customer = new Customer(resultSet.getInt("customer_id"),
                        resultSet.getString("name"));
                return customer;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}
