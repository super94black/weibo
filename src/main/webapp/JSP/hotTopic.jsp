<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="b">
    <div class="a">微博热搜</div>
    <div class="c">
        <ul class="d">
            <c:if test="${hotTopicList ne null}">
            <c:forEach var="post" varStatus="index" items="${hotTopicList}">
            <li class="aa"><a href="http://localhost:8080/post?postId=${post.id}"/><span>${index.index + 1}.</span>${post.content}</li>
            </c:forEach>
            </c:if>
        </ul>
    </div>
</div>