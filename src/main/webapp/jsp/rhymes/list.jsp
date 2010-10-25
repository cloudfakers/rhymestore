<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">

<html>

<head>
	<title>RhymeStore Management</title>
	<link type="text/css" href="<%=request.getContextPath() %>/css/rhymestore.css" rel="stylesheet" />
</head>

<body>

	<h2>RhymeStore Stored Rhymes</h2>
	
	<c:if test="${not empty result}">
		<p class="result">
			<c:out value="${result}" />
		</p>
	</c:if>
	
	<c:if test="${not empty rhymes}">
		<table>
			<c:forEach var="rhyme" items="${rhymes}">
				<tr><td align="left"><c:out value="${rhyme}" /></td></tr>
			</c:forEach>
		</table>
	</c:if>
	
	<p>
		<a href="<%=request.getContextPath() %>/web/rhymes/add">Add a rhyme</a>
	</p>
	
</body>

</html>
