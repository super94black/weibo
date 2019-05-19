<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<div class="comment-row">
    <input type="hidden" id="nickName" value="${user.nickname}">
    <input type="hidden" id="userImg" value="${user.headIcon}">
    <div id="list">
        <c:forEach items="${map}" var="dataMap">
        <div class="box clearfix">
            <c:set var="mapKey" value='${dataMap.key}'/>
            <a href="http://localhost:8080/user?uid=${mapKey.user.id}" id="aaaaa"><img class="head" src="${mapKey.user.headIcon}" alt=""/></a>
            <div class="content">
                <div class="main">
                    <p class="txt">
                        <span class="user">${mapKey.user.nickname}：</span>${mapKey.post.content}
                    </p>
                    <c:if test="${mapKey.post.img_add ne null}">
                    <img class="pic" src="${mapKey.post.img_add}" alt=""/>
                    </c:if>
                </div>
                <div class="info clearfix">
                    <span class="time">${mapKey.post.create_time}</span>

                    <c:if test="${not empty user}">
                    <c:if test="${mapKey.isZan eq 1}">
                    <a  href="javascript:void(0);" class="praise" onclick="zan('${mapKey.zanCount}','${mapKey.isZan}','${user.id}','${mapKey.post.id}')">取消赞</a>
                    </c:if>
                    <c:if test="${mapKey.isZan eq 0}">
                    <a  href="javascript:void(0);" class="praise" onclick="zan('${mapKey.zanCount}','${mapKey.isZan}','${user.id}','${mapKey.post.id}')">${mapKey.zanCount}赞</a>
                    </c:if>
                    </c:if>
                    <c:if test="${mapKey.post.uid eq user.id}">
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <a href="javascript:void(0);" class="comment-operate" onclick="deletePost('${mapKey.post.id}')">删除</a>
                    </c:if>
                </div>
                <div class="praises-total" total="0">${mapKey.zanCount}个人觉得很赞</div>
                <div class="comment-list">
                    <%-- 评论开始--%>
                    <c:forEach items="${dataMap.value}" var="postvo">
                    <div class="comment-box clearfix" class="other">
                        <a href="http://localhost:8080/user?uid=${postvo.user.id}"><img class="myhead" src="${postvo.user.headIcon}" alt=""/></a>
                        <div class="comment-content">

                            <p class="comment-text"><span class="user">
                                <c:if test="${not empty postvo.replayName}">
                                ${postvo.user.nickname}:回复${postvo.replayName}:
                                </c:if>
                                <c:if test="${empty postvo.replayName}">
                                    ${postvo.user.nickname}:
                                </c:if>
                                </span>${postvo.post.content}</p>

                            <p class="comment-time">
                                ${postvo.post.create_time}
                                    <c:if test="${postvo.post.uid eq user.id}">
                                        <a href="javascript:void(0);"  class="comment-del"  onclick="deletePost('${postvo.post.id}')">删除</a>
                                    </c:if>
                                <%--<a href="javascript:void(0);" class="comment-praise" total="0" my="0">赞</a>--%>
                                <c:if test="${not empty user.id}">
                                <a href="javascript:void(0);" class="comment-operate" onclick="replayOther('${postvo.post.id}','${user.id}')">回复</a>
                                </c:if>

                            </p>
                        </div>
                    </div>
                    </c:forEach>

                    <%--评论结束--%>

                </div>

                <div class="text-box">
                    <textarea class="comment" autocomplete="off" onclick="replayOther('${mapKey.post.id}','${user.id}')">评论…</textarea>
                    <button class="btn ">回 复</button>
                    <span class="word"><span class="length">0</span>/140</span>
                </div>

            </div>

        </div>
        </c:forEach>
    </div>
</div>

<script type="text/javascript" src="/assets/js/main.js"></script>