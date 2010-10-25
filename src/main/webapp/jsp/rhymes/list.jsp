<%@page import="com.rhymestore.web.controller.RhymeController"%>
<%@page import="java.util.Set"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">

<html>

<head>
	<title>RhymeStore Management</title>
	<link type="text/css" href="<%=request.getContextPath()%>/css/rhymestore.css" rel="stylesheet" />
</head>

<body>
	<h2>RhymeStore Stored Rhymes</h2>
	
	<%
		Set<String> result = (Set<String>) request.getAttribute(RhymeController.RESULT_ATTR);
		if (result == null) {
	%>
	
	<p class="result">Could not get stored rhymes</p>
	
	<%
		} else {
	%>
	
	<table>
		<%
		for (String rhyme : result) {
		%>
			<tr><td align="left"><%=rhyme %></td></tr>
		<%
			}
		%>
	</table>
	
	<%
		}
	%>

	<p>
		<a href="<%=request.getContextPath()%>/web/rhymes/add">Add a rhyme</a>
	</p>
	
</body>

</html>
