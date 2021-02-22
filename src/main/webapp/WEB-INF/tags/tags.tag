<%@tag language="java" pageEncoding="UTF-8" %>
<%@attribute name="title" required="true" type="java.lang.String" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${title}</title>
    <c:url var="css" value="/main.css"/>
    <link href="${css}" rel="stylesheet">
</head>
<body>
<c:url var="userList" value="/admin/userlist.html"/>
<c:url var="flightList" value="/flightlist.html"/>
<c:url var="staffList" value="/dispetcher/stafflist.html"/>
<c:url var="crewList" value="/dispetcher/crewlist.html"/>
<c:url var="logout" value="/logout.html"/>
<c:url var="alertList" value="/alertlist.html"/>
<c:url var="changeUserData" value="/userpasswordedit.html">
    <c:param name="id" value="${currentUser.id}"/>
</c:url>
<c:url var="flightSearch" value="/dispetcher/flightSearch.html"/>
<c:url var="ruslanguage" value="/ruslanguage.html"/>
<c:url var="englanguage" value="/englanguage.html"/>

<%--@elvariable id="currentUser" type="ua.nure.lymar.airlines.entity.Users"--%>
<c:choose>
    <c:when test="${sessionScope.currentUser.userRole.id==0}">
        <div id="menu">
            <ul>
                <li>
                    <a href="${userList}"><fmt:message key="userlist.title"/></a>
                </li>
                <li>
                    <a href="${flightList}"><fmt:message key="flightlist.title"/></a>
                </li>
                <li>
                    <a href="${alertList}"><fmt:message key="admin.messagealert.title"/></a>
               </li>
                <c:if test="${not empty currentUser}">
                    <li>
                        <fmt:message key="app.welcome"/> ${currentUser.login}
                    </li>
                    <li>
                        <a href="${changeUserData}"><fmt:message key="app.changepass"/></a>
                    </li>
                    <li>
                        <a href="${logout}"><fmt:message key="app.button.loguot"/></a>
                    </li>
                    <li>
                          <a href="${ruslanguage}"><fmt:message key="app.button.rus"/></a>
                    </li>
                    <li>
                          <a href="${englanguage}"><fmt:message key="app.button.eng"/></a>
                    </li>
                </c:if>
            </ul>
        </div>
    </c:when>
    <c:when test="${sessionScope.currentUser.userRole.id==1}">
        <div id="menu">
            <ul>
                <li>
                    <a href="${staffList}"><fmt:message key="stafflist.title"/></a>
                </li>
                <li>
                    <a href="${crewList}"><fmt:message key="crewlist.title"/></a>
                </li>
                <li>
                    <a href="${flightList}"><fmt:message key="flightlist.title"/></a>
                </li>
                <li>
                     <a href="${flightSearch}"><fmt:message key="flightsearch.title"/></a>
                </li>
                <c:if test="${not empty currentUser}">
                    <li>
                        <fmt:message key="app.welcome"/>: ${currentUser.login}
                    </li>
                    <li>
                        <a href="${changeUserData}"><fmt:message key="app.changepass"/></a>
                    </li>
                    <li>
                        <a href="${logout}"><fmt:message key="app.button.loguot"/></a>
                    </li>
                    <li>
                        <a href="${ruslanguage}"><fmt:message key="app.button.rus"/></a>
                    </li>
                    <li>
                        <a href="${englanguage}"><fmt:message key="app.button.eng"/></a>
                    </li>
                </c:if>
            </ul>
        </div>
    </c:when>
</c:choose>
<h2 id="text">${title}</h2>
<jsp:doBody/>
</body>
</html>
