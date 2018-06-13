package db;

import java.sql.*;

public class DBopeartion {
    static String uri = "jdbc:mysql://localhost:3306/text?useUnicode=true&characterEncoding=UTF-8";
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
            result += " \"user_add_flag\":" +"\""+rs.getString(5)+"\""+"},";
        }

        result = result.substring(0, result.length()-1) +"]}";
        System.out.println(result);
        return result;
    }



    public void close()throws SQLException{
        rs.close();
        pst.close();
        con.close();
    }



}
