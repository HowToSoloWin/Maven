package com.solomko.service;


import com.solomko.domain.Order;
import com.solomko.domain.Product;
import com.solomko.domain.User;
import com.solomko.exception.InvalidArgumentException;
import com.solomko.repository.OrderRepository;
import com.solomko.repository.ProductRepository;
import com.solomko.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    public Order createOrGetOrder(User user) {
        if (user == null) {
            throw new InvalidArgumentException("Name can't be null");
        }
        Order order = orderRepository.getOrder(user);
        return order;
    }
    public Order addProducts(Order order, String[] selectedProducts) {
        if (order == null || selectedProducts == null) {
            throw new InvalidArgumentException("Arguments cant be null");
        }

        String productTitle = selectedProducts[0];
        int productId = productRepository.getProductIdByTitle(productTitle);

        Product product = new Product();
        product.setId(productId);
        product.setName(productTitle);
        product.setPrice(productService.getAllProducts().get(productTitle));

        orderRepository.addProduct(product, order);
        order.setProducts(orderRepository.getProductsByOrder(order));
        updateOrderTotalPrice(order);

        return order;
    }
    public Order deleteProduct(Order order, int productId) {
        if (order == null) {
            throw new InvalidArgumentException("Arguments cant be null");
        }

        orderRepository.removeProduct(productId, order);
        order.setProducts(orderRepository.getProductsByOrder(order));
        updateOrderTotalPrice(order);

        return order;
    }
    public Order updateOrderTotalPrice(Order order) {
        double totalPrice = 0;
        for (Product product : order.getProducts()) {
            totalPrice += product.getPrice();
        }
        orderRepository.updateTotalPrice(totalPrice, order);

        return order;
    }
}
