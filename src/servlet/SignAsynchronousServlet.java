package servlet;

import db.DBopeartion;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SignAsynchronousServlet",urlPatterns = "/SignAsynchronousServlet")
public class SignAsynchronousServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accept = request.getParameter("account");
        String sql = "select account from user_log where account = '"+ accept +"'";
        Boolean retu  = false;
        DBopeartion dBopeartion = new DBopeartion();

        try {
            dBopeartion.Connection();
            retu =  dBopeartion.isExist(sql);
            dBopeartion.close();
        }catch (Exception e){
            e.printStackTrace();

        }
        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(retu.toString());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
