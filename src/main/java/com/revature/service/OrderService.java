package com.revature.service;

import com.revature.DAOs.*;
import com.revature.models.*;
import java.util.*;

public class OrderService {
    OrderDAO orderDAO;

    public OrderService() {
        orderDAO = new OrderDAO();
    }

    public OrderService(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    public Order addNewOrder(Order order) {
        try {
            return orderDAO.addOrder(order);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Order> retrieveAllOrders() {
        return orderDAO.getAllOrders();
    }

    public List<Order> retrieveAllOrdersByCustomerID(int customer_id) {
        return orderDAO.getAllOrdersByCustomerID(customer_id);
    }

    public Order deleteOrderByID(int order_id) {
        return orderDAO.deleteOrder(order_id);
    }
}
