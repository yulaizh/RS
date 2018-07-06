Vue.component('vue-li',{
    props: ['title','href','image'],
    template:
        '<li>'+
            '<a :href="href">'+
                '<div class="hot_img_box">'+
                    '<img :src="image" class="hot_img">'+
                '</div>'+
                '<div class="hot_title_box">'+
                    '<p class="hot_title">{{ title }}</p>'+
                '</div>'+
            '</a>'+
        '</li><hr/>'
})
var con;
$(document).ready(function () {
    $.ajax({
        type:"post",
        url:"HotNewsServlet",
        dataTye:"json",
        async:false,
        success:function (result) {
            con = result;
            console.log("con"+con)
        },
        error:function (error) {
            console.log("发生错误"+error.status)
        }
    })
    console.log(con);
    var example = new Vue({
        el:'#vue',
        data: {
            projects: con
        }
    })

})








