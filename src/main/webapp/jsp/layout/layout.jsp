<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>

<head>
    <title>The Rhymestore Project</title>
    
    <!-- Styles -->
    <link type="image/png" href="<%= request.getContextPath() %>/img/favicon.png" rel="icon" />
    <link type="text/css" href="<%= request.getContextPath() %>/css/rhymestore.css" rel="stylesheet" />
    
    <!-- Scripts -->
    <script src="<%= request.getContextPath() %>/scripts/jquery-1.4.1.min.js"></script>
    <script src="<%= request.getContextPath() %>/scripts/jquery.raptorize.1.0.js"></script>
    
    <!-- Bind the raptor to the Konami code -->
    <script type="text/javascript">
        var imgPath = '<%= request.getContextPath() %>/img';
        var soundPath = '<%= request.getContextPath() %>/media';
        $(window).load(function() {
            $('.raptor').raptorize(imgPath, soundPath, {
                'enterOn' : 'konami-code'
            });
        });
    </script>
</head>

<body>

    <div id="content">
        <jsp:include page="header.jsp" />
        
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
        
        <jsp:include page="${currentView}" />
        
        <jsp:include page="footer.jsp" />
    </div>
        
</body>

</html>
