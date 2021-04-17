<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language" var="Loc"/>

<div style="display:none;">
    <fmt:message bundle="${Loc}" key="${param.error}" var="server_error"/>
</div>
<html>
<head>
    <title>Error</title>
    <link href="/css/common.css" rel="stylesheet" type="text/css"/>
    <link href="/css/index.css" rel="stylesheet" type="text/css"/>
    <link href="/css/popup.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<div style="font-size: 45px; color: white; margin-left: 50px;margin-top: 100px">
    ${server_error}
</div>

</body>
</html>
