package someAPI.Labeling;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;

public class topic {

    public static void main(String[] args) throws Exception {
        String url = "https://aip.baidubce.com/rpc/2.0/nlp/v1/topic";
        ArrayList<String> index = null;
        ArrayList<String> content = null;
        String SQl = "select id,title,description from article order by crawl_time desc limit 100";
        String container = null;
        BDdbop op = new BDdbop();
        Bean bean = null;

        op.Connection();
        bean = op.select(SQl);
        index = bean.getIndex();
        content = bean.getContent();
        for (int i = 0; i < index.size();i++){
            container = getResults(url,content.get(i).toString());
            String sql = "update article set tag = ? where id = '" + index.get(i)+"'";
            op.update(container,sql);
        }
        op.close();
    }

    public static String getResults(String url ,String params){
        JSONObject jsonObject = null;  //转换介质
        JSONObject Jsondata =  null;  //完整数据
        JSONArray jsonArray = null;
        String container = "";
        try{
            HttpUtil httpUtil = new HttpUtil();
            String result = httpUtil.post(url,AuthService.access_token,params);
            System.out.println(result);
            Jsondata = JSONObject.fromObject(result);
            Jsondata = Jsondata.getJSONObject("item");
            jsonArray = Jsondata.getJSONArray("lv1_tag_list");
            container = jsonArray.getJSONObject(0).getString("tag");
        }catch (Exception e) {e.printStackTrace(); }

        System.out.println(container);

        return container;
    }

}