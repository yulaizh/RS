$(document).ready(function () {
    console.log("indexajax");
    $.ajax({
        type:"POST",
        url:"UserAjaxServlet",
        dateType:"json",
        success:function (date) {
            console.log(date.name)
            $('.name').html(date.name);
            if ((date.url != "null")) {
                $('.user_img').attr("src",date.url);
            }else {
                $('.user_img').attr("src","../pic/touxiang.jpg");
            }
            $('.name').attr("href","self.html");
            $('.user_down').html(
                '<ul class="user_ul">\n' +
                '   <a href="#">\n' +
                '      <li class="fans">\n' +
                '          <p>0</p>\n' +
                '          <p>粉丝</p>\n' +
                '      </li>\n' +
                '   </a>\n' +
                '   <li class="wire"></li>\n' +
                '   <a href="#">\n' +
                '      <li class="fans">\n' +
                '        <p>0</p>\n' +
                '        <p>关注</p>\n' +
                '      </li>\n' +
                '   </a>\n' +
                '</ul>')
        },
        error:function (error) {
            console.log("发生错误"+error);
        }
    })
})