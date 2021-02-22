<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>

<fmt:message key="flightlist.title" var="title"/>
<u:tags title="${title}">
    <table>
        <tr>
            <th><fmt:message key="flightlist.table.name"/></th>
            <th><fmt:message key="flightlist.table.departureCity"/></th>
            <th><fmt:message key="flightlist.table.destinationCity"/></th>
            <th><fmt:message key="flightlist.table.date"/></th>
            <th><fmt:message key="flightlist.table.time"/></th>
            <th><fmt:message key="flightlist.table.status"/></th>
            <th><fmt:message key="flightlist.table.crew"/></th>
            <th><fmt:message key="flightlist.button.edit"/></th>
            <c:if test="${sessionScope.currentUser.userRole.id==0}">
                <th><fmt:message key="flightlist.button.delete"/></th>
            </c:if>
        </tr>
        <%--@elvariable id="flightList" type="java.util.List"--%>
        <c:forEach var="flight" items="${flightList}">
            <tr>
                <td>${flight.name}</td>
                <td>${flight.departureCity}</td>
                <td>${flight.destinationCity}</td>
                <td>${flight.date}</td>
                <td>${flight.time}</td>
                <td><fmt:message key="${flight.status.name}"/></td>
                <td>${flight.crew.name}</td>
                <td>
                    <c:url var="flightEdit" value="/flightedit.html">
                        <c:param name="id" value="${flight.id}"/>
                    </c:url>
                    <a href="${flightEdit}" class="edit"></a>
                </td>
                <c:if test="${sessionScope.currentUser.userRole.id==0}">
                    <td>
                        <c:url var="flightDelete" value="/flightdelete.html">
                            <c:param name="id" value="${flight.id}"/>
                        </c:url>
                        <a href="${flightDelete}" class="delete"></a>
                    </td>
                </c:if>
            </tr>
        </c:forEach>
    </table>
    <c:if test="${sessionScope.currentUser.userRole.id==0}">
        <c:url var="flightAdd" value="/flightedit.html"/>
        <a href="${flightAdd}" class="add"></a>
    </c:if>
    <u:pagination/>
    <c:url var="sortflightbyname" value="/sortflightbyname.html"/>
     <a href="${sortflightbyname}" class="button"><fmt:message key="flightlist.sortflightsbyname"/></a>
     <c:url var="sortflightbydeparturecity" value="/sortflightbydeparturecity.html"/>
     <a href="${sortflightbydeparturecity}" class="button"><fmt:message key="flightlist.sortflightbydeparturecity"/></a>
     <c:url var="sortflightbyarrivalcity" value="/sortflightbyarrivalcity.html"/>
     <a href="${sortflightbyarrivalcity}" class="button"><fmt:message key="flightlist.sortflightbyarrivalcity"/></a>
     <c:url var="sortflightbydate" value="/sortflightbydate.html"/>
     <a href="${sortflightbydate}" class="button"><fmt:message key="flightlist.sortflightbydate"/></a>
</u:tags>
