package db;

import java.io.Reader;
import java.sql.*;

public class DBopeartion {
    static String uri = "jdbc:mysql://localhost:3306/RS?useUnicode=true&characterEncoding=UTF-8&useOldAliasMetadataBehavior=true";
    static String user = "root";
    static String password = "123456";
    static Connection con = null;

    static PreparedStatement pst = null;
    static ResultSet rs = null;


    public void Connection() throws SQLException, ClassNotFoundException{
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection(uri, user, password);
    }


    public String select(String sql) throws SQLException{
        String result = "{\"list\":[";

        System.out.println(sql);

        pst = con.prepareStatement(sql);
        rs = pst.executeQuery();

        while (rs.next()){
            if(rs.getString(2).contains("\"")||rs.getString(3).contains("\"")||rs.getString(4).contains("\"")||rs.getString(3).equals("")){
                continue;
            }
            result += "{\"id\":"+rs.getString(1)+",";
            result += " \"title\":"+"\""+rs.getString(2)+"\""+",";
            result += " \"image_list\":" +"\""+rs.getString(3)+"\""+",";
            result += " \"crawl_time\":" +"\""+rs.getString(4)+"\""+",";
            result += " \"tag\":" +"\""+rs.getString(5)+"\""+"},";
        }

        result = result.substring(0, result.length()-1) +"]}";
        System.out.println(result);
        return result;
    }

    public String select2(String sql)throws SQLException{
        String json = "{\"title\":\"";
        pst = con.prepareStatement(sql);
        rs = pst.executeQuery();
        System.out.println(sql);
        String a = null;
        String b = null;
        if (rs.next()){
            a = rs.getString("title");
            b = rs.getString("content");
        }
        json += a + "\",\"content\":\"" + b + "\"}";
        System.out.println("DB"+json);
        return json;
    }

    public void select3(String sql) throws SQLException{
        pst = con.prepareStatement(sql);
        rs = pst.executeQuery();
        while (rs.next()){
            System.out.println(rs.getString("content"));
        }
    }

    public void close()throws SQLException{
        rs.close();
        pst.close();
        con.close();
    }

    public static String getClobString(ResultSet rs, String colName) {
        try {
            Reader reader = rs.getCharacterStream(colName);
            if (reader == null) {
                return null;
            }
            StringBuffer sb = new StringBuffer();
            char[] charbuf = new char[4096];
            for (int i = reader.read(charbuf); i > 0; i = reader.read(charbuf)) {
                sb.append(charbuf, 0, i);
            }
            return sb.toString();
        } catch (Exception e) {
            return "";
        }
    }


    public void update(String sql)throws SQLException{
        pst = con.prepareStatement(sql);
        pst.executeUpdate();
    }


}
