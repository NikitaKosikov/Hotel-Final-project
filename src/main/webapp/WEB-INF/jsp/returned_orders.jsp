<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="mytag" uri="/WEB-INF/tld/taglib.tld" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language" var="Loc"/>

<fmt:message bundle="${Loc}" key="Locale.name.hotel" var="hotel_name"/>
<fmt:message bundle="${Loc}" key="Locale.link.services" var="services_link"/>
<fmt:message bundle="${Loc}" key="Locale.link.account" var="account_link"/>
<fmt:message bundle="${Loc}" key="Locale.link.sign.out" var="sign_out_link"/>
<fmt:message bundle="${Loc}" key="Locale.link.profile" var="profile_link"/>
<fmt:message bundle="${Loc}" key="Locale.link.orderRooms" var="orders_link"/>


<fmt:message bundle="${Loc}" key="Locale.name.orderRooms" var="orders_name"/>
<fmt:message bundle="${Loc}" key="Locale.link.active.orderRooms" var="active_orders_link"/>
<fmt:message bundle="${Loc}" key="Locale.link.archive.orderRooms" var="archive_orders_link"/>
<fmt:message bundle="${Loc}" key="Locale.link.returned.orderRooms" var="returned_orders_link"/>
<fmt:message bundle="${Loc}" key="Locale.name.check.in" var="check_in_name"/>
<fmt:message bundle="${Loc}" key="Locale.name.check.out" var="check_out_name"/>
<fmt:message bundle="${Loc}" key="Locale.name.cost" var="cost_name"/>
<fmt:message bundle="${Loc}" key="Locale.link.room.information" var="room_information_link"/>
<fmt:message bundle="${Loc}" key="Locale.name.number.of.beds" var="number_of_beds_name"/>
<fmt:message bundle="${Loc}" key="Locale.name.apartment.class" var="apartment_class_name"/>
<fmt:message bundle="${Loc}" key="Locale.name.cost" var="cost_name"/>
<fmt:message bundle="${Loc}" key="Locale.name.reservation" var="reservation_name"/>
<fmt:message bundle="${Loc}" key="Locale.name.room.information" var="room_information_name"/>
<fmt:message bundle="${Loc}" key="Locale.link.view.my.services" var="view_my_service_link"/>
<fmt:message bundle="${Loc}" key="Locale.footer" var="footer"/>
<fmt:message bundle="${Loc}" key="Locale.creator.designed" var="creator_designed"/>
<div style="display:none;">
    <fmt:message bundle="${Loc}" key="${requestScope.empty_order_list}" var="empty_order_list"/>
</div>

<html>
<head>
    <title>${hotel_name}</title>
    <link href="/css/common.css" rel="stylesheet" type="text/css"/>
    <link href="/css/index.css" rel="stylesheet" type="text/css"/>
    <link href="/css/popup.css" rel="stylesheet" type="text/css"/>

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
    <div class="index" style="height: 460px">
        <div style="font-size: 35px;" class="center colour">
            ${orders_name}
        </div>
        <div class="hr_status">
            <a href="/Controller?command=gotoactiveorderspage&status=забронирован"
               class="nav-link a_not_decoration status_order"
               style="margin-left: 55px; ${sessionScope.locale == 'en' ? 'margin-right:482;' : ''}">${active_orders_link}</a>
            <a href="/Controller?command=gotoarchiveorderspage&status=оплачен"
               class="nav-link a_not_decoration status_order">${archive_orders_link}</a>
            <a href="#" class="nav-link a_not_decoration colour_order"
               style="font-size: 25px">${returned_orders_link}</a>
        </div>

        <div class="slider">
            <c:choose>
                <c:when test="${empty_order_list != null}">
                    <div class="font_size" style="margin-top: 100px" align="center">${empty_order_list}</div>
                </c:when>
                <c:when test="${orderRooms.size() != 0}">
                    <c:set var="count" value="${0}"/>
                    <c:forEach var="room" items="${orderRooms}" varStatus="status">
                        <c:if test="${count<orderRooms.size()}">
                            <div class="item" style="margin-top: 80px; margin-bottom: 55px">
                                <c:forEach begin="0" end="2" step="1">
                                    <div class="img_room">
                                        <c:if test="${count<orderRooms.size()}">
                                            <div>
                                                <div style="margin-left: 40px; margin-right: 50px">
                                                    <div style="padding: 5px">№<c:out
                                                            value="${count + 1}"></c:out></div>
                                                    <div style="padding: 5px">
                                                            ${check_in_name}:
                                                        <span style="color: dodgerblue"><c:out
                                                                value="${orderRooms[status.begin+count].arrivalDate}"/></span>
                                                    </div>
                                                    <div style="padding: 5px">
                                                            ${check_out_name}:
                                                        <span style="color: dodgerblue"><c:out
                                                                value="${orderRooms[status.begin+count].departureDate}"/></span>
                                                    </div>
                                                    <div style="padding: 5px">
                                                            ${cost_name}:
                                                        <span style="color: dodgerblue"><c:out
                                                                value="${orderRooms[status.begin+count].cost}"/>$</span>
                                                    </div>
                                                    <div style="padding: 5px" class="hr_order">
                                                        <a href="#popup${count}"
                                                           class="language_colour a_not_decoration"
                                                           onclick="moreDetails()">
                                                                ${room_information_link}
                                                        </a>
                                                    </div>
                                                    <div style="padding: 5px">
                                                        <a href="Controller?command=showmyadditionalservicepage&order_id=${requestScope.orderRooms[count].orderId}"
                                                           class="language_colour a_not_decoration">
                                                                ${view_my_service_link}
                                                        </a>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:if>
                                    </div>
                                    <c:set var="count" value="${count+1}"/>
                                </c:forEach>
                            </div>
                        </c:if>
                        <script>plusSlide()</script>
                    </c:forEach>
                    <script>plusSlide()</script>
                    <a style="margin-top: -100px" class="prev" onclick="minusSlide()">&#10094;</a>
                    <a style="margin-top: -100px" class="next" onclick="plusSlide()">&#10095;</a>
                    <div class="input_wrapper" align="center">
                        <div id="current_slide" align="center" class="login_text"></div>
                        <div class="login_text">/</div>
                        <div align="center" class="login_text">
                            <mytag:getCountSlides>${requestScope.orderRooms.size()}</mytag:getCountSlides>
                        </div>
                    </div>
                </c:when>
            </c:choose>
        </div>
    </div>
</div>


<c:set var="count_popup_details" value="${0}"/>
<c:forEach items="${rooms}" varStatus="status">
    <div id="popup${count_popup_details}" class="popup">
        <a href="" class="popup_area"></a>
        <div class="popup_body">
            <div class="popup_content">
                <a href="#" class="popup_close">X</a>
                <div class="popup_title center">${room_information_name}</div>
                <hr class="footer_hr">
                <div class="popup_text">
                    <c:if test="${count_popup_details<rooms.size()}">
                        <div style="padding-bottom: 45px">
                            <div class="login_text leftstr">
                                    ${number_of_beds_name}:
                            </div>
                            <div id="number_of_beds_output" class="login_text rightstr">
                                <c:out value="${rooms[status.begin+count_popup_details].numberOfBeds}"/>
                            </div>
                        </div>
                        <div style="padding-bottom: 45px">
                            <div class="login_text leftstr">
                                    ${apartment_class_name}:
                            </div>
                            <div class="login_text rightstr">
                                <c:out value="${rooms[status.begin+count_popup_details].apartmentClass}"/>
                            </div>
                        </div>
                        <div style="padding-bottom: 45px">
                            <div class="login_text leftstr">
                                    ${cost_name}:
                            </div>
                            <div class="login_text rightstr">
                                <c:out value="${rooms[status.begin+count_popup_details].cost}"/>
                            </div>
                        </div>
                    </c:if>
                    <c:set var="count_popup_details" value="${count_popup_details + 1}"/>
                </div>
            </div>
        </div>
    </div>
</c:forEach>

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
<script src="/js/rooms.js"></script>
</html>