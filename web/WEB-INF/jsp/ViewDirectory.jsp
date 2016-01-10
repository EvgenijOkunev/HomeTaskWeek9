<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>

<head>
    <title>File manager</title>
    <link type="text/css" rel="stylesheet" href="/css/style.css"/>
</head>

<body>

<form method="post" action="/file/create">
    <input type="text" name="fileName" placeholder="file name">
    <input type="submit" value="Create new file">
</form>

<table>

    <c:if test="${sessionScope.path != requestScope.rootPath}">
        <tr>
            <td>
                <div class="row">
                    <form name="main" method="post" action="/dir/view">
                        <input type="submit" name="text" value="[..]" class="dir">
                        <input type="hidden" name="path" value="${requestScope.backPath}">
                    </form>
                </div>
            </td>
        </tr>
    </c:if>

    <c:forEach items="${directoryLinks}" var="directoryLink">
        <tr>
            <td>
                <div class="row">
                    <form name="main" method="post" action="/dir/view">
                        <input type="submit" name="text" value="[${directoryLink.key}]" class="dir">
                        <input type="hidden" name="path" value="${directoryLink.value}">
                    </form>
                </div>
            </td>
        </tr>
    </c:forEach>

    <c:forEach items="${fileLinks}" var="fileLink">
        <tr>
            <td>
                <div class="row">
                    <form name="main" method="post" action="/file/view">
                        <input type="submit" name="text" value="${fileLink.key}">
                        <input type="hidden" name="path" value="${fileLink.value}">
                    </form>
                </div>
            </td>
            <td>
                <div class="row">
                    <form name="del" method="post" action="/file/remove">
                        <input type="submit" value="delete" class="del">
                        <input type="hidden" name="path" value="${fileLink.value}">
                    </form>
                </div>
            </td>
        </tr>
    </c:forEach>

</table>

<br><br>
<jsp:include page="footer.jsp"/>

</body>

</html>
