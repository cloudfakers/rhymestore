<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="com.rhymestore.twitter.util.TwitterUtils" %>

<form action="<%= request.getContextPath() %>/web/rhymes/add" method="post">
	<input id="ryhme" name="model.rhyme" type="text" class="input" size="90" maxlength="<%= TwitterUtils.MAX_TWEET_LENGTH %>" />
	<input type="submit" value="Add it!" class="add" />
</form>

<table id="rhymes">
	<thead>
		<tr>
			<th class="left">Stored rhymes</th>
			<c:if test="${not empty model}">
				<th class="right">
					<form action="<%= request.getContextPath() %>/download" method="post">
						<input type="submit" value="Download" class="download" />
					</form>
				</th>
			</c:if>
		</tr>
	</thead>
	<c:forEach var="rhyme" items="${model}" varStatus="status">
		<tr class="tr<c:out value="${status.count % 2}"/>">
			<td class="left"><c:out value="${rhyme}" /></td>
			<td class="right">
			   <form action="<%= request.getContextPath() %>/web/rhymes/delete" method="post">
			       <input type="submit" value="Delete" class="delete"/>
			       <input name="model.rhyme" type="hidden" value="<c:out value="${rhyme}" />" />
                      </form>
                  </td>
		</tr>
	</c:forEach>
	<c:if test="${empty model}">
		<tr class="tr1"><td class="empty">There are no rhymes yet</td></tr>
	</c:if>
</table>
