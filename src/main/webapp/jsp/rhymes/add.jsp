<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>

<head>
	<title>RhymeStore Management</title>
	<link type="text/css" href="<%=request.getContextPath() %>/css/rhymestore.css" rel="stylesheet" />
</head>

<body>
	<h2>RhymeStore Management</h2>
	
	<form action="<c:url value="/web/rhymes/add" />" method="post">
		<p>Enter a Rhyme to add it to RymeStore:</p>
		<input id="ryhme" name="rhyme" type="text" size="100" />
		<br /><br />
		<input type="submit" value="Add it!" />
	</form>
	
	<p class="result">
		<c:out value="${result}" />
	</p>
	
	<p>
		<a href="<%=request.getContextPath() %>/web/rhymes/list">View stored rhymes</a>
	</p>
	
</body>

</html>
