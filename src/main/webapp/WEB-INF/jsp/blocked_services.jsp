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
<fmt:message bundle="${Loc}" key="Locale.name.services" var="services_name"/>
<fmt:message bundle="${Loc}" key="Locale.name.orderRoom.taxi" var="order_taxi_name"/>
<fmt:message bundle="${Loc}" key="Locale.name.cleanup" var="cleaup_name"/>
<fmt:message bundle="${Loc}" key="Locale.name.gym" var="gym_name"/>
<fmt:message bundle="${Loc}" key="Locale.name.cost" var="cost_name"/>
<fmt:message bundle="${Loc}" key="Locale.name.orderRoom" var="order_name"/>
<fmt:message bundle="${Loc}" key="Locale.name.orderRooms" var="orders_name"/>
<fmt:message bundle="${Loc}" key="Locale.name.check.in" var="check_in_name"/>
<fmt:message bundle="${Loc}" key="Locale.name.check.out" var="check_out_name"/>
<fmt:message bundle="${Loc}" key="Locale.name.date.service" var="date_service_name"/>
<fmt:message bundle="${Loc}" key="Locale.placeholder.format.date" var="format_date_placeholder"/>

<fmt:message bundle="${Loc}" key="Locale.link.add.service" var="add_service_link"/>
<fmt:message bundle="${Loc}" key="Locale.link.change.service" var="change_service_link"/>
<fmt:message bundle="${Loc}" key="Locale.button.unblock" var="unblock_button"/>
<fmt:message bundle="${Loc}" key="Locale.footer" var="footer"/>
<fmt:message bundle="${Loc}" key="Locale.creator.designed" var="creator_designed"/>
<div style="display:none;">
    <fmt:message bundle="${Loc}" key="${param.exist_service}" var="exist_service"/>
    <fmt:message bundle="${Loc}" key="${param.deleted_service}" var="deleted_service"/>
    <fmt:message bundle="${Loc}" key="${param.deleted_service_error}" var="deleted_service_error"/>
    <fmt:message bundle="${Loc}" key="${param.change_service_error}" var="change_service_error"/>
    <fmt:message bundle="${Loc}" key="${param.added_service}" var="added_service"/>
    <fmt:message bundle="${Loc}" key="${param.changed_service}" var="changed_service"/>
    <fmt:message bundle="${Loc}" key="${param.successful_message}" var="successful_message"/>
    <fmt:message bundle="${Loc}" key="${param.error_service}" var="error_service"/>
    <fmt:message bundle="${Loc}" key="${param.select_service}" var="select_service"/>
    <fmt:message bundle="${Loc}" key="${param.successful_unblock}" var="successful_unblock"/>
</div>

<html>
<head>
    <title>${hotel_name}</title>
    <link href="/css/common.css" rel="stylesheet" type="text/css"/>
    <link href="/css/index.css" rel="stylesheet" type="text/css"/>
    <link href="/css/popup.css" rel="stylesheet" type="text/css"/>
    <link href="/css/services.css" rel="stylesheet" type="text/css"/>

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

    <div class="slider index" style="height: 520px; font-size: 20px">
        <div style="font-size: 35px;" class="center colour">
            ${services_name}
            <c:if test="${sessionScope.user.roleId == 2}">
                <div align="left">
                    <div align="center" style="color: red; font-size: 17px">
                        <c:out value="${change_service_error}"/>
                    </div>
                    <div align="center" style="color: #0fc60f; font-size: 17px">
                        <c:out value="${changed_service}"/>
                        <c:out value="${successful_unblock}"/>
                    </div>
                </div>
            </c:if>
        </div>


        <div style="color:#0fc60f;top: 205px;left: 610px" class="error_place">
            <c:out value="${successful_message}"/>
        </div>
        <div style="color: red;top: 205px;left: 680px" class="error_place">
            ${select_service}
        </div>
        <div style="color: red; top: 205px" class="error_place"><c:out value="${error_service}"/></div>
        <c:choose>
            <c:when test="${additional_services.size() == 0}">
                <div class="font_size" style="margin-top: 100px" align="center"><c:out
                        value="${empty_list}"></c:out></div>
            </c:when>
            <c:when test="${additional_services.size() != 0}">
                <c:set var="count" value="${0}"/>
                <c:forEach items="${additional_services}" varStatus="status">
                    <c:if test="${count<additional_services.size()}">
                        <div class="item" style="margin-top: 10px;">
                            <c:forEach begin="0" end="2" step="1">
                                <c:if test="${count<additional_services.size()}">
                                    <div class="img_room" style="margin-right: 30px; margin-left: 57px;">
                                        <div>
                                            <div>
                                                <img class="img_border" style="width: 350px"
                                                     src="<c:out value="${additional_services[status.begin + count].getUrlPhoto()}"/>">
                                                <div>
                                                    <c:out value="${additional_services[status.begin+count].name}"/>
                                                </div>
                                                <div>
                                                        ${cost_name}
                                                    <span style="color: dodgerblue"><c:out
                                                            value="${additional_services[status.begin+count].cost}"/>$</span>
                                                </div>
                                                <div>

                                                    <c:if test="${sessionScope.user.roleId == 2}">
                                                        <a style="font-size: 16px"
                                                           href="/Controller?command=gotochangeservicepage&service_id=${additional_services[status.begin+count].serviceId}"
                                                           class="nav-link a_not_decoration leftstr">${change_service_link}</a>
                                                        <form class="rightstr"
                                                              action="/Controller?command=unblockservice" method="post">
                                                            <input type="hidden" name="service_id"
                                                                   value="${additional_services[status.begin+count].serviceId}">
                                                            <input type="submit" value="${unblock_button}">
                                                        </form>
                                                    </c:if>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:if>
                                <c:set var="count" value="${count+1}"/>
                            </c:forEach>
                        </div>
                    </c:if>
                </c:forEach>

                <a class="prev" onclick="minusSlide()">&#10094;</a>
                <a class="next" onclick="plusSlide()">&#10095;</a>
                <div class="input_wrapper" style="padding: 0" align="center">
                    <div id="current_slide" align="center" class="login_text"></div>
                    <div class="login_text">/</div>
                    <div align="center" class="login_text">
                        <mytag:getCountSlides>${requestScope.additional_services.size()}</mytag:getCountSlides>
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
<script src="/js/additionalServices.js"></script>
</body>
</html>
