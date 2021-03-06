<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language" var="Loc"/>

<fmt:message bundle="${Loc}" key="Locale.name.hotel" var="hotel_name"/>
<fmt:message bundle="${Loc}" key="Locale.link.services" var="services_link"/>
<fmt:message bundle="${Loc}" key="Locale.link.account" var="account_link"/>
<fmt:message bundle="${Loc}" key="Locale.link.sign.out" var="sign_out_link"/>
<fmt:message bundle="${Loc}" key="Locale.link.profile" var="profile_link"/>
<fmt:message bundle="${Loc}" key="Locale.link.orderRooms" var="orders_link"/>

<fmt:message bundle="${Loc}" key="Locale.name.number.of.beds" var="number_of_beds_name"/>
<fmt:message bundle="${Loc}" key="Locale.name.cost" var="cost_name"/>
<fmt:message bundle="${Loc}" key="Locale.name.economy" var="economy_name"/>
<fmt:message bundle="${Loc}" key="Locale.name.comfort" var="comfort_name"/>
<fmt:message bundle="${Loc}" key="Locale.name.business" var="business_name"/>
<fmt:message bundle="${Loc}" key="Locale.name.premium" var="premium_name"/>
<fmt:message bundle="${Loc}" key="Locale.name.apartment.class" var="apartment_class_name"/>
<fmt:message bundle="${Loc}" key="Locale.link.add.room" var="add_room_link"/>
<fmt:message bundle="${Loc}" key="Locale.button.add" var="add_button"/>
<fmt:message bundle="${Loc}" key="Locale.footer" var="footer"/>
<fmt:message bundle="${Loc}" key="Locale.creator.designed" var="creator_designed"/>
<fmt:message bundle="${Loc}" key="Locale.name.select.image" var="select_image_name"/>
<fmt:message bundle="${Loc}" key="Locale.button.update.image" var="update_button"/>
<fmt:message bundle="${Loc}" key="Locale.name.image" var="image_name"/>
<div style="display:none;">
    <fmt:message bundle="${Loc}" key="${param.error_number_of_beds}" var="error_number_of_beds"/>
    <fmt:message bundle="${Loc}" key="${param.error_cost_room}" var="error_cost_room"/>
    <fmt:message bundle="${Loc}" key="${param.empty_beds}" var="empty_beds"/>
    <fmt:message bundle="${Loc}" key="${param.empty_cost}" var="empty_cost"/>
</div>


<html>
<head>
    <title>${hotel_name}</title>
    <link href="/css/common.css" rel="stylesheet" type="text/css"/>
    <link href="/css/index.css" rel="stylesheet" type="text/css"/>
    <link href="/css/popup.css" rel="stylesheet" type="text/css"/>
    <link href="/css/upload_image.css" rel="stylesheet" type="text/css"/>

    <meta name="viewport" content="width=device-width,initial-scale=1.0">
</head>
<body>
<div class="container">
    <div class="header">
        <div class="title font_size not_cursor">
            ${hotel_name}
            <a href="/Controller?command=gotoindexpage"><img class="home_button" src="images/common/home.png"></a>
        </div>
        <div class="account" style="${sessionScope.locale == 'en' ? 'width: 70px' : 'width: 55px'}">
            <a href="/Controller?command=gotoadditionalservicespage"
               class="nav-link a_not_decoration">${services_link}</a>
        </div>
        <div class="account" style="${sessionScope.locale == 'en' ? 'width: 90px' : ''}">
            <form action="/Controller" class="header-form" method="get">
                <select class="language_colour" name="command" onchange="submit()">
                    <option selected>${account_link}</option>
                    <option value="gotoprofilepage">${profile_link}</option>
                    <option value="gotoactiveorderspage">${orders_link}</option>
                </select>
            </form>
        </div>
        <div class="icon">
            <img class="home_button" src="images/common/language.png">
        </div>

        <div class="language">
            <form action="/Controller" class="header-form" method="get">
                <input type="hidden" name="command" value="changelocale">
                <select class="language_colour" name="locale" onchange="submit()">
                    <option value="ru" ${sessionScope.locale == 'ru' ? 'selected' : ''}>Рус</option>
                    <option value="en" ${sessionScope.locale == 'en' ? 'selected' : ''}>Eng</option>
                </select>
            </form>
        </div>

        <div class="sing_out">
            <a class="nav-link" href="/Controller?command=Logout">${sign_out_link}</a>
        </div>
    </div>

</div>
<div class="index" style="height: 350px;">
    <form action="/Controller?command=addNewRoom" method="post">
        <div class="input_wrapper">
            <div class="login_text leftstr">
                ${number_of_beds_name}
                <div style="color: red">
                    ${error_number_of_beds}
                    ${empty_beds}
                </div>
            </div>
            <div class="login_text rightstr">
                <input style="width: 250px" class="center" type="text" name="number_of_beds" required>
            </div>
        </div>
        <div class="input_wrapper">
            <div class="login_text leftstr">
                ${apartment_class_name}
            </div>
            <div class="login_text rightstr">
                <select style="width: 211px" type="text" name="apartment_class" class="language_colour">
                    <option value="Эконом">${economy_name}</option>
                    <option value="Комфорт">${comfort_name}</option>
                    <option value="Бизнес">${business_name}</option>
                    <option value="Премиум">${premium_name}</option>
                </select>
            </div>

        </div>
        <div class="input_wrapper">
            <div class="login_text leftstr">
                ${cost_name}
                <div style="color: red">
                    ${error_cost_room}
                    ${empty_cost}
                </div>
            </div>
            <div class="login_text rightstr">
                <input style="width: 250px" class="center" type="text" name="cost" required>
            </div>
        </div>

        <div align="center">
            <input type="submit" value="${add_button}">
        </div>
    </form>
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
