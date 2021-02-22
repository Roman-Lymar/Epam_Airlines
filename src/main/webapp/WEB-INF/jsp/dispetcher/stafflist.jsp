<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>

<%--@elvariable id="staffList" type="java.util.List"--%>
<fmt:message key="stafflist.title" var="title"/>
<u:tags title="${title}">
    <table>
        <tr>
            <th><fmt:message key="stafflist.table.name"/></th>
            <th><fmt:message key="stafflist.table.surname"/></th>
            <th><fmt:message key="stafflist.table.profession"/></th>
            <th><fmt:message key="stafflist.button.edit"/></th>
            <th><fmt:message key="stafflist.button.delete"/></th>
        </tr>
        <c:forEach var="staff" items="${staffList}">
            <tr>
                <td>${staff.name}</td>
                <td>${staff.surname}</td>
                <td><fmt:message key="${staff.profession.name}"/></td>
                <td>
                    <c:url var="staffEdit" value="/dispetcher/staffedit.html">
                        <c:param name="id" value="${staff.id}"/>
                    </c:url>
                    <a href="${staffEdit}" class="edit"></a>
                </td>
                <td>
                    <c:url var="staffDelete" value="/dispetcher/staffdelete.html">
                        <c:param name="id" value="${staff.id}"/>
                    </c:url>
                    <a href="${staffDelete}" class="delete"></a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <c:url var="staffAdd"  value="/dispetcher/staffedit.html"/>
    <a href="${staffAdd}" class="add"></a>
    <u:pagination/>
</u:tags>
