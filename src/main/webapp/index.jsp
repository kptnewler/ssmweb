<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<meta http-equiv=“Content-Type” content=“text/html; charset=UTF-8″>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>主页</title>
    <script src="https://cdn.staticfile.org/jquery/1.10.2/jquery.min.js">
    </script>
    <script>
        // $(function () {
        //     $("#login-form").submit(function () {
        //         $.ajax({
        //             url:"/user/login.action",
        //             type: "POST",
        //             contentType: "application/json; charset=utf-8",
        //             data:JSON.stringify({"name":$("[name='name']").val(), "password":$("[name='password']").val(),
        //                 "date":$("[name='birthday']").val()}),
        //             async: true,
        //             success: function (data) {
        //                 alert(JSON.stringify(data));
        //             }
        //         });
        //         return false;
        //     });
        // });
    </script>
</head>
<body>
<form id="login-form" method="post" action="user/upload">
    <div>
        <label>
            <span>用户名：</span>
            <input type="text" name="name"/>
        </label>
    </div>

    <div>
        <label>
            <span>密码：</span>
            <input type="password" name="password"/>
        </label>
    </div>

    <div>
        <label>
            <span>
                生日：
            </span>
            <input type="text" name="birthday">
        </label>
    </div>

    <div>
        <label>
            <span>
                头像：
            </span>
            <input type="file" name="file">
        </label>
    </div>

    <div>
        <input type="submit"  id="login-submit" value="登录">
    </div>

</form>
</body>

</html>


