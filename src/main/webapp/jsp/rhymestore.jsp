<%@page import="com.rhymestore.web.RhymeServlet"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>

<head>
	<title>RhymeStore Management</title>
	<link type="text/css" href="<%=request.getContextPath()%>/css/rhymestore.css" rel="stylesheet" />
</head>

<body>
	<h2>RhymeStore Management</h2>
	
	<form action="<%=request.getContextPath()%>/rhyme" method="post">
		<p>Enter a Rhyme to add it to RymeStore:</p>
		<input id="ryhme" name="<%=RhymeServlet.RHYME_PARAM%>" type="text" size="100" />
		<br /><br />
		<input type="submit" value="Add it!" />
	</form>
	
	<%
		String result = (String) request.getAttribute(RhymeServlet.RESULT_ATTR);
		if (result != null) {
	%>
	
	<p class="result"><%= result %></p>
	
	<%
		}
	%>
	
</body>

</html>
