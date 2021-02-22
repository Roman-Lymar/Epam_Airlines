<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>

<c:if test="${empty user}">
    <jsp:useBean id="user" class="ua.nure.lymar.airlines.entity.Users"/>
</c:if>
<fmt:message var="title" key="${not empty user.id?'useredit.title.edit':'useredit.title.add'}"/>
<u:tags title="${title}">
    <c:url var="UserList" value="/admin/userlist.html"/>
    <c:url var="UserSave" value="/admin/usersave.html"/>
    <form action="${UserSave}" method="post">
        <c:if test="${not empty user.id}">
            <input name="id" value="${user.id}" type="hidden">
        </c:if>
        <label for="${user.login}"><fmt:message key="useredit.form.login"/></label>
        <input type="text" name="login" id="${user.login}" value="${user.login}" required><br>
        <c:if test="${empty user.id}">
            <label for="${user.password}"><fmt:message key="useredit.form.password"/></label>
            <input type="password" name="password" id="${user.password}" value="${user.password}" required><br>
        </c:if>
        <label for="${user.name}"><fmt:message key="useredit.form.name"/></label>
        <input type="text" name="name" id="${user.name}" value="${user.name}" required><br>
        <label for="${user.surname}"><fmt:message key="useredit.form.surname"/></label>
        <input type="text" name="surname" id="${user.surname}" value="${user.surname}" required><br>
        <label for="${user.email}"><fmt:message key="useredit.form.email"/></label>
        <input type="email" name="email" id="${user.email}" value=" ${user.email}" required><br>
        <label for="userRole"><fmt:message key="useredit.form.userrole"/></label>
        <select id="userRole" name="userRole">
            <c:forEach var="userRole" items="${userRoles}">
                <option value="${userRole.id}" ${userRole.id == user.userRole.id ?
                        'selected' :''}><fmt:message key="${userRole.name}"/></option>
            </c:forEach>
        </select><br>
        <button class="save"><fmt:message key="useredit.button.save"/></button>
        <button class="cancel" formnovalidate formaction="${UserList}" formmethod="post">
            <fmt:message key="useredit.button.cancel"/>
        </button>
    </form>
</u:tags>