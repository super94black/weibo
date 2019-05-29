function check(postId) {

    $.ajax({
        type: "POST",
        url: "/admin/check",
        contentType: 'application/x-www-form-urlencoded;charset=utf-8',
        data: {"pid":postId},
        dataType: "json",
        success: function(data){
            console.log(data);
            if(data.status == '200'){
                alert("审核通过")
                window.location = "http://localhost:8080/check";
            }else{
                alert(data.msg);
            }
        },
        error:function(e){
            console.log(e);
        }
    });


}