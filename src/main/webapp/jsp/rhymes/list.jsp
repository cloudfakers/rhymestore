<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="com.rhymestore.twitter.util.TwitterUtils" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>

<head>
    <title>The Rhymestore Project</title>
    <link type="image/png" href="<%= request.getContextPath() %>/img/favicon.png" rel="icon" />
	<link type="text/css" href="<%= request.getContextPath() %>/css/rhymestore.css" rel="stylesheet" />
</head>

<body>

	<div id="content">
		<h2>The Rhymestore Project</h2>
		<p class="subtitle">&#47;&eth;&#601; &#39;raymst&#596;r &#39;pr&#593;&#496;&#603;kt&#47;</p>
		
		<hr />
		
		<c:if test="${not empty errors}">
			<div class="errors">
				<ul>
					<c:forEach var="error" items="${errors}">
						<li><c:out value="${error}" /></li>
					</c:forEach>
				</ul>
			</div>
		</c:if>

		<form action="<%= request.getContextPath() %>/web/rhymes/list" method="post">
			<input id="ryhme" name="rhyme" type="text" class="input" size="90" maxlength="<%= TwitterUtils.MAX_TWEET_LENGTH %>" />
			<input type="submit" value="Add it!" class="add" />
		</form>
		
		<table id="rhymes">
			<thead>
				<tr>
					<th class="left">Stored rhymes</th>
					<c:if test="${not empty rhymes}">
						<th class="right">
							<form action="<%= request.getContextPath() %>/download" method="post">
								<input type="submit" value="Download" class="download" />
							</form>
						</th>
					</c:if>
				</tr>
			</thead>
			<c:forEach var="rhyme" items="${rhymes}" varStatus="status">
				<tr class="tr<c:out value="${status.count % 2}"/>">
					<td colspan="2"><c:out value="${rhyme}" /></td>
				</tr>
			</c:forEach>
			<c:if test="${empty rhymes}">
				<tr class="tr1"><td class="empty">There are no rimes yet</td></tr>
			</c:if>
		</table>
		
		<a href="http://twitter.com/rimamelo" title="Follow on Twitter"><img src="<%= request.getContextPath() %>/img/twitter.png" alt="Follow on Twitter" border="0" /></a>
	</div>
	
</body>

</html>
