package otherAPI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ReadURL {//6.10测试过 可直接用，接口未关闭。
    static InputStream is = null;
    static InputStreamReader isr = null;
    static BufferedReader br= null;
    static StringBuilder sb = new StringBuilder();
    static String Str = null;
    static JSONObject response = null;
    static String uri = "jdbc:mysql://localhost:3306/RS?useUnicode=true&characterEncoding=UTF-8";//(数据库名，不是连接名)
    static String user = "root";
    static String password = "123456";
    static Connection con = null;
    static PreparedStatement pst = null;

    public static void connection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection(uri, user, password);

    }

    public static void close() throws IOException,SQLException{
        is.close();
        isr.close();
        br.close();
        pst.close();
        con.close();
    }

    public static String URL2String(int i)throws IOException{
        URL url = new URL("http://www.textvalve.com/htdatasub/subscribe/articles/toPublish/v2?userId=82&size=100&rnd0.456121920803368&page="+i);
        System.out.println(url.toString());
        is = url.openStream();
        isr = new InputStreamReader(is,"UTF-8");
        br = new BufferedReader(isr);
        String str = null;
        while((str = br.readLine())!=null) {
            sb.append(str);
        }

        String big_Str = sb.toString();
        sb.setLength(0);
        return big_Str;
    }

    public static void read_json(String big_Str)throws IOException, SQLException{
        String a,b,c,d,e = null;
        JSONObject dataJson = JSONObject.fromObject(big_Str);
        response = dataJson.getJSONObject("data");
        a = response.getString("pageSize");
        b = response.getString("currentPage");
        d = response.getString("totalRecord");
        c = response.getString("totalPages");
        e = response.getString("list");
        //	System.out.println(e);
        JSONArray jsonArray = response.getJSONArray("list");
        JSONObject littledataJson = null;
        String f,g,h,i,j,k,l,m = null;
        for(int z=0;z<jsonArray.size();z++) {
            littledataJson = jsonArray.getJSONObject(z);
            a = littledataJson.getString("id");
            b = littledataJson.getString("title");
            c = littledataJson.getString("source_site");
            d = littledataJson.getString("content");
            e = "";
            f = littledataJson.getString("keywords");
            g = littledataJson.getString("description");
            h = "0";
            i = "0";
            j = "0";
            k = littledataJson.getString("source_url");
            l = littledataJson.getString("image_list");
            m = littledataJson.getString("crawl_time");



            String SQL = "insert into article (id,title,origin,content,tag,keywords,description,readss,likes,dislikes,source_url,image_list,crawl_time) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
            pst = con.prepareStatement(SQL);
            pst.setString(1, a);
            pst.setString(2, b);
            pst.setString(3, c);
            pst.setString(4, d);
            pst.setString(5, e);
            pst.setString(6, f);
            pst.setString(7, g);
            pst.setString(8, h);
            pst.setString(9, i);
            pst.setString(10, j);
            pst.setString(11, k);
            pst.setString(12, l);
            pst.setString(13,m);
            pst.execute();

        }
        System.out.println("存储完毕");
    }


    public static void main(String[] args) throws Exception {
        connection();
        for(int i=1;i<11;i++)
        {	Str = URL2String(i);
            read_json(Str);
        }
        close();
    }
}