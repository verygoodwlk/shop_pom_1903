$(function(){
    //发送ajax请求认证当前是否登录
    $.ajax({
        url:"http://localhost:8084/sso/checkLogin",
        success: function(data){
            var html = "";
            if(data != null){
                html = data.nickname + " 您好，欢迎来到<b><a>ShopCZ商城</a></b> <a href='http://localhost:8084/sso/logout'>注销</a>";
            } else {
                html = " [<a href=\"http://localhost:8084/sso/tologin\">登录</a>][<a href=\"http://localhost:8084/sso/toregister\">注册</a>]";
            }
            $("#pid").html(html);
        },
        dataType: "jsonp",
        jsonpCallback: "method"
    });
});