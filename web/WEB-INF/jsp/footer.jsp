<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body>
<div class="operationLog">
    <c:forEach items="${messages}" var="message">
        ${message}
        <br>
    </c:forEach>
</div>
</body>
</html>
