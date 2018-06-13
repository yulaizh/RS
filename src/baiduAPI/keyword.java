package baiduAPI;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;

public class keyword {


    public static void main(String[] args) throws Exception {


        String url = "https://aip.baidubce.com/rpc/2.0/nlp/v1/keyword";

        ArrayList<String> index = null;
        ArrayList<String> content = null;
        String SQl = "select id,title,description from article";
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
            jsonArray = Jsondata.getJSONArray("items");
            for (int i=0;i < jsonArray.size();i++){
                jsonObject = jsonArray.getJSONObject(i);
                container += jsonObject.getString("tag")+"|";
            }
            container = container.substring(0,container.length()-1);
        }catch (Exception e) {e.printStackTrace(); }

        System.out.println(container);

        return container;
    }






}