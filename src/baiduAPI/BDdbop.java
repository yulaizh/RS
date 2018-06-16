package baiduAPI;

import java.sql.*;
import java.util.ArrayList;

public class BDdbop {
    static String uri = "jdbc:mysql://localhost:3306/RS?useUnicode=true&characterEncoding=UTF-8";
    static String user = "root";
    static String password = "123456";
    static Connection con = null;

    static PreparedStatement pst = null;
    static ResultSet rs = null;

    public void Connection() throws SQLException, ClassNotFoundException{
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection(uri, user, password);
    }

    public void close()throws SQLException{
        rs.close();
        pst.close();
        con.close();
    }

    public Bean select(String sql) throws SQLException{
        ArrayList<String> index = new ArrayList<>();
        ArrayList<String> content = new ArrayList<>();
        String c = "";

        pst = con.prepareStatement(sql);
        rs = pst.executeQuery();
        while(rs.next()){
            c += "{\"title\":\""+rs.getString("title")+"\",";
            c += "\"content\":\""+rs.getString("description")+"\"}";
            index.add(rs.getString("id"));
            content.add(c);
            c = "";
        }
        Bean bean = new Bean();
        bean.setIndex(index);
        bean.setContent(content);
        return bean;
    }




    public void update(String container ,String sql) throws SQLException {
        pst = con.prepareStatement(sql);
        pst.setString(1,container);
        pst.executeUpdate();
    }
}
