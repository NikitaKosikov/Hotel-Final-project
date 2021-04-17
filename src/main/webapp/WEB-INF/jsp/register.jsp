<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language" var="Loc"/>


<fmt:message bundle="${Loc}" key="Locale.name.hotel" var="hotel_name"/>
<fmt:message bundle="${Loc}" key="Locale.button.sign.up" var="sign_up_button"/>
<fmt:message bundle="${Loc}" key="Locale.link.sign.up" var="sign_up_link"/>
<fmt:message bundle="${Loc}" key="Locale.link.sign.in" var="sign_in_link"/>
<fmt:message bundle="${Loc}" key="Locale.name.sign.up" var="sign_up_name"/>
<fmt:message bundle="${Loc}" key="Locale.placeholder.enter.name" var="enter_name_placeholder"/>
<fmt:message bundle="${Loc}" key="Locale.placeholder.enter.surname" var="enter_surname_placeholder"/>
<fmt:message bundle="${Loc}" key="Locale.placeholder.enter.email" var="enter_email_placeholder"/>
<fmt:message bundle="${Loc}" key="Locale.placeholder.enter.password" var="enter_password_placeholder"/>
<fmt:message bundle="${Loc}" key="Locale.placeholder.enter.repeat.password" var="enter_repeat_password_placeholder"/>
<fmt:message bundle="${Loc}" key="Locale.placeholder.enter.phone.number" var="enter_phone_number_placeholder"/>
<fmt:message bundle="${Loc}" key="Locale.name.name" var="name_name"/>
<fmt:message bundle="${Loc}" key="Locale.name.phone" var="phone_name"/>
<fmt:message bundle="${Loc}" key="Locale.name.surname" var="surname_name"/>
<fmt:message bundle="${Loc}" key="Locale.name.email" var="email_name"/>
<fmt:message bundle="${Loc}" key="Locale.name.password" var="password_name"/>
<fmt:message bundle="${Loc}" key="Locale.name.repeat.password" var="repeat_password_name"/>
<fmt:message bundle="${Loc}" key="Locale.footer" var="footer"/>
<fmt:message bundle="${Loc}" key="Locale.creator.designed" var="creator_designed"/>
<div style="display:none;">
    <fmt:message bundle="${Loc}" key="${param.empty_name_error}" var="empty_name_error"/>
    <fmt:message bundle="${Loc}" key="${param.empty_surname_error}" var="empty_surname_error"/>
    <fmt:message bundle="${Loc}" key="${param.user_email_error}" var="user_email_error"/>
    <fmt:message bundle="${Loc}" key="${param.empty_password_error}" var="empty_password_error"/>
    <fmt:message bundle="${Loc}" key="${param.password_error}" var="password_error"/>
    <fmt:message bundle="${Loc}" key="${param.phone_number_error}" var="phone_number_error"/>
</div>


<html>
<head>
    <title>${hotel_name}</title>
    <link href="/css/common.css" , rel="stylesheet" type="text/css"/>
    <link href="/css/index.css" , rel="stylesheet" type="text/css"/>
    <link href="/css/register.css" , rel="stylesheet" type="text/css"/>
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
                <select class="language_colour" id="language" name="locale" onchange="submit()">
                    <option value="ru" ${sessionScope.locale == 'ru' ? 'selected' : ''}>Рус</option>
                    <option value="en" ${sessionScope.locale == 'en' ? 'selected' : ''}>Eng</option>
                </select>
            </form>
        </div>
    </div>

    <div class="register not_cursor">
        <div class="center colour font_size">${sign_up_name}</div>
        <form action="/Controller?command=signup" method="post">
            <div class="input_wrapper">
                <div class="login_text leftstr">
                    ${name_name}
                    <div style="color: red">${empty_name_error}</div>
                </div>
                <div class="login_text rightstr">
                    <input style="width: 250px" class="center" type="text" name="name"
                           placeholder="${enter_name_placeholder}"
                           value="<c:out value="${param.name}"/>" required>

                </div>

            </div>
            <div class="input_wrapper">
                <div class="login_text leftstr">
                    ${surname_name}
                    <div style="color: red">${empty_surname_error}</div>
                </div>
                <div class="login_text rightstr">
                    <input style="width: 250px" class="center" type="text" name="surname"
                           placeholder="${enter_surname_placeholder}"
                           value="<c:out value="${param.surname}"/>" required>
                </div>
            </div>

            <div class="input_wrapper">
                <div class="login_text leftstr">
                    ${email_name}
                    <div style="color: red">
                        <c:out value="${user_email_error}"/>
                        <%--                            <c:if test="${param.user_email_error!=null}">--%>
                        <%--                                (<c:out value="${param.user_email_error}"/>)--%>
                        <%--                            </c:if>--%>
                    </div>

                </div>
                <div class="login_text rightstr">
                    <input style="width: 250px" class="center" type="text" name="email"
                           placeholder="${enter_email_placeholder}"
                           value="<c:out value="${param.email}"/>" required>
                </div>
            </div>
            <div class="input_wrapper">
                <div class="login_text leftstr">
                    ${password_name}
                    <div style="color: red">${empty_password_error}</div>
                </div>
                <div class="login_text rightstr">
                    <input style="width: 250px" class="center" type="password" name="password"
                           placeholder="   ${enter_password_placeholder}"
                           value="<c:out value="${param.password}"/>" required>
                </div>
            </div>
            <div class="input_wrapper">
                <div class="login_text leftstr">
                    ${repeat_password_name}
                    <div style="color: red; font-size: 15px">
                        ${empty_password_error}
                        <c:out value="${password_error}"/>
                    </div>
                </div>
                <div class="login_text rightstr">
                    <input style="width: 250px" class="center" type="password" name="password_repeat"
                           placeholder="${enter_repeat_password_placeholder}"
                           value="<c:out value="${param.password_repeat}"/>" required>
                </div>
            </div>

            <div class="input_wrapper">
                <div class="login_text leftstr">
                    ${phone_name}
                    <div style="color: red; font-size: 15px">
                        <c:out value="${phone_number_error}"/>
                    </div>
                </div>
                <div class="login_text rightstr">
                    <input style="width: 250px" class="center" type="text" name="phone_number"
                           placeholder="${enter_phone_number_placeholder}"
                           value="<c:out value="${param.phone_number}"/>" required>
                </div>
            </div>

            <div class="button_wrapper">
                <input class="input_hover submit" style="font-size: 17px; margin-top: 15px" type="submit"
                       value="${sign_up_button}">
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


