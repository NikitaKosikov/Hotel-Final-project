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

<fmt:message bundle="${Loc}" key="Locale.button.search" var="search_button"/>
<fmt:message bundle="${Loc}" key="Locale.placeholder.number.of.beds" var="number_of_beds_placeholder"/>
<fmt:message bundle="${Loc}" key="Locale.placeholder.apartment.class" var="apartment_class_placeholder"/>
<fmt:message bundle="${Loc}" key="Locale.placeholder.check.in" var="check_in_placeholder"/>
<fmt:message bundle="${Loc}" key="Locale.placeholder.check.out" var="check_out_placeholder"/>
<fmt:message bundle="${Loc}" key="Locale.placeholder.cost" var="cost_placeholder"/>
<fmt:message bundle="${Loc}" key="Locale.placeholder.format.date" var="format_date_placeholder"/>
<fmt:message bundle="${Loc}" key="Locale.button.reservation" var="reservation_button"/>
<fmt:message bundle="${Loc}" key="Locale.name.reservation" var="reservation_name"/>
<fmt:message bundle="${Loc}" key="Locale.name.check.in" var="check_in_name"/>
<fmt:message bundle="${Loc}" key="Locale.name.check.out" var="check_out_name"/>
<fmt:message bundle="${Loc}" key="Locale.name.number.of.beds" var="number_of_beds_name"/>
<fmt:message bundle="${Loc}" key="Locale.name.cost" var="cost_name"/>
<fmt:message bundle="${Loc}" key="Locale.name.economy" var="economy_name"/>
<fmt:message bundle="${Loc}" key="Locale.name.comfort" var="comfort_name"/>
<fmt:message bundle="${Loc}" key="Locale.name.business" var="business_name"/>
<fmt:message bundle="${Loc}" key="Locale.name.premium" var="premium_name"/>
<fmt:message bundle="${Loc}" key="Locale.name.apartment.class" var="apartment_class_name"/>
<fmt:message bundle="${Loc}" key="Locale.link.add.room" var="add_room_link"/>
<fmt:message bundle="${Loc}" key="Locale.link.change.room" var="change_room_link"/>
<fmt:message bundle="${Loc}" key="Locale.button.unblocked.rooms" var="unblocked_room_button"/>
<fmt:message bundle="${Loc}" key="Locale.link.blocked.rooms" var="blocked_rooms_link"/>
<fmt:message bundle="${Loc}" key="Locale.footer" var="footer"/>
<fmt:message bundle="${Loc}" key="Locale.creator.designed" var="creator_designed"/>

<div style="display:none;">
    <fmt:message bundle="${Loc}" key="${param.error_cost_room}" var="error_cost_room"/>
    <fmt:message bundle="${Loc}" key="${param.error_format_date}" var="error_format_date"/>
    <fmt:message bundle="${Loc}" key="${param.error_number_of_beds}" var="error_number_of_beds"/>
    <fmt:message bundle="${Loc}" key="${param.error_date}" var="error_date"/>
    <fmt:message bundle="${Loc}" key="${empty_list_of_room}" var="empty_list_of_room"/>
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
    <c:if test="${sessionScope.user.roleId == 2}">
        <div align="center" class="account"
             style="${sessionScope.locale == 'en' ? 'width: 70px' : 'width: 55px'}; width: 98%">
            <a href="/Controller?command=gotoblockedroomspage"
               class="nav-link a_not_decoration">${blocked_rooms_link}</a>
            <a href="/Controller?command=gotoaddroompage" class="nav-link a_not_decoration">${add_room_link}</a>
        </div>
    </c:if>
</div>

<div class="index">
    <form action="/Controller" method="get">
        <input type="hidden" name="command" value="SEARCHROOMSBYCRYTERIA">
        <div class="outline" style="padding-bottom: 10px">
            <div class="search">
                <div>
                    <input class="date_color" style="margin-right: 330px" type="text" name="number_of_beds"
                           placeholder="${number_of_beds_placeholder}"
                           value="<c:out value="${number_of_beds}"/>">
                    <span class="error_place" style="color:red; top:176px; left: 330px; font-size: 15px">
                            <c:out value="${error_number_of_beds}"/>
                            <c:if test="${sessionScope.user.roleId == 2}">
                                <div style="color:#0fc60f;"><c:out value="${changed_room}"/></div>
                                <div style="color:#0fc60f;"><c:out value="${deleted_room}"/></div>
                                <div style="color:red;"><c:out value="${deleted_room_error}"/></div>
                                <div style="color:red;"><c:out value="${change_room_error}"/></div>
                            </c:if>
                        </span>

                    <input class="nav-link" type="submit" style="margin-right: 340px; width:210px; font-size: 20px;"
                           value="${search_button}">


                    <input class="date_color" type="text" name="cost_room" placeholder="${cost_placeholder}"
                           value="<c:out value="${cost_room}"/>">
                    <span class="error_place" style="color:red; top:173px; left: 900px;font-size: 15px">
                            <c:out value="${error_cost_room}"/>
                    </span>
                </div>
                <div style="margin-top: 20px" class="center">
                    <input class="date_color" maxlength="10" style="margin-right: 330px" type="text" name="arrival_date"
                           placeholder="${check_in_placeholder} (${format_date_placeholder})"
                           value="<c:out value="${arrival_date}"/>">
                    <div style="color: red;top:223px; left: 330px; font-size: 15px" class="error_place">
                        <c:out value="${error_format_date}"/>
                    </div>
                    <input class="date_color" maxlength="10" style="margin-right: 339px" type="text"
                           name="departure_date" placeholder="${check_out_placeholder} (${format_date_placeholder})"
                           value="<c:out value="${departure_date}"/>">
                    <select style="width: 211px" type="text" name="apartment_class" class="language_colour">
                        <option value="" ${apartment_class == apartment_class_name ? 'selected' : ''}>${apartment_class_name}</option>
                        <option value="Эконом" ${apartment_class == economy_name ? 'selected' : ''}>${economy_name}</option>
                        <option value="Комфорт" ${apartment_class == comfort_name ? 'selected' : ''}>${comfort_name}</option>
                        <option value="Бизнес" ${apartment_class == business_name ? 'selected' : ''}>${business_name}</option>
                        <option value="Премиум" ${apartment_class == premium_name ? 'selected' : ''}>${premium_name}</option>
                    </select>
                </div>
            </div>
        </div>
    </form>
    <div style="color: red; top:259px; font-size: 20px" class="error_place"><c:out value="${access_room}"/></div>

    <div class="slider" style="font-size: 16px">
        <div class="error_place" align="center" style="color: red; top: 240px; font-size: 17px">${error_date}</div>
        <c:choose>
            <c:when test="${rooms == null || rooms.size()==0}">
                <div class="font_size" style="margin-top: 150px" align="center">${empty_list_of_room}</div>
            </c:when>
            <c:when test="${rooms.size() != 0 && rooms!=null}">
                <c:set var="count" value="${0}"/>
                <c:forEach items="${rooms}" varStatus="status">
                    <c:if test="${count<rooms.size()}">

                        <div class="item">

                            <c:forEach begin="0" end="2" step="1">
                                <c:if test="${count<rooms.size()}">
                                    <div class="img_room">
                                        <div>
                                            <div>
                                                <img class="img_border"
                                                     src="<c:out value="${rooms[status.begin + count].getUrlPhoto()}"/>">
                                                <div><c:out value="${rooms[status.begin+count].apartmentClass}"/></div>
                                                <div>
                                                    <c:if test="${sessionScope.user.roleId == 2}">
                                                        <div class="leftstr">
                                                                ${number_of_beds_name}:
                                                            <span style="color:dodgerblue;">
                                                            <c:out value="${rooms[status.begin+count].numberOfBeds}"/>
                                                    </span>
                                                        </div>
                                                    </c:if>
                                                    <c:if test="${sessionScope.user.roleId != 2}">
                                                        <div>
                                                                ${number_of_beds_name}:
                                                            <span style="color:dodgerblue;">
                                                            <c:out value="${rooms[status.begin+count].numberOfBeds}"/>
                                                    </span>
                                                        </div>
                                                    </c:if>
                                                    <div>
                                                        <c:if test="${sessionScope.user.roleId == 2}">
                                                            <div>
                                                                <form style="margin-bottom: 5px;"
                                                                      action="/Controller?command=unblockroom"
                                                                      method="post">
                                                                    <input type="hidden" name="room_id"
                                                                           value="${rooms[status.begin+count].roomId}">
                                                                    <input type="submit"
                                                                           value="${unblocked_room_button}">
                                                                </form>
                                                            </div>
                                                        </c:if>
                                                    </div>

                                                </div>
                                                <div>
                                                    <c:if test="${sessionScope.user.roleId == 2}">
                                                        <div class="leftstr">
                                                                ${cost_name}:
                                                            <span style="color:dodgerblue;">
                                                                <c:out value="${rooms[status.begin+count].cost}"/>$
                                                            </span>
                                                        </div>
                                                        <a style="font-size: 16px;"
                                                           href="/Controller?command=gotochangeroompage&room_id=${rooms[status.begin+count].roomId}"
                                                           class="nav-link a_not_decoration">${change_room_link}</a>
                                                    </c:if>
                                                    <c:if test="${sessionScope.user.roleId != 2}">
                                                        <div>
                                                                ${cost_name}:
                                                            <span style="color:dodgerblue;">
                                                                <c:out value="${rooms[status.begin+count].cost}"/>$
                                                            </span>
                                                        </div>
                                                    </c:if>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:if>
                                <c:set var="count" value="${count + 1}"/>
                            </c:forEach>
                        </div>
                    </c:if>
                </c:forEach>
                <a class="prev" onclick="minusSlide()">&#10094;</a>
                <a class="next" onclick="plusSlide()">&#10095;</a>
                <div style="padding: 0px" class="input_wrapper" align="center">
                    <div id="current_slide" align="center" class="login_text"></div>
                    <div class="login_text">/</div>
                    <div align="center" class="login_text">
                        <c:if test="${requestScope.rooms.size()!=0 && requestScope.rooms!=null}">
                            <mytag:getCountSlides>${requestScope.rooms.size()}</mytag:getCountSlides>
                        </c:if>
                    </div>
                </div>
            </c:when>
        </c:choose>
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
<script src="/js/rooms.js"></script>
</body>
</html>