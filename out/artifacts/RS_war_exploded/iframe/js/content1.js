var canRevise = false;
var sexIndex = 2;

$(document).ready(function () {

    for (var i=1990;i<=2018;i++) {
        $('#year').append("<option value="+i+">"+i+"</option>");
    }
    for (var i=1;i<=12;i++) {
        if (i == 1){
            $('#month').append("<option selected value=\"" + 0+i + "\">"+ i +"</option>");
        }else if ( i < 10) {
            $('#month').append("<option value=\"" + 0+i + "\">" + i + "</option>");
        }else {
            $('#month').append("<option value=\"" + i + "\">" + i + "</option>");
        }
    }
    for (var i=1;i<=31;i++) {
        if (i == 1) {
            $('#day').append("<option selected value=\"" + 0+i + "\">" + i + "</option>");
        }else if( i < 10 ){
            $('#day').append("<option value=\"" +0+ i + "\">" + i + "</option>");
        }else {
            $('#day').append("<option value=\"" + i + "\">" + i + "</option>");
        }
    }

    $.ajax({
        type:"get",
        url:"/User_infoServlet",
        dataType:"json",
        async:false,
        success:function (data) {
            sexIndex = data.sexIndex;
            change(sexIndex);
            $('#year').val(data.year);
            $('#month').val(data.month);
            $('#day').val(data.day);
            // $('#province').val(data.province);
            addressInit('province', 'city', 'sector',data.province,data.city,data.sector);
            $('.text_input > input').val(data.area);
        },
        error:function (error) {
            console.log("发生错误"+error.status)
        }
    })

    $('#cancel').on('click',function () {
        if (canRevise){
            canRevise = false;
            $('#cancel').css('display','none');
        }
    })

    $('#revise').on('click',function () {

        if (!canRevise){
            canRevise = true;
            $('#cancel').css('display','inline-block');
        }
    })

    $('#confirm').on('click',function () {
        if (canRevise){
            canRevise = false;

            $.ajax({
                type:"post",
                url:"/User_infoServlet",
                data:{
                    sexIndex:sexIndex,
                    year:$('#year').val(),
                    month:$('#month').val(),
                    day:$('#day').val(),
                    province:$('#province').val(),
                    city:$('#city').val(),
                    sector:$('#sector').val(),
                    area:$('.text_input > input').val()
                },
                success:function () {
                    console.log("保存成功")
                },
                error:function (error) {
                    console.log("发生错误"+error.status)
                }
            })
            location.reload();
        }
    })

    $('.sex_input > a').each(function (index) {
        $(this).on('click',function () {
            $('.sex_input > a').removeClass("checked");
            $(this).addClass("checked");
            sexIndex = index;
        })
    })
})

function change(sexIndex) {
    $('.sex_input > a').removeClass("checked");
    $('.sex_input > a').eq(sexIndex).addClass("checked");
}