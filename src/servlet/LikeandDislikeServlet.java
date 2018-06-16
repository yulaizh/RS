package servlet;

import db.DBopeartion;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LikeandDislikeServlet" ,urlPatterns = "/LikeandDislikeServlet")
public class LikeandDislikeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int index = Integer.parseInt(request.getParameter("param"));
        String id = request.getParameter("id");
        DBopeartion dBopeartion = new DBopeartion();

        String sql = null;
        if (index == 0){
            sql = "update article set likes = likes + 1 where id = '"+ id +"'";
        }else if (index == 1){
            sql = "update article set dislikes = dislikes + 1 where id = '"+ id +"'";
        }
        try{
            dBopeartion.Connection();
            dBopeartion.update(sql);
            dBopeartion.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int index = Integer.parseInt(request.getParameter("param"));
        String id = request.getParameter("id");
        String sql = null;
        DBopeartion dBopeartion = new DBopeartion();
        if (index == 0){
            sql = "update article set likes = likes - 1 where id = '"+ id +"'";
        }else if (index == 1){
            sql = "update article set dislikes = dislikes - 1 where id = '"+ id +"'";
        }
        try{
            dBopeartion.Connection();
            dBopeartion.update(sql);
            dBopeartion.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
