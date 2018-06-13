Vue.component('vue-li',{
    props: ['title','tag','timeAgo','image1'],
    methods:{
        flow:function () {
            $('.content_ul').append();
        }
            

    },
    template:
        `
    <li>
        <div class="box">
            <div class="pic_box">
                <a href="#">
                    <img class="pic" :src="image1">
                </a>
            </div>
            <div class="content_box">
                <div class="title_box">
                    <a class="title" href="">{{ title }}</a>
                </div>
                <div class="little_things_box">
                    <a class="little_tag">{{ tag }}·</a>
                    <span class="little_font_of_time">{{ timeAgo }}</span>
                </div>
            </div>
        </div>
    </li>
    `

})




var example = new Vue({
    el:'#vue',
    data: {
        projects: [
            {title: '美国中央情报局局长彭佩奥已与朝鲜领导人金正恩会面', tag: '军事', timeAgo: '一小时前', image1: 'pic/1.jpg'},
            {title: '美国中央情报局局长彭佩奥已与朝鲜领导人金正恩会面', tag: '军事', timeAgo: '一小时前', image1: 'pic/1.jpg'},
            {title: '美国中央情报局局长彭佩奥已与朝鲜领导人金正恩会面', tag: '军事', timeAgo: '一小时前', image1: 'pic/1.jpg'},
            {title: '美国中央情报局局长彭佩奥已与朝鲜领导人金正恩会面', tag: '军事', timeAgo: '一小时前', image1: 'pic/1.jpg'},
            {title: '美国中央情报局局长彭佩奥已与朝鲜领导人金正恩会面', tag: '军事', timeAgo: '一小时前', image1: 'pic/1.jpg'}
        ]
    }
})






// <li>
    //     <div class="box">
    //         <div class="pic_box">
    //             <a href="#">
    //                 <img class="pic" src="pic/1.jpg">
    //             </a>
    //         </div>
    //         <div class="content_box">
    //             <div class="title_box">
    //                 <a class="title" href="">美国中央情报局局长彭佩奥已与朝鲜领导人金正恩会面</a>
    //             </div>
    //             <div class="little_things_box">
    //                 <a class="little_tag">军事·</a> <span class="little_font_of_time">一小时前</span>
    //             </div>
    //         </div>
    //     </div>
    // </li>