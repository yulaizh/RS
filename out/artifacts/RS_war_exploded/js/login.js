//设置快捷键
document.onkeydown = function (event) {
    if(event.keyCode ==13)
    {
        $('.button').click();
    }
}


$('.account_input').on('blur',function () {
    if ($('.button').val() == '注册') {     // 注册时，失去焦点就验证账号。
        $.ajax({
            type:"POST",
            url:"AjaxLoginServlet",
            data:{
                account:$('.account_input').val()
            },
            dataType:"text",
            success:function (retu) {
                console.log(retu);
                if (retu == "false"){
                    $('#output1').html('你要注册的账户已被注册');
                }else if(retu == "true"){
                    $('#output1').html('允许注册');
                }
            },
            error:function (error) {
                console.log("发生错误"+error);
            }
        })
    }
})


$('.button').on('click',function () {
    $('#output1').html("");
    $('#output2').html("");
    $('#output3').html("");
    if ($('.button').val() == '登录'){      // 如果是登录就直接验证
        var isnull = ($('.account_input').val() != "")&&($('.password_input').val() != "");
        if ($('.account_input').val() == ""){
            $('#output1').html("请输入账号");
        }
        if ($('.password_input').val() == ""){
            $('#output2').html("请输入密码");
        }
        if (isnull){
            $.ajax({
                type:"POST",
                url:"LogAndSignServlet",
                data:{
                    account:$('.account_input').val(),
                    password:$('.password_input').val()
                },
                dataType:"text",
                success:function (retu) {
                    if (retu == "true"){
                        console.log("登录成功");
                        window.location = "index.html";
                    }else if(retu == "false"){
                        $('#output2').html('账户或密码错误');
                    }
                },
                error:function (error) {
                    console.log("发生错误"+error);
                }
            })
        }
    }
    if ($('.button').val() == '注册'){  //还存在缺陷
        var isnull = ($('.account_input').val() != "")&&($('.password_input').val() != "")&&($('.re_password_input').val() != "");
        if ($('.account_input').val() == ""){
            $('#output1').html("请输入账号");
        }
        if ($('.password_input').val() == ""){
            $('#output2').html("请输入密码");
        }
        if ($('.re_password_input').val() == ""){
            $('#output3').html("请再次输入密码");
        }
        if (( $('.password_input').val() != $('.re_password_input').val())) {
            $('#output3').html("两次输入的密码不一致");
        }
        if ($('.password_input').val().length < 6){
            $('#output2').html("密码的长度应大于等于6");
        }
        if (( $('.password_input').val() == $('.re_password_input').val())&&($('.password_input').val().length >= 6)) {
            if (isnull){
                $.ajax({
                    type:"GET",
                    url:"LogAndSignServlet",
                    data:{
                        account:$('.account_input').val(),
                        password:$('.password_input').val()
                    },
                    dataType:"text",
                    success:function (retu) {
                        if (retu){
                            alert("注册成功");
                        }else{
                            $('#output3').html("注册失败，请更换账号重试");
                        }
                    },
                    error:function (error) {
                        console.log("发生错误"+error);
                    }
                })
            }
        }else {
            $('#output3').html('两次输入的密码不一致');
        }
    }
})



$('.point_box > button').on('click',function () {
    $('#output1').html("");
    $('#output2').html("");
    $('#output3').html("");
    $('.account_input').val("");
    $('.password_input').val("");
    $('.re_password_input').val("");
    if ($(this).text() == "注册"){
        $('.password').after(
            '<div class="re_password">\n' +
            '   <input class="re_password_input" placeholder="重复输入密码" type="password"><output id="output3"></output>\n' +
            '</div>')
        $('.three').html('');
        $('.button').attr('value','注册');
        $('.point_box > span').html('已有账号？点击');
        $('.point_box > button').html('登录')
    } else if ($(this).text() == "登录"){
        if ($('.re_password')){
            $('.re_password').html('');
        }
        $('.three').html(
            '<button class="three_box">\n' +
            '     <img src="pic/1.png">\n' +
            '     <span>账号</span>\n' +
            '</button>\n' +
            '<button class="three_box">\n' +
            '     <img class="three2" src="pic/1.png">\n' +
            '     <span class="threeName2">QQ</span>\n' +
            '</button>\n' +
            '<button class="three_box">\n' +
            '     <img src="pic/1.png">\n' +
            '     <span>微信</span>\n' +
            '</button>');
        $('.button').attr('value','登录');
        $('.point_box > span').html('还没注册？点击');
        $('.point_box > button').html('注册')
    }
})