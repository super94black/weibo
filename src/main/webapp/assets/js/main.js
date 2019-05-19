var postId;
var userId;
var delId;
var zanCount;
var isZan;
window.onload = function () {
    var nickName = $("#nickName").val();
    var list = document.getElementById('list');
    var boxs = list.children;
    var timer;


    //格式化日期
    function formateDate(date) {
        var y = date.getFullYear();
        var m = date.getMonth() + 1;
        var d = date.getDate();
        var h = date.getHours();
        var mi = date.getMinutes();
        m = m > 9 ? m : '0' + m;
        return y + '-' + m + '-' + d + ' ' + h + ':' + mi;
    }

    //删除节点
    function removeNode(node) {
        node.parentNode.removeChild(node);


    }

    /**
     * 赞分享
     * @param box 每个分享的div容器
     * @param el 点击的元素
     */
    function praiseBox(box, el) {

        var count = 0;
        var txt = el.innerHTML;
        var praisesTotal = box.getElementsByClassName('praises-total')[0];
        var oldTotal = parseInt(praisesTotal.getAttribute('total'));

        if (txt == '取消赞') {
            alert(zanCount + " -----" + isZan +"------" + userId + "---" + postId);
            count = parseInt(zanCount) - 1;
            praisesTotal.innerHTML = count + '个人觉得很赞';
            el.innerHTML = '赞';


            $.ajax({
                type: "POST",
                url: "/disZan",
                contentType: 'application/x-www-form-urlencoded;charset=utf-8',
                data: {"postId":postId, "uid":userId},
                dataType: "json",
                success: function(data){
                    console.log(data);
                },
                error:function(e){
                    console.log(e);
                }
            });


        }
        else {
            count = parseInt(zanCount) + 1;
            praisesTotal.innerHTML = count + '个人觉得很赞';
            el.innerHTML = '取消赞';


            $.ajax({
                type: "POST",
                url: "/zan",
                contentType: 'application/x-www-form-urlencoded;charset=utf-8',
                data: {"postId":postId, "uid":userId},
                dataType: "json",
                success: function(data){
                    console.log(data);
                },
                error:function(e){
                    console.log(e);
                }
            });

        }
        praisesTotal.style.display = (count == 0) ? 'none' : 'block';
    }

    /**
     * 发评论
     * @param box 每个分享的div容器
     * @param el 点击的元素
     */
    function reply(box, el) {

        if(userId == null || '' == userId || '0' == userId){
            alert("请您先登陆");
            return;
        }

        var userImg = $("#userImg").val();
        var commentList = box.getElementsByClassName('comment-list')[0];
        var textarea = box.getElementsByClassName('comment')[0];
        var commentBox = document.createElement('div');
        commentBox.className = 'comment-box clearfix';
        commentBox.setAttribute('user', 'self');
        commentBox.innerHTML =
            '<img class="myhead" src="' +userImg +  '" alt=""/>' +
                '<div class="comment-content">' +
                '<p class="comment-text"><span class="user">' + nickName + '：</span>' + textarea.value + '</p>' +
                '<p class="comment-time">' +
                formateDate(new Date()) +
                '</p>' +
                '</div>';

        alert(textarea.value + "---" + postId + "--" + userId);
        //向后台提交回复请求
        var pid = postId;
        var uid = userId;
        var content = textarea.value;
        var con = new Array();




        if((content.substr(0,2).search("回复")) != -1 && (content.substr(0,14).search(":")) != -1){
            var arr = content.split(":");
            for(i = 1 ; i < arr.length; i++){
                con.push(arr[i]);
            }
            var str = con.join("");
            content = str;
        }



        $.ajax({
            type: "POST",
            url: "/replay",
            contentType: 'application/x-www-form-urlencoded;charset=utf-8',
            data: {"pid":pid, "uid":uid,"content":content},
            dataType: "json",
            success: function(data){
                console.log(data);
            },
             error:function(e){
                 console.log(e);
             }
    });

        commentList.appendChild(commentBox);
        textarea.value = '';
        textarea.onblur();
    }

    /**
     * 赞回复
     * @param el 点击的元素
     */
    function praiseReply(el) {
        var myPraise = parseInt(el.getAttribute('my'));
        var oldTotal = parseInt(el.getAttribute('total'));
        var newTotal;
        if (myPraise == 0) {
            newTotal = oldTotal + 1;
            el.setAttribute('total', newTotal);
            el.setAttribute('my', 1);
            el.innerHTML = newTotal + ' 取消赞';
        }
        else {
            newTotal = oldTotal - 1;
            el.setAttribute('total', newTotal);
            el.setAttribute('my', 0);
            el.innerHTML = (newTotal == 0) ? '赞' : newTotal + ' 赞';
        }
        el.style.display = (newTotal == 0) ? '' : 'inline-block'
    }

    /**
     * 操作留言   评论留言
     * @param el 点击的元素
     */
    function operate(el) {
        var commentBox = el.parentNode.parentNode.parentNode;
        var box = commentBox.parentNode.parentNode.parentNode;
        var txt = el.innerHTML;
        var user = commentBox.getElementsByClassName('user')[0].innerHTML;
        var textarea = box.getElementsByClassName('comment')[0];
        if (txt == '回复') {
            textarea.focus();
            textarea.value = '回复' + user.split(":")[0].trim() + ": ";
            textarea.onkeyup();
        }
        else {
            alert(delId)
            removeNode(commentBox);
            $.ajax({
                type: "POST",
                url: "/post/del",
                contentType: 'application/x-www-form-urlencoded;charset=utf-8',
                data: {"id":delId},
                dataType: "json",
                success: function(data){
                    console.log(data);
                },
                error:function(e){
                    console.log(e);
                }
            });

        }
    }

    //把事件代理到每条分享div容器
    for (var i = 0; i < boxs.length; i++) {
        //点击
        boxs[i].onclick = function (e) {
            e = e || window.event;
            var el = e.srcElement;
            switch (el.className) {

                //关闭分享
                case 'close':
                    removeNode(el.parentNode);
                    break;

                //赞分享
                case 'praise':
                    praiseBox(el.parentNode.parentNode.parentNode, el);
                    break;

                //回复按钮蓝
                case 'btn':
                    reply(el.parentNode.parentNode.parentNode, el);
                    break;

                //回复按钮灰
                case 'btn btn-off':
                    clearTimeout(timer);
                    break;

                //赞留言
                case 'comment-praise':
                    praiseReply(el);
                    break;

                //操作留言
                case 'comment-operate':
                    operate(el);
                    break;

                //操作留言
                case 'comment-del':
                    operate(el);
                    break;
            }
        }

        //评论
        var textArea = boxs[i].getElementsByClassName('comment')[0];
        if(i == 0)
            continue
        //评论获取焦点
        textArea.onfocus = function () {
            this.parentNode.className = 'text-box text-box-on';
            this.value = this.value.trim() == '评论…' ? '' : this.value.trim();
            this.onkeyup();
        }

        //评论失去焦点
        textArea.onblur = function () {
            var me = this;
            var val = me.value.trim();

            if (val == '') {
                timer = setTimeout(function () {
                    me.value = '评论…';
                    me.parentNode.className = 'text-box';
                }, 200);
            }
        }

        //评论按键事件
        textArea.onkeyup = function () {
            var val = this.value.trim();
            var len = val.length;
            var els = this.parentNode.children;
            var btn = els[1];
            var word = els[2];

            if (len <=0 || len > 140) {
                btn.className = 'btn btn-off';
            }
            else {
                btn.className = 'btn';
            }
            word.innerHTML = len + '/140';
        }



    }

};

function replayOther(postid,uid) {
    postId = postid;
    userId = uid;


}
function deletePost(postId) {
    delId = postId;
}

function zan(count,is,uid,pid) {
    zanCount = count;
    isZan = is;
    postId = pid;
    userId = uid;
}




