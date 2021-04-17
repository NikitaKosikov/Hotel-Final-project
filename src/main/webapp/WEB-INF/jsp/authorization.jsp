<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language" var="Loc"/>

<fmt:message bundle="${Loc}" key="Locale.name.hotel" var="hotel_name"/>
<fmt:message bundle="${Loc}" key="Locale.name.sign.in" var="sign_in_name"/>
<fmt:message bundle="${Loc}" key="Locale.placeholder.enter.mail" var="enter_mail_placeholder"/>
<fmt:message bundle="${Loc}" key="Locale.placeholder.enter.password" var="enter_password_placeholder"/>
<fmt:message bundle="${Loc}" key="Locale.button.sign.in" var="sign_in_button"/>
<fmt:message bundle="${Loc}" key="Locale.link.sign.in" var="sign_in_link"/>
<fmt:message bundle="${Loc}" key="Locale.link.sign.up" var="sign_up_link"/>
<fmt:message bundle="${Loc}" key="Locale.name.email" var="email_name"/>
<fmt:message bundle="${Loc}" key="Locale.name.password" var="password_name"/>
<fmt:message bundle="${Loc}" key="Locale.footer" var="footer"/>
<fmt:message bundle="${Loc}" key="Locale.creator.designed" var="creator_designed"/>
<div style="display:none;">
    <fmt:message bundle="${Loc}" key="${requestScope.error}" var="incorrectly_user"/>
    <fmt:message bundle="${Loc}" key="${param.authorization_error}" var="authorization_error"/>
</div>


<html>
<head>
    <title>${hotel_name}</title>
    <link href="/css/common.css" rel="stylesheet" type="text/css"/>
    <link href="/css/index.css" rel="stylesheet" type="text/css"/>
    <link href="/css/authentication.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<div class="container">
    <div class="header">
        <div class="title font_size not_cursor" style="${sessionScope.locale == 'en' ? 'width:650px' : ''}">
            ${hotel_name}
            <a href="/Controller?command=gotoindexpage"><img class="home_button" src="images/common/home.png"></a>
        </div>
        <div class="sign_in">
            <a class="nav-link" href="/Controller?command=GOTOAUTHORIZATIONPAGE">${sign_in_link}</a>
        </div>
        <div class="sign_up" style="${sessionScope.locale == 'en' ? 'width:80px':''}">
            <a class="nav-link" href="/Controller?command=GOTOREGISTRATIONPAGE">${sign_up_link}</a>
        </div>
        <div class="icon">
            <img class="home_button" src="images/common/language.png">
        </div>
        <div class="language">
            <form action="/Controller" class="header-form" method="get">
                <input type="hidden" name="command" value="changelocale">
                <select class="language_colour" id="locale" name="locale" onchange="submit()">
                    <option value="ru" ${sessionScope.locale == 'ru' ? 'selected' : ''}>Рус</option>
                    <option value="en" ${sessionScope.locale == 'en' ? 'selected' : ''}>Eng</option>
                </select>
            </form>
        </div>
    </div>

    <div class="authentication not_cursor">
        <div class="center colour font_size">${sign_in_name}</div>
        <form action="/Controller" method="get">
            <input type="hidden" name="command" value="signin">
            <div class="input_wrapper">
                <div class="login_text leftstr">
                    ${email_name}
                </div>
                <div class="login_text rightstr">
                    <input style="width: 250px" class="center" type="text" name="email"
                           placeholder="${enter_mail_placeholder}" required>
                </div>

            </div>
            <div class="input_wrapper">
                <div class="login_text leftstr">
                    ${password_name}
                </div>
                <div class="login_text rightstr">
                    <input style="width: 250px" class="center" type="password" name="password"
                           placeholder="${enter_password_placeholder}" required>
                </div>
            </div>
            <div class="button_wrapper" align="center">
                <input class="input_hover submit" style="font-size: 17px" type="submit" value="${sign_in_button}">
            </div>
            <div align="center" style="margin-top: 30px; color: red">
                <c:out value="${incorrectly_user}"/>
                <c:out value="${authorization_error}"/>
            </div>
        </form>
    </div>
</div>

<div class="footer">
    <hr class="footer_hr">
    <div class="footer_data_text">
        <div class="icon_alien"><img src="images/common/alien.png"></div>
        <div class="icon_alien">
            ${footer}
            <div style="color: dodgerblue; display: inline-block;vertical-align: middle;">
                <a style="font-size: 13px; color: dodgerblue" class="nav-link a_not_decoration"
                   href="https://vk.com/animemademeso123">
                    ${creator_designed}
                </a>
            </div>
        </div>
    </div>
</div>
</body>
</html>
