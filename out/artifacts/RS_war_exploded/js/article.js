var islike = false;
var ishate = false;

$(document).ready(function () {
    //增加 点赞样式加载，，以及评论的加载
    $.ajax({
        type:"post",
        url:"Article_infoServlet",
        data:{
            id:getUrlParam('id')
        },
        dateType:"json",
        success:function (data) {
            $('.artHead > h1').html(data.title);
            $('.artMain').html(data.content);
            $('.artInfo > span').eq(0).html(data.origin);
            $('.artInfo > span').eq(1).html(new Date(data.crawl_time).toLocaleString());
            $('.artInfo > span').eq(2).html("阅读数"+data.readss);
            $('.artInfo > span').eq(3).html("喜欢数"+data.likes);
            $('.artInfo > span').eq(4).html("不喜欢数量"+data.dislikes);
            $('.artInfo > span').eq(5).html(data.tag);
            $('.artInfo > span').eq(6).html(data.keywords);
            if (data.likeornot == 0){
                $('.artPosNeg > input').eq(1).css("color","red");
                ishate = true;
            }else if (data.likeornot == 2){
                $('.artPosNeg > input').eq(0).css("color","red");
                islike = true;
            }
        },
        error:function (error) {
            console.log("发生错误"+error.status);
        }
    })

    $('.artPosNeg > input').each(function(index){
        $(this).on('click',function(){
            if ($(this).css("color") == "rgb(255, 255, 255)"){
                if (index == 0){
                    if (!islike&&ishate) {
                        return false;
                    }
                    islike = true;

                }else if (index == 1){
                    if (islike&&!ishate) {
                        return false;
                    }
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

})

function getUrlParam(name){
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r!=null) return unescape(r[2]);
    return null;
}

