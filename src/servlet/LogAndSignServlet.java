package servlet;

import db.DBopLogAndSign;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LogAndSignServlet",urlPatterns = "/LogAndSignServlet")
public class LogAndSignServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String account = request.getParameter("account");
        String password = request.getParameter("password");
        String sql = "select * from user_log where account = '" + account + "' and password = '" + password + "'";
        Boolean retu  = false;
        DBopLogAndSign dBopLogAndSign = new DBopLogAndSign();
        try {
            dBopLogAndSign.Connection();
            retu =  !dBopLogAndSign.select(sql);
            dBopLogAndSign.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(retu.toString());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String account = request.getParameter("account");
        String password = request.getParameter("password");
        String sql = "select * from user_log where account = '" + account + "' and password = '" + password + "'";
        String insertSql = "insert into user_log(account,password) values ('"+account+"','"+password+"')";
        Boolean retu  = false;
        DBopLogAndSign dBopLogAndSign = new DBopLogAndSign();
        try {
            dBopLogAndSign.Connection();
            retu =  dBopLogAndSign.select(sql);
            System.out.println(retu);
            System.out.println(insertSql);
            if (retu){
                dBopLogAndSign.insert(insertSql);
            }
            dBopLogAndSign.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(retu.toString());
    }
}
