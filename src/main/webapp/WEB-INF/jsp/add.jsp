<%@page import="com.rhymestore.web.RhymeResource"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">

<html>

<head>
	<title>RymeStore Management</title>
	<link type="text/css" href="<%= request.getContextPath() %>/css/rhymestore.css" rel="stylesheet" />
</head>

<body>
	<h2>RymeStore Management</h2>
	
	<form action="<%= request.getContextPath() %>/rhyme" method="post">
		<p>Enter a Rhyme to add it to RymeStore:</p>
		<input id="ryhme" name="<%= RhymeResource.RHYME_PARAM %>" type="text" size="100" />
		<br /><br />
		<input type="submit" value="Add it!" />
	</form>
</body>

</html>
