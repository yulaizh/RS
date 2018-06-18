package servlet;

import db.ConPools;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet(name = "LogAndSignServlet",urlPatterns = "/LogAndSignServlet")
public class LogAndSignServlet extends HttpServlet {
    //登录
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String account = request.getParameter("account");
        String password = request.getParameter("password");
        String sql = "select id from user_log where account = '" + account + "' and password = '" + password + "'";
        Boolean retu  = false;
        String id = null;
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            con = ConPools.getInstance().getConnection();
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()){
                id = rs.getString("id");
            }else {
                id = null;
            }
            con.close();
        }catch (Exception e){ e.printStackTrace();}

        retu = id.equals(null)?false:true;
        if (retu){
            session.setAttribute("id",id);
            session.setAttribute("account",account);
        }
        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(retu.toString());
    }



    //注册
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String account = request.getParameter("account");
        String password = request.getParameter("password");
        String sql = "select id from user_log where account = '" + account + "' and password = '" + password + "'";
        String insertSql = "insert into user_log(account,password) values ('"+account+"','"+password+"')";
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Boolean retu  = false;
        String id = null;

        try {
            con = ConPools.getInstance().getConnection();

            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()){
                retu = false;   //查到了返回false
            }else {
                retu = true;
            }


            if (retu){
                pst = con.prepareStatement(insertSql);
                pst.executeUpdate();

                pst = con.prepareStatement(sql);
                rs = pst.executeQuery();
                if (rs.next()){
                    id = rs.getString("id");
                }else {
                    id = null;
                }

                if (!id.equals(null)){
                    String insertSql_info = "insert into user_info (id) values ('"+ id +"')";

                    pst = con.prepareStatement(insertSql_info);
                    pst.executeUpdate();
                }
            }
            con.close();
        }catch (Exception e){ e.printStackTrace(); }

        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(retu.toString());
    }
}
