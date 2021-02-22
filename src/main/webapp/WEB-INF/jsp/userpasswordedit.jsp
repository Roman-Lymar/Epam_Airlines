<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>

<fmt:message key="app.changepass" var="title"/>
<u:tags title="${title}">
    <c:url var="PasswordSave" value="/userpasswordsave.html"/>
    <jsp:useBean id="user" scope="request" type="ua.nure.lymar.airlines.entity.Users"/>
    <c:url var="HomePage" value="/"/>
    <form action="${PasswordSave}" method="post">
        <input name="id" value="${user.id}" type="hidden">
        <label for="${user.password}"><fmt:message key="userpasswordedit.form.password"/></label>
        <input type="password" name="password" id="${user.password}" required><br>
        <button class="save"><fmt:message key="userpasswordedit.button.save"/></button>
        <button class="cancel" formnovalidate formaction="${HomePage}" formmethod="post">
            <fmt:message key="userpasswordedit.button.cancel"/>
        </button>
    </form>
</u:tags>
