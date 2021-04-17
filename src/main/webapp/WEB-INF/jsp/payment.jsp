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
<fmt:message bundle="${Loc}" key="Locale.name.payment" var="payment_name"/>
<fmt:message bundle="${Loc}" key="Locale.name.type.card" var="type_card_name"/>
<fmt:message bundle="${Loc}" key="Locale.name.card.number" var="card_number_name"/>
<fmt:message bundle="${Loc}" key="Locale.name.CVV" var="CVV_name"/>
<fmt:message bundle="${Loc}" key="Locale.name.validity.card" var="validity_card_name"/>
<fmt:message bundle="${Loc}" key="Locale.name.owner.card" var="owner_card_name"/>
<fmt:message bundle="${Loc}" key="Locale.button.to.pay" var="to_pay_button"/>
<fmt:message bundle="${Loc}" key="Locale.link.pay.later" var="pay_later_name"/>
<fmt:message bundle="${Loc}" key="Locale.name.cost" var="cost_name"/>
<fmt:message bundle="${Loc}" key="Locale.footer" var="footer"/>
<fmt:message bundle="${Loc}" key="Locale.creator.designed" var="creator_designed"/>
<div style="display:none;">
    <fmt:message bundle="${Loc}" key="${param.error_of_payment}" var="error_of_payment"/>
    <fmt:message bundle="${Loc}" key="${param.card_number_error}" var="card_number_error"/>
</div>


<html>
<head>
    <title>${hotel_name}</title>
    <link href="/css/common.css" rel="stylesheet" type="text/css"/>
    <link href="/css/index.css" rel="stylesheet" type="text/css"/>
    <link href="/css/popup.css" rel="stylesheet" type="text/css"/>
    <link href="/css/edit_profile.css" rel="stylesheet" type="text/css"/>

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
                <input type="hidden" name="order_id" value="${param.order_id}">
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

<div class="index" style="width:900px ;height: 520px">
    <div align="center" style="font-size: 35px">${payment_name}</div>
    <form action="/Controller?command=topayorder" method="post">
        <input type="hidden" name="order_id" value="${param.order_id}">
        <div class="data_edit" style="margin-left: 70px; margin-top: 40px">
            <div class="leftstr">
                ${type_card_name}
            </div>
            <div class="rightstr" style="margin-right: 40px">
                <select style="width: 211px" type="text" name="type_of_card" class="language_colour">
                    <option value="visa" ${requestScope.card.typeOfCard == 'Visa' ? 'selected' : ''}>Visa</option>
                    <option value="master_card" ${requestScope.card.typeOfCard == 'Master Card' ? 'selected' : ''}>Master Card</option>
                </select>
            </div>
        </div>
        <div class="data_edit" style="margin-top: 20px">
            <div class="leftstr" style="margin-left: 70px">
                ${card_number_name}
            </div>
            <div class="rightstr" style="margin-right: 40px">
                <input style="width: 250px" type="text" class="date_color" name="card_number" value="${requestScope.card.cardNumber}">
            </div>
        </div>
        <div class="data_edit" style="margin-top: 20px">
            <div class="leftstr" style="margin-left: 70px">
                ${CVV_name}
            </div>
            <div class="rightstr" style="margin-right: 40px">
                <input style="width: 250px;" type="text" class="date_color" name="CVV" value="${requestScope.card.CVV}">
            </div>
        </div>
        <div class="data_edit" style="margin-top: 20px">
            <div class="leftstr" style="margin-left: 70px">
                ${validity_card_name}
            </div>
            <div class="rightstr" style="margin-right: 40px">
                <input style="width: 250px" class="date_color" name="validity_card" value="${requestScope.card.validityCard}">
            </div>
        </div>
        <div class="data_edit" style="margin-top: 20px">
            <div class="leftstr" style="margin-left: 70px">
                ${owner_card_name}
            </div>
            <div class="rightstr" style="margin-right: 40px">
                <input style="width: 250px;" type="text" class="date_color" name="owner_card" value="${requestScope.card.ownerCard}">
            </div>
        </div>
        <div class="data_edit" style="margin-top: 20px">
            <div class="leftstr" style="margin-left: 70px">
                ${cost_name}
            </div>
            <div class="rightstr" style="margin-right: 40px">
                ${requestScope.cost}$
            </div>
        </div>
        <div class="error_place" style="color: red;top: 570px;">
            <c:out value="${error_of_payment}"/>
        </div>
        <div class="data_edit">
            <div align="center" style="margin-top: 5px; width: 100%; display: flex; justify-content: space-around">

                <a style="margin-right: 70px" class="nav-link a_not_decoration"
                   href="/Controller?command=gotoactiveorderspage"> ${pay_later_name}</a>
                <input style="font-size: 20px" type="submit" class="edit_profile input_hover submit"
                       value="${to_pay_button}">
            </div>
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
<script src="/js/rooms.js"></script>
</body>
</html>