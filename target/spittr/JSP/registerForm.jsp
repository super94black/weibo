<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page session="false" %>
<html>
<%@ include file="include/head.jsp" %>
<body>
<%@ include file="include/header.jsp" %>


<br>
<!-- 表单没有设置action属性，会提交到当前路径下；
modelAttribute用于绑定模型对象（对象在Controller中创建）;
 enctype="multipart/form-data" 以multipart数据的形式提交表单，而不是以表单数据的形式进行提交-->
<sf:form cssClass="form-horizontal" id="register" method="post"
         modelAttribute="user" enctype="multipart/form-data">
    <div class="form-group">
        <label class="col-md-1 col-md-offset-4 control-label">用户名：</label>
        <!-- sf:input渲染成的html的type=text ,path属性值会渲染成value值 -->
        <div class="col-md-2">
            <sf:input cssClass="form-control" path="username"/>
            <sf:errors path="username"/>
        </div>
        <div class="col-md-2" id="usernameError"></div>
    </div>
    <div class="form-group">
        <label class="col-md-1 col-md-offset-4 control-label">密码：</label>
        <div class="col-md-2">
            <sf:password cssClass="form-control" path="password"/>
            <sf:errors path="password"/>
        </div>
    </div>

    <div class="form-group">
        <label class="col-md-1 col-md-offset-4 control-label">昵称：</label>
        <div class="col-md-2">
            <sf:input cssClass="form-control" path="nickname"/>
            <sf:errors path="nickname"/>
        </div>
        <div class="col-md-2" id="nicknameError"></div>
    </div>

    <div class="form-group">
        <label class="col-md-1 col-md-offset-4 control-label">上传头像：</label>
        <div class="col-md-1">
            <input type="file" name="icon" accept="image/jpeg,image/png,image/gif"/>
        </div>
    </div>
    <div class="form-group">
        <div class="col-md-2 col-md-offset-5">
            <input type="button" class="btn btn-primary btn-block" value="注册" onclick="register()"/>
        </div>
    </div>
</sf:form>

<%@ include file="include/footer.jsp" %>
</body>

<script type="text/javascript">
    $(function () {
        //键盘松开时触发监听事件，有实时反馈的体验（change体验不好，DOM失去焦点时才触发）
        $(":input[name='username']").keyup(function () {
            var val = $(this).val();
            val = $.trim(val);
            if(val != ""){
                var url = "${pageContext.request.contextPath}/validateUsername";
                var args = {"username": val, "time": new Date()};
                $.post(url, args, function (data) {

                    $("#usernameError").html(data);
                });
            }
        });

        $(":input[name='nickname']").keyup(function () {
            var val = $(this).val();
            val = $.trim(val);
            if(val != ""){
                var url = "${pageContext.request.contextPath}/validateNickname";
                var args = {"nickname": val, "time": new Date()};
                $.post(url, args, function (data) {
                    $("#nicknameError").html(data);
                });
            }
        });
    })



    function register() {
        var formData = new FormData($( "#register" )[0]);

        var regNickName = /^[\u4e00-\u9fa5a-a-zA-Z0-9_]{2,12}$/;
        var regUsername  = /^[a-zA-Z0-9_]{5,11}$/;
        if(!regUsername.test(formData.get('username'))){
            alert("用户名必须为由大小写字母、数字或下划线_组成的5-11个字符");
            return null;
        }
        if(formData.get('password').length < 8 || formData.get('password').length >16){
            alert("密码长度为8-16位");
            return null;
        }

        if(!regNickName.test(formData.get('nickname'))){
            alert("昵称必须为2-12个字符，且不包含特殊字符");
            return null;
        }




        $.ajax({
            url: '/register' ,
            type: 'POST',
            data: formData,
            async: true,
            cache: false,
            contentType: false,
            processData: false,
            success: function (returndata) {

                if(returndata.status != '200'){
                    alert(returndata.msg);
                }else{
                    window.location.href = "http://localhost:8080/user/" + formData.get('username');
                }

            },
            error: function (returndata) {
                alert("err")
            }
        });
    }

</script>

</html>