<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language" var="Loc"/>

<fmt:message bundle="${Loc}" key="Locale.name.hotel" var="hotel_name"/>
<fmt:message bundle="${Loc}" key="Locale.link.services" var="services_link"/>
<fmt:message bundle="${Loc}" key="Locale.link.account" var="account_link"/>
<fmt:message bundle="${Loc}" key="Locale.link.sign.out" var="sign_out_link"/>
<fmt:message bundle="${Loc}" key="Locale.link.orderRooms" var="orders_link"/>
<fmt:message bundle="${Loc}" key="Locale.link.profile" var="profile_link"/>

<fmt:message bundle="${Loc}" key="Locale.name.profile" var="profile_name"/>
<fmt:message bundle="${Loc}" key="Locale.name.personal_data" var="personal_data_name"/>
<fmt:message bundle="${Loc}" key="Locale.name.name" var="name_name"/>
<fmt:message bundle="${Loc}" key="Locale.name.surname" var="surname_name"/>
<fmt:message bundle="${Loc}" key="Locale.name.email" var="email_name"/>
<fmt:message bundle="${Loc}" key="Locale.name.phone" var="phone_name"/>
<fmt:message bundle="${Loc}" key="Locale.link.change.password" var="change_password_link"/>
<fmt:message bundle="${Loc}" key="Locale.name.edit.profile" var="edit_profile_name"/>
<fmt:message bundle="${Loc}" key="Locale.button.edit.profile" var="edit_profile_button"/>
<fmt:message bundle="${Loc}" key="Locale.name.new.password" var="new_password_name"/>
<fmt:message bundle="${Loc}" key="Locale.name.current.password" var="current_password_name"/>
<fmt:message bundle="${Loc}" key="Locale.name.repeat.new.password" var="repeat_new_password_name"/>
<fmt:message bundle="${Loc}" key="Locale.button.change.password" var="change_password_button"/>
<fmt:message bundle="${Loc}" key="Locale.name.change.password" var="change_password_name"/>
<fmt:message bundle="${Loc}" key="Locale.footer" var="footer"/>
<fmt:message bundle="${Loc}" key="Locale.creator.designed" var="creator_designed"/>
<fmt:message bundle="${Loc}" key="Locale.name.photo.successful" var="photo_sccessfully_updated"/>
<fmt:message bundle="${Loc}" key="Locale.name.photo.error" var="photo_error_updated"/>
<fmt:message bundle="${Loc}" key="Locale.name.select.image" var="select_image_name"/>
<fmt:message bundle="${Loc}" key="Locale.button.update.image" var="update_button"/>
<div style="display:none;">
    <fmt:message bundle="${Loc}" key="${param.password_error}" var="password_error"/>
    <fmt:message bundle="${Loc}" key="${param.empty_name_error}" var="empty_name_error"/>
    <fmt:message bundle="${Loc}" key="${param.empty_surname_error}" var="empty_surname_error"/>
    <fmt:message bundle="${Loc}" key="${param.user_email_error}" var="user_email_error"/>
    <fmt:message bundle="${Loc}" key="${param.successful_message}" var="successful_message"/>
    <fmt:message bundle="${Loc}" key="${param.phone_number_error}" var="phone_number_error"/>
</div>


<html>
<head>
    <title>${hotel_name}</title>
    <link href="/css/common.css" rel="stylesheet" type="text/css"/>
    <link href="/css/index.css" rel="stylesheet" type="text/css"/>
    <link href="/css/profile.css" rel="stylesheet" type="text/css"/>
    <link href="/css/edit_profile.css" rel="stylesheet" type="text/css"/>
    <link href="/css/popup.css" rel="stylesheet" type="text/css"/>
    <link href="/css/upload_image.css" rel="stylesheet" type="text/css"/>
    <meta name="viewport" content="width=device-width,initial-scale=1.0">

</head>
<body class="not_cursor">
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
                    <option selected> ${account_link}</option>
                    <option value="gotoprofilepage"> ${profile_link}</option>
                    <option value="gotoactiveorderspage"> ${orders_link}</option>
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
            <a class="nav-link" href="/Controller?command=Logout"> ${sign_out_link}</a>
        </div>
    </div>

    <div class="profile">
        <div style="font-size: 35px;" class="center colour"> ${profile_name}</div>

        <div class="size_img">
            <div class="relative_class">
                <img class="img_border" src="<c:out value="${sessionScope.user.urlPhoto}"/>">
            </div>
            <div align="center">
                <form action="/UploadAvatar" method="post" enctype="multipart/form-data">
                    <div class="upload_image">
                        <div class="form-group">
                            <input type="file"  name="file" id="file" class="input-file"  required>
                            <label for="file" class="btn btn-tertiary js-labelFile">
                                <span class="js-fileName">${select_image_name}</span>
                            </label>
                        </div>
                    </div>
                    <input style="margin-top: 15px" type="submit" value="${update_button}">
                </form>
                <a href="#popup">
                    <img class="edit_profile_button input_hover submit"
                         src="images/common/pencil_edit_profile.png">
                </a>
            </div>
        </div>
        <div align="left" class="size_personal_data">
            <div class="line"></div>
            <div class="personal_date_size"> ${personal_data_name}</div>
            <div class="data">
                ${name_name}:
                <div class="date_color">
                    <c:out value="${sessionScope.user.name}"></c:out>
                </div>
            </div>
            <div class="data">
                ${surname_name}:
                <div class="date_color">
                    <c:out value="${sessionScope.user.surname}"></c:out>
                </div>
            </div>
            <div class="data">
                ${email_name}:
                <div class="date_color">
                    <c:out value="${sessionScope.user.email}"></c:out>
                </div>
            </div>
            <div class="data">
                ${phone_name}:
                <div class="date_color">
                    <c:out value="${sessionScope.user.phoneNumber}"></c:out>
                </div>
            </div>
            <div class="data">
                <a href="#popup2" class="a_not_decoration">
                    <div class="language_colour">
                        ${change_password_link}
                    </div>
                </a>
            </div>
            <div>
                <div style="color: red"><c:out value="${password_error}"/></div>
                <div style="color: red"><c:out value="${empty_name_error}"/></div>
                <div style="color: red"><c:out value="${empty_surname_error}"/></div>
                <div style="color: red"><c:out value="${user_email_error}"/></div>
                <div style="color: red"><c:out value="${phone_number_error}"/></div>
                <div style="color: #0fc60f"><c:out value="${successful_message}"/></div>
                <c:if test="${param.uploadResult == true}">
                    <div style="color: #0fc60f"><c:out value="${photo_sccessfully_updated}"/></div>
                </c:if>
                <c:if test="${param.uploadResult == false}">
                    <div style="color: #0fc60f"><c:out value="${photo_error_updated}"/></div>
                </c:if>
            </div>
        </div>
    </div>
</div>
<div id="popup" class="popup">
    <a href="" class="popup_area"></a>
    <div class="popup_body">
        <div class="popup_content">
            <a href="#" class="popup_close">X</a>
            <div class="popup_title" align="center">${edit_profile_name}</div>
            <hr class="footer_hr">
            <div class="popup_text">
                <form action="/Controller?command=changeprofile" method="post">
                    <div class="data_edit">
                        <div class="leftstr">
                            ${name_name}
                        </div>
                        <div class="rightstr">
                            <input style="width: 250px; " type="text" class="date_color" style="margin-left: 12px"
                                   name="name" value="<c:out value="${sessionScope.user.name}"></c:out>">
                        </div>
                        <div class="leftstr" style="margin-left: 70px">
                            ${surname_name}
                        </div>
                        <div class="rightstr">
                            <input style="width: 250px" type="text" class="date_color" name="surname"
                                   value="<c:out value="${sessionScope.user.surname}"></c:out>">
                        </div>

                    </div>
                    <div class="data_edit">
                        <div class="leftstr">
                            ${email_name}
                        </div>
                        <div class="rightstr">
                            <input style="width: 250px;" type="text" class="date_color" name="email"
                                   value="<c:out value="${sessionScope.user.email}"></c:out>">
                        </div>
                        <div class="leftstr" style="margin-left: 70px">
                            ${phone_name}
                        </div>
                        <div class="rightstr">
                            <input style="width: 250px" class="date_color" name="phone_number"
                                   value="<c:out value="${sessionScope.user.phoneNumber}"></c:out>">
                        </div>

                    </div>
                    <div class="data_edit">
                        <div align="center" style="margin-top: 45px">
                            <input type="submit" class="edit_profile input_hover submit" value="${edit_profile_button}">
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</div>


<div id="popup2" class="popup">
    <a href="" class="popup_area"></a>
    <div class="popup_body">
        <div class="popup_content">
            <a href="#" class="popup_close">X</a>
            <div class="popup_title" align="center">${change_password_name}</div>
            <hr class="footer_hr">
            <div class="popup_text">
                <form action="/Controller?command=CHANGEPASSWORD" method="post">
                    <div style="padding-bottom: 45px">
                        <div class="login_text leftstr">
                            ${current_password_name}
                        </div>
                        <div class="login_text rightstr">
                            <input type="password" class="date_color" name="current_password">
                        </div>
                    </div>
                    <div style="padding-bottom: 45px">
                        <div class="login_text leftstr">
                            ${new_password_name}
                        </div>
                        <div class="login_text rightstr">
                            <input type="password" class="date_color" name="new_password">
                        </div>
                    </div>
                    <div>
                        <div class="login_text leftstr">
                            ${repeat_new_password_name}
                        </div>
                        <div class="login_text rightstr">
                            <input type="password" class="date_color" name="repeat_password">
                        </div>
                    </div>


                    <div class="password_edit">
                        <div align="center" style="margin-top: 45px">
                            <input type="submit" class="data_edit input_hover submit" value="${change_password_button}">
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
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
<script src="../../js/loadAvatar.js"></script>
</body>
</html>
