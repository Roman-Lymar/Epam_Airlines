<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<form>
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
        <c:forEach var="message" items="${messages}">
                    <tr>
                        <td>${message.dispatcherName}</td>
                        <td>${message.severityDescription}</td>
                        <td>${message.messageText}</td>
                        <td>${message.issuedDate}</td>
                        <td> <fmt:message key="${message.status.status}"/></td>
                        <td>
                        <c:url var="alertEdit" value="/admin/alertedit.html">
                        <c:param name="id" value="${message.id}"/>
                        </c:url>
                         <a href="${alertEdit}" class="edit"></a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
    </table>
    </form>
</u:tags>