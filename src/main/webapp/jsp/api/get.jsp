<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %><?xml version="1.0" encoding="ISO-8859-1"?>
<rhymes>
    <total><c:out value="${fn:length(model)}" /></total><c:forEach var="rhyme" items="${model}">
    <rhyme><c:out value="${rhyme}" escapeXml="false" /></rhyme></c:forEach>
</rhymes>
