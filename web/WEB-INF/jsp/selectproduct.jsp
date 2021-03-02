<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<%@ page import="com.solomko.domain.Order"%>
<%@ page import="com.solomko.domain.User"%>
<%@ page import="com.solomko.domain.Product"%>
<%@ page import="com.solomko.service.OrderService"%>
<%@ page import="com.solomko.service.UserService"%>
<%@ page import="com.solomko.Spring.SpringContext"%>
<%@ page import="org.springframework.context.annotation.AnnotationConfigApplicationContext"%>

<%@ page import="com.solomko.repository.ProductRepository"%>

<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
AnnotationConfigApplicationContext context = SpringContext.getApplicationContext();
UserService userService = (UserService) context.getBean(UserService.class);
OrderService orderService = (OrderService) context.getBean(OrderService.class);
User user = userService.createOrGetUser(session.getAttribute("customer").toString());
Order order = orderService.createOrGetOrder(user);
%>

<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Choose items</title>
</head>

<body>
    <div class="container">
        <div class="header">Welcome, <%=user.getName()%>!!!</div>
        </br>
        <div>Make your order!</div>
        <div>
            <form method="post" id="addprod" action="selectproduct">
                <select name="selected" size="1">
                    <c:forEach var="product" items="${products}">
                        <option value="${product.key}"> ${product.key} (${product.value}$) </option>
                    </c:forEach>
                 </select>
                <input type="hidden" name="id" value="<%=order.getId()%>"></input>
                <input type="submit" name="additem" value="add item"></input>
            </form>
            <form method="POST" id="addprod" action="cart">
                  <input type="submit" value="submit"></input>
                  <input type="submit" name="exit" value="exit"></input>
                  <input type="hidden" name="id" value="<%=order.getId()%>"></input>
                  <input type="hidden" name="customer" value="<%=user.getName()%>"></input>
             </form>
            <div>
             <% int index =0; %>
                <c:forEach var="pickedProduct" items="${order.getProducts()}">
                    <p> ${pickedProduct.getName()} ${pickedProduct.getPrice()}</p>
                     <button type="submit" value="${pickedProduct.getId()}" name="remove" form="addprod"> remove </button>
                </c:forEach>
            </div>
        </div>
    </div>
</body>
</html>
