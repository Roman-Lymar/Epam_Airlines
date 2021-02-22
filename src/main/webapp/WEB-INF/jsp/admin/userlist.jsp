<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>

<fmt:message key="userlist.title" var="title"/>
<u:tags title="${title}">
    <table>
        <tr>
            <th><fmt:message key="userlist.table.login"/></th>
            <th><fmt:message key="userlist.table.name"/></th>
            <th><fmt:message key="userlist.table.surname"/></th>
            <th><fmt:message key="userlist.table.email"/></th>
            <th><fmt:message key="userlist.table.userrole"/></th>
            <th><fmt:message key="userlist.button.edit"/></th>
            <th><fmt:message key="userlist.button.delete"/></th>
            <th><fmt:message key="userlist.button.setdefpass"/></th>
        </tr>
        <%--@elvariable id="userList" type="java.util.List"--%>
        <c:forEach var="user" items="${userList}">
            <tr>
                <td>${user.login}</td>
                <td>${user.name}</td>
                <td>${user.surname}</td>
                <td>${user.email}</td>
                <td><fmt:message key="${user.userRole.name}"/></td>
                <td>
                    <c:url var="userEdit" value="/admin/useredit.html">
                        <c:param name="id" value="${user.id}"/>
                    </c:url>
                    <a href="${userEdit}" class="edit"></a>
                </td>
                <td>
                    <c:url var="userDelete" value="/admin/userdelete.html">
                        <c:param name="id" value="${user.id}"/>
                    </c:url>
                    <a href="${userDelete}" class="delete"></a>
                </td>
                <td>
                    <c:url var="setDefaultPass" value="/admin/userpassworddefault.html">
                        <c:param name="id" value="${user.id}"/>
                    </c:url>
                    <a href="${setDefaultPass}" class="defPassword"></a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <c:url var="userAdd" value="/admin/useredit.html"/>
    <a href="${userAdd}" class="add"></a>
    <u:pagination/>
</u:tags>