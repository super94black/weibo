
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title></title>
    <script src="/js/jquery-3.3.1.js"></script>
    <style>
        *{ margin:0; padding:0}
        ul{ list-style:none}
        .wrap{ width:500px;font-size:14px; margin:50px auto}
        .countTxt em{ font-size: 16px; font-weight: bold; font-style: normal; padding: 0 5px}
        .countTxt .red{ color: red;}
        textarea{ padding:10px; display:block; width:480px; resize:none; height:100px; border:1px solid #ccc; font-size:14px}
        .commonBtn{ height:40px; width:100px; border:1px solid #ccc; margin:10px 0 0 380px}

    </style>
</head>
<body>
<div class="wrap">
    <form id="uploadform" method="post" enctype="multipart/form-data">
        <textarea name="content" id="content"></textarea>
        上传视频：<input type="file" id="file" name="file" />
        <br>
        <input type="hidden" id="uid" name="uid" value="${sessionScope.user.id}">
        上传图片：<input type="file" id="img" name="img"/>
        <button type="button" class="commonBtn" onclick="post()">发表说说</button>
    </form>


</div>






</body>
</html>


<script>
    function post() {

        var FileController = "/post";    // 接收上传文件的后台地址

        // FormData 对象
        var form = new FormData($( "#uploadform" )[0]);




        $.ajax({
            type: "POST",
            url: "/post",
            dataType: "json",//预期服务器返回的数据类型
            data: new FormData($( "#uploadform" )[0]),
            processData:false,
            contentType:false,
            success: function(data){
                console.log(data);
                if(data.status == '200'){
                    window.location = "http://localhost:8080/index";
                }else{
                    alert(data.msg);
                }
            },
            error:function(e){
                console.log(e);
            }
        });
    }


</script>

