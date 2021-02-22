<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>

<fmt:message key="flightsearch.title" var="title"/>
<c:if test="${empty flight}">
    <jsp:useBean id="flight" class="ua.nure.lymar.airlines.entity.Flight"/>
</c:if>
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
            <th><fmt:message key="flightlist.button.delete"/></th>
        </tr>
        <%--@elvariable id="flightSearch" type="java.util.List"--%>
        <c:forEach var="flight" items="${flightSearch}">
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
                <td>
                <c:url var="flightDelete" value="/flightdelete.html">
                <c:param name="id" value="${flight.id}"/>
                </c:url>
                <a href="${flightDelete}" class="delete"></a>
               </td>
            </tr>
        </c:forEach>
    </table>
    <u:pagination/>
    <c:url var="findbyname" value="/dispetcher/searchResult.html"/>
    <form action="${findbyname}" method="post">
    <label for="${flight.name}"><fmt:message key="flightsearch.name"/></label>
            <input type="text" name="name" id="${flight.name}" value="${flight.name}"><br>
     <br>
    <label for="departureCityId"><fmt:message key="flightsearch.table.alldepartures"/></label>
             <select id="departureCityId" name="departureCityId">
                 <c:forEach var="departureCity" items="${departureCityList}">
                     <option value="${departureCity}">
                        ${departureCity}
                     </option>
       </c:forEach>
     </select><br>
     <label for="destinationCityId"><fmt:message key="flightsearch.table.alldestinations"/></label>
                  <select id="destinationCityId" name="destinationCityId">
                      <c:forEach var="destinationCity" items="${destinationCityList}">
                          <option value="${destinationCity}">
                             ${destinationCity}
                          </option>
            </c:forEach>
          </select><br>
     <label for="${flight.date}"><fmt:message key="flightedit.form.date"/></label>
     <input type="date" name="date" id="${flight.date}" value="${flight.date}"><br>
     <button class="search"><fmt:message key="flightsearch.search"/></button>
 </form>
</u:tags>
