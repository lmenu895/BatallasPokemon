<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
  <title>Gachapon</title>
</head>
<body>
		<h1>Tus monedas ${puntos}</h1>
  <form:form method="POST" action="gachapon-resultado">
    <label for="opcion">Costo del Gachapon</label>
    <select id="opcion" name="monedas" modelAttribute="integer">
      <option value="100" >100</option>
      <option value="500" >500</option>
      <option value="1000" >1000</option>
    </select>
    <br><br>
    <input type="submit" value="Enviar">
  </form:form>
  <c:if  test="${not empty error}"><h4 class="text-center">
  <span>${error}</span>
   </h4> <br>
   </c:if>
</body>
</html>