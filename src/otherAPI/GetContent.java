package otherAPI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import net.sf.json.JSONObject;

public class GetContent {
    static InputStream is = null;
    static InputStreamReader isr = null;
    static BufferedReader br= null;
    static StringBuilder sb = new StringBuilder();
    static String Str = null;
    static JSONObject dataJson = null;
    static JSONObject response = null;
    static String uri = "jdbc:mysql://localhost:3306/text?useUnicode=true&characterEncoding=UTF-8";//(数据库名，不是连接名)
    static String user = "root";
    static String password = "123456";
    static Connection con = null;
    static PreparedStatement pst = null;
    static Statement st = null;
    static ResultSet rs = null;
    String usr_id = "lihang";

    public static void connection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection(uri, user, password);
    }

    public static void close() throws IOException,SQLException{
        is.close();
        isr.close();
        br.close();
        pst.close();
        st.close();
        rs.close();
        con.close();
    }
    public static void main(String[] args) throws Exception {
        connection();
        getContent();
        close();

    }
    public static void getContent() throws IOException, SQLException {
        String sql = "select id from article where id > 1";
        String id = null;
        pst = con.prepareStatement(sql);
        rs= pst.executeQuery();

        while(rs.next()){
            id = rs.getString(1);
            System.out.println(id);
            URL url = new URL("http://www.textvalve.com/htdatasub/subscribe/articles/v2/article-"+id);
            //	System.out.println(url.toString());
            is = url.openStream();
            isr = new InputStreamReader(is,"UTF-8");
            br = new BufferedReader(isr);
            String str = null;
            while((str = br.readLine())!=null) {
                System.out.println(str);
                str = str.replaceAll("\\\\", "\\\\\\\\");
                System.out.println(str);
                sb.append(str);
            }
            Str = sb.toString();
            dataJson = JSONObject.fromObject(Str);
            sb.setLength(0);
            response = dataJson.getJSONObject("data");
            String ct = response.getString("content");
            System.out.println(ct);
            String sqll ="update article set content ="+ct+ "where id ="+id;

            pst = con.prepareStatement(sqll);
            pst.executeUpdate(sqll);
        }
    }





}
