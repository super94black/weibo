<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>



<%@ include file="include/head.jsp" %>
<body>
<%@ include file="include/header.jsp" %>


<br>


<div class="container-fluid">
    <div class="row-fluid">
        <div class="span12">

                <div class="control-group">

                    <div class="controls">
                        用户名:<input id="username" type="text" style="height: 30px"/>
                    </div>
                </div>
                <div class="control-group">
                    <br><br>
                    <div class="controls">
                       &nbsp;&nbsp;密码:<input id="password" type="password" style="height: 30px"/>
                    </div>
                </div>
                <br>
                <div class="control-group">
                    <div class="controls">
                       <button type="button" class="btn" onclick="lo()">登陆</button>

                    </div>
                </div>


        </div>
    </div>
</div>

<%@ include file="include/footer.jsp" %>

</body>
</html>
