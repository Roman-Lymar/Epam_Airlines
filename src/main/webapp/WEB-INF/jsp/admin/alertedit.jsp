<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
 <c:url var="AlertSave" value="/admin/alertsave.html">
 <c:param name="id" value="${message.id}"/>
 </c:url>
<form action="${AlertSave}" method="post">
<c:if test="${empty message}">
    <jsp:useBean id="message" class="ua.nure.lymar.airlines.entity.Message"/>
</c:if>
<fmt:message key="admin.messagealert.title" var="title"/>
<u:tags title="${title}">
    <h3></h3>
    <table>
        <tr>
            <th><fmt:message key="dispetcher.alert.name"/></th>
            <th><fmt:message key="dispetcher.alert.severity"/></th>
            <th><fmt:message key="dispetcher.alert.issue"/></th>
            <th><fmt:message key="dispetcher.alert.issuedate"/></th>
            <th><fmt:message key="dispetcher.alert.status"/></th>
        </tr>
         <tr>
            <td>${message.dispatcherName}</td>
            <td>${message.severityDescription}</td>
            <td>${message.messageText}</td>
            <td>${message.issuedDate}</td>
            <td> <fmt:message key="${message.status.status}"/></td>
         </tr>
    </table>
     <label for="status"><fmt:message key="dispetcher.alert.status"/></label>
            <select id="status" name="status">
                <c:forEach var="status" items="${statuses}">
                <option value="${status.id}"
                    ${status.id == status.id  ? 'selected' :'' }>
                        <fmt:message key="${status.status}"/>
                    </c:forEach>
            </select><br>
    <button class="save"><fmt:message key="dispetcher.alert.savestatus"/></button>
    </form>
</u:tags>