package servlet;

import db.DBopeartion;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LogAndSignServlet",urlPatterns = "/LogAndSignServlet")
public class LogAndSignServlet extends HttpServlet {
    //登录
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String account = request.getParameter("account");
        String password = request.getParameter("password");
        String sql = "select id from user_log where account = '" + account + "' and password = '" + password + "'";
        Boolean retu  = false;
        DBopeartion dBopeartion = new DBopeartion();
        String id = null;
        try {
            dBopeartion.Connection();
            id = dBopeartion.select(sql,"id");
            dBopeartion.close();
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

        Boolean retu  = false;
        DBopeartion dBopeartion = new DBopeartion();

        try {
            dBopeartion.Connection();
            retu =  dBopeartion.isExist(sql);

            if (retu){
                dBopeartion.insert(insertSql);
                if (!dBopeartion.select(sql,"id").equals(null)){
                    String insertSql_info = "insert into user_info (id) values ('"+ dBopeartion.select(sql,"id") +"')";
                    dBopeartion.insert(insertSql_info);
                }
            }
            dBopeartion.close();
        }catch (Exception e){ e.printStackTrace(); }

        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(retu.toString());
    }
}
