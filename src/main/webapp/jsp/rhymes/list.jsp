<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>

<head>
	<title>The Rhymestore Project</title>
	<link type="text/css" href="<%=request.getContextPath() %>/css/rhymestore.css" rel="stylesheet" />
</head>

<body>

	<h2>The Rhymestore Project</h2>
	
	<c:if test="${not empty errors}">
		<div class="errors">
			The following errors occured:
			<ul>
				<c:forEach var="error" items="${errors}">
					<li><c:out value="${error}" /></li>
				</c:forEach>
			</ul>
		</div>
	</c:if>
	
	<form action="<%=request.getContextPath() %>/web/rhymes/list" method="post">
		<p>Enter the rhyme to add:</p>
		<input id="ryhme" name="rhyme" type="text" size="100" class="input" />
		<input type="submit" value="Add it!" class="button" />
	</form>
	
	<br />

	<table id="rhymes">
		<thead>
			<tr><th>Rhyme</th></tr>
		</thead>
		<c:forEach var="rhyme" items="${rhymes}" varStatus="status">
			<tr class="tr<c:out value="${status.count % 2}"/>">
				<td align="left"><c:out value="${rhyme}" /></td>
			</tr>
		</c:forEach>
		<c:if test="${empty rhymes}">
			<tr class="tr1"><td class="empty">There are no rimes yet</td></tr>
		</c:if>
	</table>
	
</body>

</html>
