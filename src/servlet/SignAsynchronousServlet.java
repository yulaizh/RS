package servlet;

import db.ConPools;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet(name = "SignAsynchronousServlet",urlPatterns = "/SignAsynchronousServlet")
public class SignAsynchronousServlet extends HttpServlet {
    /**
     * 注册时的异步消息
     * */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accept = request.getParameter("account");
        String sql = "select account from user_log where account = ?";
        Boolean retu  = false;
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            con = ConPools.getInstance().getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1,accept);
            rs = pst.executeQuery();

            if (rs.next()){
                retu = false;   //查到了返回false
            }else {
                retu = true;
            }
            con.close();

        }catch (Exception e){ e.printStackTrace(); }
        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(retu.toString());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
