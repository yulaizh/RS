package servlet;

import db.DBopLogAndSign;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "UserAjaxServlet" , urlPatterns = "/UserAjaxServlet")
public class UserAjaxServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        DBopLogAndSign dBopLogAndSign = new DBopLogAndSign();
        String name = (String)session.getAttribute("account");
        long id = Long.parseLong((String) session.getAttribute("id"));
        String sql = "select pic_url from user_info where id = '"+ id +"'";
        String json = null;
        try{
            dBopLogAndSign.Connection();
            json = "{\"name\":\""+name +"\",\"url\":\""+ dBopLogAndSign.select(sql,"pic_url") +"\"}";
            dBopLogAndSign.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println(json);

        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
