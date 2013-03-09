<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %><?xml version="1.0" encoding="ISO-8859-1"?>
<errors>
    <total><c:out value="${fn:length(errors)}" /></total><c:forEach var="error" items="${errors}">
    <error><c:out value="${error}" escapeXml="false" /></error></c:forEach>
</errors>
