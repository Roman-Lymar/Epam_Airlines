<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>

<c:if test="${empty flight}">
    <jsp:useBean id="flight" class="ua.nure.lymar.airlines.entity.Flight"/>
</c:if>
<fmt:message var="title" key="${not empty flight.id?'flightedit.title.edit':'flightedit.title.add'}"/>
<u:tags title="${title}">
    <c:url var="FlightList" value="/flightlist.html"/>
    <c:url var="FlightSave" value="/flightsave.html"/>
    <form action="${FlightSave}" method="post">
        <c:if test="${not empty flight.id}">
            <input name="id" value="${flight.id}" type="hidden">
        </c:if>
        <c:set var="disable" value="${sessionScope.currentUser.userRole.id==1?'disabled':''}"/>
        <label for="${flight.name}"><fmt:message key="flightedit.form.name"/></label>
        <input type="text" name="name" id="${flight.name}" value="${flight.name}" required ${disable}><br>
        <label for="${flight.departureCity}"><fmt:message key="flightedit.form.departureCity"/></label>
        <input type="text" name="departureCity" id="${flight.departureCity}" value="${flight.departureCity}"
               required ${disable}><br>
        <label for="${flight.destinationCity}"><fmt:message key="flightedit.form.destinationCity"/></label>
        <input type="text" name="destinationCity" id="${flight.destinationCity}" value="${flight.destinationCity}"
               required ${disable}><br>
        <label for="${flight.date}"><fmt:message key="flightedit.form.date"/></label>
        <input type="date" name="date" id="${flight.date}" value="${flight.date}" required ${disable}><br>
        <label for="${flight.time}"><fmt:message key="flightedit.form.time"/></label>
        <input type="time" name="time" id="${flight.time}" value="${fn:substring(flight.time,0 ,5)}"
               required ${disable}><br>
        <label for="flightStatus"><fmt:message key="flightedit.form.status"/></label>
        <c:set var="disable" value="${sessionScope.currentUser.userRole.id==0?'disabled':''}"/>
        <select id="flightStatus" name="flightStatus" ${disable}>
            <c:choose>
                <c:when test="${not empty flight.id}">
                    <c:forEach var="flightStatus" items="${flightStatus}">
                        <option value="${flightStatus.id}" ${flightStatus.id == flight.status.id ?
                                'selected' :''}><fmt:message key="${flightStatus.name}"/></option>
                    </c:forEach>
                </c:when>
            </c:choose>
        </select><br>
        <c:choose>
            <c:when test="${sessionScope.currentUser.userRole.id==0}">
                <label for="${flight.crew}"><fmt:message key="flightedit.form.crew"/></label>
                <input type="text" name="crewId" id="${flight.crew}" value="${flight.crew.name}" disabled><br>
            </c:when>
            <c:when test="${sessionScope.currentUser.userRole.id==1}">
                <label for="crewId"><fmt:message key="flightedit.form.crew"/></label>
                <select id="crewId" name="crewId">
                    <option selected disabled><fmt:message key="flightedit.form.crew.select"/></option>
                    <c:forEach var="crewId" items="${crewList}">
                        <option value="${crewId.id}" ${crewId.id == flight.crew.id?
                                'selected' :''}>${crewId.name}</option>
                    </c:forEach>
                </select><br>
            </c:when>
        </c:choose>
        <button class="save"><fmt:message key="flightedit.button.save"/></button>
        <button class="cancel" formnovalidate formaction="${FlightList}" formmethod="post">
            <fmt:message key="flightedit.button.cancel"/>
        </button>
    </form>
</u:tags>
