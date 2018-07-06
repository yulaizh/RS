$(document).ready(function () {
    $.ajax({
        type:"post",
        url:"/GetRecordServlet",
        dataType:"json",
        async:false,
        success:function (result) {
            var chartData = [[result.synthesize,"综合"], [result.society,"社会"], [result.entertainment,"娱乐"], [result.economics,"财经"],[result.technology,"科技"],[result.civilization,"文化"],[result.education,"教育"],[result.current_events,"时事"],[result.international,"国际"],[result.tourism,"旅游"],[result.physical,"体育"],[result.car,"汽车"],[result.fashion,"时尚"],[result.other,"其他"]]
            start(chartData);
        },
        error:function (error) {
            console.log("发生错误"+error.status)
        }
    })
})

function start(data) {
    var color = ["#2dc6c8","#b6a2dd","#5ab1ee","#d7797f","#2dc5c8","#e6a2dd","#5rb1ee","#d7597f","#2dc6b8","#b6a5dd","#5ab7ee","#d7727f","#d4727f","#d7787f"]
    var num = color.length;
    var canvas = document.getElementById('canvas');
    var ctx;
    if(canvas && canvas.getContext){
        ctx = canvas.getContext("2d");
    }
    var radius,ox,oy;//半径 圆心
    var ratioArr =[];
    var ctr = 1;
    var numctr = 40;


    init();
    function init() {
        canvas.width = 1000;
        canvas.height = 500;
        canvas.style.height = canvas.height + "px";
        canvas.style.width = canvas.width + "px";

        radius = canvas.height/3;
        ox = canvas.width/2 + 50;
        oy = canvas.height/2;

        var totleNb = 0;
        for (var i = 0; i < data.length; i++){
            totleNb += data[i][0];
        }
        for (var i = 0; i < data.length; i++){
            ratioArr.push( data[i][0] / totleNb );
        }
    }

    drawSm();
    function drawSm() {
        ctx.textAlign="left";
        var tWidth = 40;  //小框框的长宽
        var tHeight = 15;
        var posX = 20;    //上左边框的距离
        var posY = 10;
        var vX = 15;     //模块之间的距离
        var vY = 30;
        var textX = tWidth  + posX + vX ;  //文本的长宽
        var textY = tHeight + posY ;
        for (var i = 0;i < num ;i++){
            ctx.fillStyle = color[i];
            ctx.fillRect(posX,posY + vY * i,tWidth,tHeight);
            ctx.moveTo(posX, posY + vY * i);
            ctx.font = 'normal 14px 微软雅黑';
            var percent = data[i][1] + "：" + parseInt(100 * ratioArr[i]) + "%";
            ctx.fillText(percent, textX, textY + vY * i - 2);
        }
    }

    pieDraw();
    function pieDraw(){
        var startAngle = 0;   //扇形开始旋转的位置 顺时针
        var endAngle = 0;
        var lineStartAngle = -startAngle;
        var line = 40;
        var textPadding = 10;
        var textMoveDis = 200;

        for (var i = 0; i < num ; i++){
            if (data[i][0] < 0){
                continue;
            }
            ctx.fillStyle = ctx.strokeStyle = color[i];
            ctx.lineWidth = 1;
            var step = ratioArr[i] * Math.PI * 2;      //旋转弧度
            var lineAngle = lineStartAngle + step/2;   //计算线的角度
            lineStartAngle += step;//结束弧度
            ctx.beginPath();
            var x0 = ox + radius * Math.cos(lineAngle),//圆弧上线与圆相交点的x坐标
                y0 = oy + radius * Math.sin(lineAngle),//圆弧上线与圆相交点的y坐标
                x1 = ox + (radius + line) * Math.cos(lineAngle),//圆弧上线与圆相交点的x坐标
                y1 = oy + (radius + line) * Math.sin(lineAngle),//圆弧上线与圆相交点的y坐标
                x2 = x1, //转折点的x坐标
                y2 = y1,
                linePadding = ctx.measureText(data[i][1]).width + 10; //获取文本长度来确定折线的长度
            ctx.moveTo(x0,y0);

            //对x1/y1进行处理，来实现折线的运动
            var yMove = y0 + ( y1 - y0) * ctr / numctr;

            ctx.lineTo(x1,yMove);
            if(x1 <= x0){
                x2 -= line;
                ctx.textAlign="right";
                ctx.lineTo(x2 - linePadding,yMove);
                ctx.fillText(data[i][1], x2 - textPadding - textMoveDis * (numctr - ctr) / numctr, y2 - textPadding);
            }else{
                x2 += line;
                ctx.textAlign="left";
                ctx.lineTo(x2 + linePadding,yMove);
                ctx.fillText(data[i][1], x2 + textPadding + textMoveDis * (numctr - ctr) / numctr, y2 - textPadding);
            }
            ctx.stroke();
        }
        ctx.save();

        ctx.translate(ox, oy);
        ctx.beginPath();
        ctx.arc(0, 0 ,(radius+20) * ctr / numctr, 0, Math.PI*2, false); //外圈圆   x,y,r,开始角度,结束角度,是否顺时针
        ctx.stroke();

        for (var j = 0; j < num; j++){
            endAngle = endAngle + ratioArr[j]* ctr/numctr * Math.PI * 2; //结束弧度
            ctx.beginPath();
            ctx.moveTo(0,0); //移动到到圆心
            ctx.arc(0, 0, radius * ctr/numctr, startAngle, endAngle, false); //绘制圆弧
            ctx.fillStyle = color[j];
            ctx.closePath();
            ctx.fill();
            startAngle = endAngle; //设置起始弧度
            if( j == data.length-1 ){
                startAngle = endAngle = 90*Math.PI/180; //起始弧度 结束弧度
            }
        }
        ctx.restore();

        if(ctr < numctr){  //动态效果
            ctr++;
            setTimeout(function(){
                ctx.clearRect(0,0,canvas.width,canvas.height);
                drawSm();
                pieDraw();
            }, 0);
        }
    }

}