function lo() {
    var regUserName = /^[a-zA-Z0-9_]{5,11}$/;
    var username = $("#username").val();
    var password = $("#password").val();
    if(!regUserName.test(username)  || password.length < 8 || password.length > 16){
        alert("用户名或者密码不正确");
        return null;
    }

    $.ajax({
        type: "POST",
        url: "/login",
        contentType: 'application/x-www-form-urlencoded;charset=utf-8',
        data: {
                "username":username,
                "password":password
        },
        dataType: "json",
        success: function(data){
            console.log(data);
            if(data.status == '200'){
                window.location = data.data;
            }else{
                alert(data.msg);
            }
        },
        error:function(e){
            console.log(e);
        }
    });

}
