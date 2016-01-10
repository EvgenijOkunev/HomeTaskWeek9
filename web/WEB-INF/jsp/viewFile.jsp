<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=utf-8" language="java" %>
<html>
<head>
    <meta charset="windows-1251">
    <title>File content</title>
    <link type="text/css" rel="stylesheet" href="/css/style.css"/>
</head>
<body>
<div>
    <form name="back" method="post" action="/dir/view">
        <input type="submit" name="back" value="Return">
        <input type="hidden" name="path" value="${requestScope.backPath}">
    </form>
</div>
<h4> File name: ${fileName}</h4>

<h4> File content:</h4>

<div class="fileContent">
    <c:forEach items="${fileLines}" var="fileLine">
        ${fileLine}
        <br>
    </c:forEach>
</div>
</body>
</html>
