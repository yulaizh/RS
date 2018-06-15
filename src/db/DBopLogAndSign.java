package db;

import java.sql.*;

public class DBopLogAndSign {
    private final String uri = "jdbc:mysql://localhost:3306/RS?useUnicode=true&characterEncoding=UTF-8";
    private final String user = "root";
    private final String password = "123456";
    private Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    private Statement st = null;

    public void Connection() throws SQLException, ClassNotFoundException{
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection(uri, user, password);
    }

    public void close()throws SQLException{
        rs.close();
        st.close();
        con.close();
    }

    public boolean select(String sql) throws SQLException {
        st = con.createStatement();
        rs = st.executeQuery(sql);
        if (rs.next()){
            return false;   //查到了返回false
        }else {
            return true;
        }
    }

    public String select(String sql,String sm) throws SQLException{
        st = con.createStatement();
        rs = st.executeQuery(sql);
        if (rs.next()){
            return rs.getString(sm);
        } else {
            return "null";
        }

    }

    public void insert(String sql)throws SQLException{
        st = con.createStatement();
        st.executeUpdate(sql);
    }


//    public void test(String sql,String sm)throws SQLException{
//        st = con.createStatement();
//        rs = st.executeQuery(sql);
//        if (rs.next()){
//            System.out.println(rs.getString(sm));
//        }
//    }
}
