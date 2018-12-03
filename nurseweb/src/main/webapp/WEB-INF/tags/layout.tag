<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <!-- Required meta tags-->
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="Test">
    <title>NurseAPP</title>

    <%--<link rel="icon" href="<c:url value="/resources/images/favicon.png"/>"/>--%>

    <!-- CSS -->
    <jsp:include page="/WEB-INF/views/common/includes-css.jsp"/>
    <jsp:include page="/WEB-INF/views/common/includes-js.jsp"/>

    <c:url value="/" var="baseUrl" scope="request" />
</head>


<body>
<div class="cuerpo">
    <div class="container">
        <div class="row align-items-center">
            <div class="col">
                <jsp:doBody/>
            </div>
        </div>

    </div>
</div>




</body>
</html>
