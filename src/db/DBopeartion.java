package db;

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

    public ResultSet select(String sql) throws SQLException{
        pst = con.prepareStatement(sql);
        rs = pst.executeQuery();
        return rs;
    }

    public String select(String sql,String label) throws SQLException{
        pst = con.prepareStatement(sql);
        rs = pst.executeQuery();
        if (rs.next()){
            return rs.getString(label);
        }else {
            return null;
        }
    }

    public boolean isExist(String sql) throws SQLException {
        pst = con.prepareStatement(sql);
        rs = pst.executeQuery();
        if (rs.next()){
            return false;   //查到了返回false
        }else {
            return true;
        }
    }

    public void update(String sql)throws SQLException{
        pst = con.prepareStatement(sql);
        pst.executeUpdate();
    }

    public void insert(String sql) throws SQLException{
        pst = con.prepareStatement(sql);
        pst.executeUpdate();
    }

    public void close()throws SQLException{
        rs.close();
        pst.close();
        con.close();
    }

}
