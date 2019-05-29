<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
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

                        <c:if test="${postPo.video ne null && postPo.video.name ne null}">
                            <input type="hidden" id="videoname1" value="${postPo.video.name}">
                            <div id="player1">
                            </div>
                            <script type="text/javascript">

                                var videoAdd = $("#videoname1").val();

                                jwplayer("player1").setup({
                                    "flashplayer": "http://localhost:8080/jwplayer.flash.swf", //player.swf文件的uri
                                    "file": "http://localhost/vod/" + videoAdd,//视频文件路径
                                    "aspectratio": "16:9",//播放器自适应比例
                                    "height": "360",//播放器高度
                                    "type":"",//播放文件类型（可选）
                                    "description": "",//描述（可选）
                                    "repeat":"true",//重复播放（留空则不重复播放）
                                    "autostart":"",//自动播放（留空则   不启用自动播放）
                                    "streamer":"start"
                                });

                            </script>

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
