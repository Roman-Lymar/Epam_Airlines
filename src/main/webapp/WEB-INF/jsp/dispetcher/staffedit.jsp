<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>

<c:if test="${empty staff}">
    <jsp:useBean id="staff" class="ua.nure.lymar.airlines.entity.Staff"/>
</c:if>
<fmt:message var="title" key="${not empty staff.id?'staffedit.title.add':'staffedit.title.edit'}"/>
<u:tags title="${title}">
    <c:url var="StaffList" value="/dispetcher/stafflist.html"/>
    <c:url var="StaffSave" value="/dispetcher/staffsave.html"/>
    <form action="${StaffSave}" method="post">
        <c:if test="${not empty staff.id}">
            <input name="id" value="${staff.id}" type="hidden">
        </c:if>
        <label for="${staff.name}"><fmt:message key="staffedit.form.name"/></label>
        <input type="text" name="name" id="${staff.name}" value="${staff.name}" required><br>
        <label for="${staff.surname}"><fmt:message key="staffedit.form.surname"/></label>
        <input type="text" name="surname" id="${staff.surname}" value="${staff.surname}" required><br>
        <label for="profession"><fmt:message key="staffedit.form.profession"/></label>
        <select id="profession" name="profession">
            <c:forEach var="profession" items="${professions}">
            <option value="${profession.id}"
                ${profession.id == staff.profession.id  ? 'selected' :'' }>
                    <fmt:message key="${profession.name}"/>
                </c:forEach>
        </select><br>
        <button class="save"><fmt:message key="staffedit.button.save"/></button>
        <button class="cancel" formnovalidate formaction="${StaffList}" formmethod="post">
            <fmt:message key="staffedit.button.cancel"/>
        </button>
    </form>
</u:tags>
