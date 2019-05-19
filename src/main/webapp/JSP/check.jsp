<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<%@ include file="include/head.jsp" %>
<body>
<%@ include file="include/header.jsp" %>

<div class="row">

    <div id="list">
        <c:forEach var="postPo" items="${list}">
            <div class="box clearfix">

                <a href="http://localhost:8080/user?uid=${postPo.user.id}"><img class="head" src="${postPo.user.headIcon}" alt=""/></a>
                <div class="content">
                    <div class="main">
                        <p class="txt">
                            <span class="user">${postPo.user.nickname}：</span>${postPo.post.content}
                        </p>
                        <c:if test="${postPo.post.img_add ne null}">
                        <img class="pic" src="${postPo.post.img_add}" alt=""/>
                        </c:if>
                    </div>
                    <div class="info clearfix">
                        <span class="time">${postPo.post.create_time}</span>

                        <c:if test="${sessionScope.user.type eq 2}">
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <a href="javascript:void(0);" onclick="check('${postPo.post.id}')">通过</a>

                        </c:if>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>


<%@ include file="include/paging.jsp"%>

<%@ include file="include/footer.jsp" %>
</body>
</html>
