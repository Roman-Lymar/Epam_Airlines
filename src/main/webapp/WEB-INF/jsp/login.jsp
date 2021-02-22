<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:message key="login.title" var="title"/>
<html>
<head>
    <meta charset="UTF-8">
    <title>${title}</title>
    <c:url var="css" value="/main.css"/>
    <link href="${css}" rel="stylesheet">
</head>
<body>
<h1 id="text"><fmt:message key="app.title"/></h1>
<h2 id="text">${title}</h2>
<c:if test="${not empty param.message}">
    <p class="error"><fmt:message key="${param.message}"/></p>
</c:if>
<c:url var="login" value="/login.html"/>
<form action="${login}" method="get">
    <label for="login"><fmt:message key="login.form.login"/>:</label>
    <input id="login" name="login" required>
    <label for="password"><fmt:message key="login.form.password"/>:</label>
    <input id="password" name="password" type="password" required><br>
    <button class="login"><fmt:message key="login.button.login"/></button>
</form>
</body>
</html>
