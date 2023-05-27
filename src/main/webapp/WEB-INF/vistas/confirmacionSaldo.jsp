<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Confirmacion de saldo</title>
</head>
<body>
<div class="container mb-3">
<div style="text-align:center; margin-top:20px;">
<h1>¡Dinero ingresado con éxito!</h1>
<h4>Su nuevo saldo es: ${billetera.saldo} </h4>

<c:if test="${not empty mensaje}">
				
				    ${mensaje}
				 				        	        
		        </c:if>
</div>
</body>
</html>