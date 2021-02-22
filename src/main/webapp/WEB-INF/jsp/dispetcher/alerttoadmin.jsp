<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>
<c:url var="SaveMessage" value="/dispetcher/savemessage.html?id=${crewid}"/>
<form action="${SaveMessage}" method="post">
<fmt:message key="dispetcher.alert.title" var="title"/>
<c:if test="${empty message}">
    <jsp:useBean id="message" class="ua.nure.lymar.airlines.entity.Message"/>
</c:if>
<u:tags title="${title}">
    <h3></h3>
    <table>
        <tr>
            <th><fmt:message key="dispetcher.alert.name"/></th>
            <th><fmt:message key="dispetcher.alert.severity"/></th>
            <th><fmt:message key="dispetcher.alert.issue"/></th>
        </tr>
        <tr>
        <th>
        <input type="text" name="user" id="${user.login}" value="${user.login}" disabled>
        </th>
        <th>
          <select id="severity" name="severity">
                     <c:forEach var="severity" items="${severities}">
                         <option value="${severity.id}">
                                         ${message.severity.id == severity.id  ? 'selected' :''}
                                 ${severity.description}
                         </option>
                     </c:forEach>
                 </select>
         </th>
         <th>
         <input type="text" name="message_text" id="message_text" value="${message.messageText}" required>
         </th>
        </tr>
    </table>
    <br>
        <button class="save"><fmt:message key="crewshow.button.reportToAdmin"/></button>
        <c:url var="crewshow" value="/dispetcher/crewshow.html?id=${crewid}"/><br>
        <a href="${crewshow}" class="cancel"></a>
    </form>
</u:tags>