var islike = false;
var ishate = false;

$(document).ready(function () {

    //文章的所有信息加载
    $.ajax({
        type:"post",
        url:"Article_LoadServlet",
        data:{
            id:getUrlParam('id')
        },
        dataType:"json",
        success:function (result) {
            $('.artHead > h1').html(result.title);
            $('.artMain').html(result.content);
            $('.artInfo > span').eq(0).html(result.origin);
            $('.artInfo > span').eq(1).html(new Date(result.crawl_time).toLocaleString());
            $('.artInfo > span').eq(2).html("阅读数"+result.readss);
            $('.artInfo > span').eq(3).html("喜欢数"+result.likes);
            $('.artInfo > span').eq(4).html("不喜欢数量"+result.dislikes);
            $('.artInfo > span').eq(5).html(result.tag);
            $('.artInfo > span').eq(6).html(result.keywords);
            if (result.likeornot == 0){
                $('.artPosNeg > input').eq(1).css("color","red");
                ishate = true;
            }else if (result.likeornot == 2){
                $('.artPosNeg > input').eq(0).css("color","red");
                islike = true;
            }
            $.each(result.list,function (index,item) {
                $('.comArea').append('' +
                    '<div class="comMain">' +
                    '   <div class="comLeft">' +
                    '       <img src="pic/touxiang.jpg" alt="">' +
                    '   </div>' +
                    '   <div class="comRight">' +
                    '       <div class="comUserName">' +
                    '           <span>'+ item.user_id +'</span>' +
                    '       </div>' +
                    '       <div class="comContent">' +
                    '           <p>'+ item.comment_content +'</p>' +
                    '       </div>' +
                    '       <div class="comRecom">'+
                    '           <a href="#">回复</a>'+
                    '       </div>'+
                    '   </div>' +
                    '</div>');
            })
        },
        error:function (error) {
            console.log("发生错误"+error.status);
        }
    })


    //点赞点踩添加事件。
    $('.artPosNeg > input').each(function(index){
        $(this).on('click',function(){
            if ($(this).css("color") == "rgb(255, 255, 255)"){
                if (index == 0){
                    if (!islike&&ishate) {return false;}
                    islike = true;
                }else if (index == 1){
                    if (islike&&!ishate) {return false;}
                    ishate = true;
                }

                $(this).css("color","red");

                $.ajax({
                    type:"POST",
                    url:"LikeandDislikeServlet",
                    data:{
                        param:index,
                        id:getUrlParam('id')
                    },
                    success:function(){
                        console.log("更新成功"+index+"+1")
                    },
                    error:function (error) {
                        console.log("发生错误"+error.status);
                    },
                })
            }
            else if ($(this).css("color") == "rgb(255, 0, 0)"){
                if (index == 0){
                    islike = false;
                }else if (index == 1){
                    ishate = false;
                }
                
                $(this).css("color","#ffffff");

                $.ajax({
                    type:"GET",
                    url:"LikeandDislikeServlet",
                    data:{
                        param:index,
                        id:getUrlParam('id')
                    },
                    success:function(){
                        console.log("更新成功"+index+"-1")
                    },
                    error:function (error) {
                        console.log("发生错误"+error.status);
                    },
                })
            }
        })
    })
    


    //提交评论添加事件。
    $('.comEditButton > input').on('click',function () {
        console.log(getUrlParam("id")+$('.comEditText > input').val())
        $.ajax({
            type:"POST",
            url:"Article_commentServlet",
            data:{
                id:getUrlParam("id"),
                comment:$('.comEditText > input').val()
            },
            dataType:"text",
            success:function(){
                console.log("成功")
                location.reload();
            },
            error:function(error){
                console.log("发生错误"+error.status)
            }

        })
    })

    window.onscroll = function() {//滚动条滚动触发
        if ($(document).scrollTop() >= 350) {
            $('.hot_news').addClass('fixed');
        } else {
            $('.hot_news').removeClass('fixed');
        }
    }

})


function getUrlParam(name){
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r!=null) return unescape(r[2]);
    return null;
}

