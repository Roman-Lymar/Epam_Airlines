<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>

<fmt:message key="crewlist.title" var="title"/>
<u:tags title="${title}">
    <table>
        <tr>
            <th><fmt:message key="crewlist.table.name"/></th>
            <th><fmt:message key="crewlist.table.creater"/></th>
            <th><fmt:message key="crewlist.button.edit"/></th>
            <th><fmt:message key="crewlist.button.delete"/></th>
            <th><fmt:message key="crewlist.button.show"/></th>
        </tr>
        <%--@elvariable id="crewList" type="java.util.List"--%>
        <c:forEach var="crew" items="${crewList}">
            <tr>
                <td>${crew.name}</td>
                <td>${crew.user.login}</td>
                <td>
                    <c:url var="crewEdit" value="/dispetcher/crewedit.html">
                        <c:param name="id" value="${crew.id}"/>
                    </c:url>
                    <a href="${crewEdit}" class="edit"></a>
                </td>
                <td>
                    <c:url var="crewDelete" value="/dispetcher/crewdelete.html">
                        <c:param name="id" value="${crew.id}"/>
                    </c:url>
                    <a href="${crewDelete}" class="delete"></a>
                </td>
                <td>
                    <c:url var="crewShow" value="/dispetcher/crewshow.html">
                        <c:param name="id" value="${crew.id}"/>
                    </c:url>
                    <a href="${crewShow}" class="show"></a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <c:url var="crewAdd" value="/dispetcher/crewedit.html"/>
    <a href="${crewAdd}" class="add"></a>
    <u:pagination/>
</u:tags>
