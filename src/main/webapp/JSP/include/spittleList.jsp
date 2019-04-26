<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<div class="row">
    <div id="list">
        <c:forEach items="${map}" var="dataMap">
        <div class="box clearfix">
            <c:set var="mapKey" value='${dataMap.key}'/>
            <img class="head" src="${mapKey.spitter.headIcon}" alt=""/>
            <div class="content">
                <div class="main">
                    <p class="txt">
                        <span class="user">${mapKey.spitter.username}：</span>${mapKey.post.content}
                    </p>
                    <img class="pic" src="${mapKey.post.img_add}" alt=""/>
                </div>
                <div class="info clearfix">
                    <span class="time">${mapKey.post.create_time}</span>
                    <a class="praise" href="javascript:;">赞</a>
                </div>
                <div class="praises-total" total="0" style="display: none;"></div>
                <div class="comment-list">
                    <%-- 评论开始--%>
                    <c:forEach items="${dataMap.value}" var="postvo">
                    <div class="comment-box clearfix" user="other">
                        <img class="myhead" src="${postvo.spitter.headIcon}" alt=""/>
                        <div class="comment-content">
                            <p class="comment-text"><span class="user">
                                <c:if test="${not empty postvo.replayName}">
                                ${postvo.spitter.username}:回复${postvo.replayName}
                                </c:if>
                                <c:if test="${empty postvo.replayName}">
                                    ${postvo.spitter.username}
                                </c:if>
                                ：</span>${postvo.post.content}</p>
                            <p class="comment-time">
                                ${postvo.post.create_time}
                                <a href="javascript:;" class="comment-praise" total="0" my="0">赞</a>
                                <a href="javascript:;" class="comment-operate">回复</a>
                            </p>
                        </div>
                    </div>
                    </c:forEach>

                    <%--评论结束--%>

                </div>

                <div class="text-box">
                    <textarea class="comment" autocomplete="off">评论…</textarea>
                    <button class="btn ">回 复</button>
                    <span class="word"><span class="length">0</span>/140</span>
                </div>
            </div>
        </div>
        </c:forEach>
    </div>
</div>
