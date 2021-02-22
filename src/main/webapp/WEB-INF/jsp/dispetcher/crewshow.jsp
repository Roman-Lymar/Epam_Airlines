<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>

<fmt:message key="crewshow.title" var="title"/>
<u:tags title="${title}">
    <h3>${crew.name}:</h3>
    <table>
        <tr>
            <th><fmt:message key="crewshow.table.name"/></th>
            <th><fmt:message key="crewshow.table.surname"/></th>
            <th><fmt:message key="crewshow.table.profession"/></th>
            <th><fmt:message key="crewshow.table.delete"/> </th>
        </tr>
        <c:forEach var="staff" items="${staffInCrew}">
            <tr>
                <td>${staff.name}</td>
                <td>${staff.surname}</td>
                <td><fmt:message key="${staff.profession.name}"/></td>
                <td>
                    <c:url var="staffDelete" value="/dispetcher/deletefromcrew.html">
                        <c:param name="staffId" value="${staff.id}"/>
                        <c:param name="crewId" value="${crew.id}"/>
                    </c:url>
                    <a href="${staffDelete}" class="delete"></a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <br>
    <hr>
    <c:url var="AddStaff" value="/dispetcher/addstaffincrew.html"/>
    <form action="${AddStaff}" method="post">
        <input name="crewId" value="${crew.id}" type="hidden">
        <label for="staffId"><fmt:message key="crewshow.table.addstaff"/></label>
        <select id="staffId" name="staffId">
            <c:forEach var="staff" items="${freeStaff}">
                <option value="${staff.id}">
                        ${staff.surname} ${staff.name} <fmt:message key="${staff.profession.name}"/>
                </option>
            </c:forEach>
        </select><br>
        <button class="add"><fmt:message key="crewshow.button.add"/></button>
        <c:url var="alertToAdmin" value="/dispetcher/alerttoadmin.html?id=${crew.id}"/><br>
        <a href="${alertToAdmin}" class="send_message"></a>
    </form>
    <c:url var="crewList" value="/dispetcher/crewlist.html"/><br>
    <a href="${crewList}" class="cancel"></a>
</u:tags>
