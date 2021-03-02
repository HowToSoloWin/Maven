<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<%@ page import="com.solomko.domain.Order"%>
<%@ page import="com.solomko.domain.User"%>
<%@ page import="com.solomko.domain.Product"%>
<%@ page import="com.solomko.service.OrderService"%>
<%@ page import="com.solomko.service.UserService"%>
<%@ page import="com.solomko.Spring.SpringContext"%>
<%@ page import="org.springframework.context.annotation.AnnotationConfigApplicationContext"%>
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
    <title>Cart</title>
</head>

<body>
<div class="container">

    <div class="header">Dear, <%=user.getName()%>, your order is: </div>

     <% int index =0; %>
     <c:forEach var="pickedProduct" items="${order.getProducts()}">
           <p> <%= ++index %>) ${pickedProduct.getName()} ${pickedProduct.getPrice()}$</p>
     </c:forEach>

    <div class="totalPrice">Total price is: <%=order.getTotalPrice()%>$</div>

    <form method="POST" action="selectproduct">
           <input type="submit" name="add" value="Edit cart"></input>
    </form>

     <form method="POST" action="cart">
           <input type="submit" name="exit" value="Exit"></input>
     </form>

</div>
</body>
</html>
