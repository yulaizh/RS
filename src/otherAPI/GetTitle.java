package otherAPI;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class GetTitle {
    static StringBuilder sb = new StringBuilder();
    static File file = new File("/Users/lh_os/Desktop/title.txt");
    static String Str = null;
    static String uri = "jdbc:mysql://localhost:3306/text?useUnicode=true&characterEncoding=UTF-8";//(数据库名，不是连接名)
    static String user = "root";
    static String password = "123456";
    static Connection con = null;
    static PreparedStatement pst = null;
    static ResultSet rs = null;
    static FileWriter fw = null;
    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
        start();
        close();
    }
    public static void start() throws ClassNotFoundException, SQLException, IOException {
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection(uri, user, password);
        String sql = "select title from article where id > 1";
        pst = con.prepareStatement(sql);
        rs = pst.executeQuery();
        while(rs.next()) {
            sb.append(rs.getString(1)+"\n");
        }
        String str = sb.toString();
        fw = new FileWriter(file, true);
        PrintWriter pw = new PrintWriter(fw);
        pw.println(str);
    }

    public static void close() throws IOException,SQLException{
        pst.close();
        rs.close();
        con.close();
    }

}