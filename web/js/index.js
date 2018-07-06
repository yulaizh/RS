
//轮播图效果
var a = 0;
setInterval(function () {

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
        a = $(this).index();
        change_class(a);
    },
    function () {
    });

function change_class(i) {
    console.log(11);
    $('.link>img').removeClass("image");
    $('.link>img').addClass("image_hidden");
    $('.link>img').eq(i).addClass("image");
    $('.link>img').eq(i).removeClass("image_hidden");
}





// $(document).ready(function () {
//     $.ajax({
//         type:"post",
//         url:"",
//         dataType:"json",
//         success:function () {
//
//         },
//         error:function (error) {
//             console.log("发生错误"+error);
//         }
//     })
//
// })




//页面初始化
$(document).ready(function() {
    $.ajax({
        type: "POST",
        url: "IndexLoadingServlet",
        data: { param:'推荐' },
        dataType: "json",
        success: function (result) {
            $(".content_ul").html("");
            $.each(result,function (index, item) {
                var arr = item.image_list.split(',')
                $(".content_ul").append(
                    '<li>\n' +
                    '  <div class="box">\n' +
                    '      <div class="pic_box">\n' +
                    "          <a href=\"article.html?id="+ item.id + "\">" +
                    '              <img class="pic" src='+arr[0]+'>' +
                    '          </a>'+
                    '      </div>\n' +
                    '      <div class="content_box">\n' +
                    '          <div class="title_box">\n' +
                    "             <a class='title' target=\"_blank\" href=\"article.html?id="+item.id+"\">"+item.title+"</a>\n" +
                    '          </div>\n' +
                    '          <div class="little_things_box">\n' +
                    '              <span class="little_tag">'+ item.tag +'</span>\n' +
                    '              <span class="little_font">'+ item.origin +'</span>'+
                    '          </div>\n' +
                    '       </div>\n' +
                    '   </div>\n' +
                    '</li>');
            });
        },
        error: function (jqXHR) { console.log("发生错误：" + jqXHR.status);},
    })
})



var currentIndex = 0;
//切换标签
//给按钮设定事件
$(document).ready(function() {
    $('.left_list').each(function (index) {
        $(this).on('click', function () {
            $('.left_ul > li > a').removeClass("selected");
            $('.left_ul > li > a').eq(index).addClass("selected")

            currentIndex = index;

            $.ajax({
                type: "POST",
                url: "IndexLoadingServlet",
                data: {
                    param: $('.left_list > span').eq(index).text()
                },
                dataType: "json",
                success: function (result) {
                    $(".content_ul").html("");
                    $.each(result,function (index, item) {
                        var arr = item.image_list.split(',')
                        $(".content_ul").append(
                            '<li>\n' +
                            '  <div class="box">\n' +
                            '      <div class="pic_box">\n' +
                            "          <a href=\"article.html?id="+ item.id + "\">" +
                            '              <img class="pic" src='+arr[0]+'>' +
                            '          </a>'+
                            '      </div>\n' +
                            '      <div class="content_box">\n' +
                            '          <div class="title_box">\n' +
                            "             <a class='title' target=\"_blank\" href=\"article.html?id="+item.id+"\">"+item.title+"</a>\n" +
                            '          </div>\n' +
                            '          <div class="little_things_box">\n' +
                            '              <span class="little_tag">'+ item.tag +'</span>\n' +
                            '              <span class="little_font">'+ item.origin +'</span>'+
                            '          </div>\n' +
                            '       </div>\n' +
                            '   </div>\n' +
                            '</li>');
                    });
                },
                error: function (jqXHR) {console.log("发生错误：" + jqXHR.status);},
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

function waterfall(){
    currentId = $('.content_ul > li:last-child .title').attr("href").split('=')[1]; //
    $.ajax({
        type: "POST",
        url: "WaterfallServlet",
        data: {
            param : $('.left_list > span').eq(currentIndex).text(),
            counter: currentId
        },
        dataType: "json",
        success: function (result) {
            console.log("currentIndex"+currentIndex);
            $.each(result,function (index, item) {
                var arr = item.image_list.split(',');
                $(".content_ul").append(
                    '<li>\n' +
                    '  <div class="box">\n' +
                    '      <div class="pic_box">\n' +
                    "          <a href=\"article.html?id="+ item.id + "\">" +
                    '              <img class="pic" src='+arr[0]+'>' +
                    '          </a>'+
                    '      </div>\n' +
                    '      <div class="content_box">\n' +
                    '          <div class="title_box">\n' +
                    "             <a class='title' target=\"_blank\" href=\"article.html?id="+item.id+"\">"+item.title+"</a>\n" +
                    '          </div>\n' +
                    '          <div class="little_things_box">\n' +
                    '              <span class="little_tag">'+ item.tag +'</span>\n' +
                    '              <span class="little_font">'+ item.origin +'</span>'+
                    '          </div>\n' +
                    '       </div>\n' +
                    '   </div>\n' +
                    '</li>');
            });
        },
        error: function (jqXHR) {
            console.log("发生错误：" + jqXHR.status);
        },
    })
}