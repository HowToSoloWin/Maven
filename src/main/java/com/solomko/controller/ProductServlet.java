package com.solomko.controller;

import com.solomko.Spring.SpringContext;
import com.solomko.domain.Order;
import com.solomko.domain.User;
import com.solomko.repository.OrderRepository;
import com.solomko.service.OrderService;
import com.solomko.service.ProductService;
import com.solomko.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.*;
import java.io.IOException;

@WebServlet (name = "ProductServlet", urlPatterns = "/selectproduct")
public class ProductServlet extends HttpServlet {

    private UserService userService;
    private OrderService orderService;
    private OrderRepository orderRepository;
    private ProductService productService;

    @Override
    public void init() {
        AnnotationConfigApplicationContext context = SpringContext.getApplicationContext();
        this.userService = (UserService) context.getBean(UserService.class);
        this.orderService = (OrderService) context.getBean(OrderService.class);
        this.orderRepository = (OrderRepository) context.getBean(OrderRepository.class);
        this.productService = (ProductService) context.getBean(ProductService.class);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String customer;
        if (session.getAttribute("customer") == null && req.getParameter("customer") != null) {
            session.setAttribute("customer", req.getParameter("customer"));
            customer = session.getAttribute("customer").toString();
        } else {
            customer = session.getAttribute("customer").toString();
        }
        User user = userService.createOrGetUser(customer);
        Order order = orderService.createOrGetOrder(user);

    if(req.getParameter("name")!=null)
    {
        int idDeleteProduct = Integer.parseInt(req.getParameter("remove"));
        order = orderService.deleteProduct(order, idDeleteProduct);
    } else {
        if (req.getParameterValues("selected") != null) {
            order = orderService.addProducts(order, req.getParameterValues("selected"));
        }
    }
    order.setProducts(orderRepository.getProductsByOrder(order));
    req.setAttribute("products",productService.getAllProducts());
    req.setAttribute("order",order);
    req.getRequestDispatcher("WEB-INF/jsp/selectproduct.jsp").forward(req, resp);
    }
}
