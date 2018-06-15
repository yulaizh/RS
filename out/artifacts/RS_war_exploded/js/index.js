alert(111);

//轮播图效果
setInterval(function () {
    var a = 0;
    $('.buttons>li').removeClass("checked");
    $('.buttons>li').eq(a).addClass("checked");

    a++;
    change_class(a);
    if (a >= 5){
        a = a % 5;
    }
},3000);

$('.buttons > li').hover(
    function () {
        var i = $(this).index();
        change_class(i);
    },
    function () {
    });

function change_class(i) {
    $('.link>img').removeClass("image");
    $('.link>img').addClass("image_hidden");
    $('.link>img').eq(i).addClass("image");
    $('.link>img').eq(i).removeClass("image_hidden");
}


//页面初始化
$(document).ready(function() {
    $.ajax({
        type: "POST",
        url: "IndexLoadingServlet",
        data: { param: 0 },
        dataType: "json",
        success: function (result) {
            $(".content_ul").html("");
            $.each(result.list,function (index, item) {
                var arr = item.image_list.split(',')
                $(".content_ul").append(
                    '<li>\n' +
                    '  <div class="box">\n' +
                    '      <div class="pic_box">\n' +
                    '          <img class="pic" src='+arr[0]+'>\n' +
                    '      </div>\n' +
                    '      <div class="content_box">\n' +
                    '          <div class="title_box">\n' +
                    "             <a class='title' href=\"article.html?id="+item.id+"\">"+item.title+"</a>\n" +
                    '          </div>\n' +
                    '          <div class="little_things_box">\n' +
                    '              <a class="little_tag">flagname</a>\n' +
                    '                 <span class="little_font_of_time">'+item.crawl_time+'</span>\n' +
                    '          </div>\n' +
                    '       </div>\n' +
                    '   </div>\n' +
                    '</li>');
            });
        },
        error: function (jqXHR) { alert("发生错误：" + jqXHR.status);},
    })
})



var currentIndex = 0;
//切换标签
//给按钮设定事件
$(document).ready(function() {
    $('.left_list').each(function (index) {
        $(this).on('click', function () {
            currentIndex = index;
            $.ajax({
                type: "POST",
                url: "IndexLoadingServlet",
                data: {
                    param: index
                },
                dataType: "json",
                success: function (result) {
                    $(".content_ul").html("");
                    $.each(result.list,function (index, item) {
                        var arr = item.image_list.split(',')
                        $(".content_ul").append(
                            '<li>\n' +
                            '  <div class="box">\n' +
                            '      <div class="pic_box">\n' +
                            '          <img class="pic" src='+arr[0]+'>\n' +
                            '      </div>\n' +
                            '      <div class="content_box">\n' +
                            '          <div class="title_box">\n' +
                            "             <a class='title' href=\"article.html?id=111"+item.id+"\">"+item.title+"</a>\n" +
                            '          </div>\n' +
                            '          <div class="little_things_box">\n' +
                            '              <a class="little_tag">flagname</a>\n' +
                            '                 <span class="little_font_of_time">'+ item.crawl_time +'</span>\n' +
                            '          </div>\n' +
                            '       </div>\n' +
                            '   </div>\n' +
                            '</li>');
                    });
                },
                error: function (jqXHR) {alert("发生错误：" + jqXHR.status);},
            })
        })
    })
})


//导航栏跟随与瀑布流
$( window ).on( "load", function(){
    window.onscroll = function(){//滚动条滚动触发
        if ($(document).scrollTop() >= 50){
            $('.leftNav').addClass('fixed');
        }else {
            $('.leftNav').removeClass('fixed');
        }
        if ($(document).scrollTop() >= 350){
            $('.hot_news').addClass('fixed');
        }else{
            $('.hot_news').removeClass('fixed');
        }

        if ($(document).scrollTop() >= $(document).height() - $(window).height()) {
            setTimeout(function () {
                waterfall();
            }, 50);
        }
    }
});

//瀑布流刷新
var currentTime = 0;
function waterfall(){
    currentTime = Number($('.content_ul > li:last-child span').text());
    console.log("currentTime"+currentTime);
    $.ajax({
        type: "POST",
        url: "AjaxLoadingwaterfallServlet",
        data: {
            param : currentIndex,
            num : 4,
            counter: currentTime
        },
        dataType: "json",
        success: function (result) {
            console.log("currentIndex"+currentIndex);
            $.each(result.list,function (index, item) {
                var arr = item.image_list.split(',');
                $(".content_ul").append(
                    '<li>\n' +
                    '  <div class="box">\n' +
                    '      <div class="pic_box">\n' +
                    '          <img class="pic" src='+arr[0]+'>\n' +
                    '      </div>\n' +
                    '      <div class="content_box">\n' +
                    '          <div class="title_box">\n' +
                    "             <a class='title' href=\"article.html?id="+item.id+"\">"+item.title+"</a>\n" +
                    '          </div>\n' +
                    '          <div class="little_things_box">\n' +
                    '              <a class="little_tag">flagname</a>\n' +
                    '                 <span class="little_font_of_time">'+ item.crawl_time +'</span>\n' +
                    '          </div>\n' +
                    '       </div>\n' +
                    '   </div>\n' +
                    '</li>');
            });
        },
        error: function (jqXHR) {
            alert("发生错误：" + jqXHR.status);
        },
    })
}