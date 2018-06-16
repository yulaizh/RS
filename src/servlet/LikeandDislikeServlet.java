package servlet;

import db.DBopeartion;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LikeandDislikeServlet" ,urlPatterns = "/LikeandDislikeServlet")
public class LikeandDislikeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user_id = (String)request.getSession().getAttribute("id");
        int index = Integer.parseInt(request.getParameter("param"));
        String article_id = request.getParameter("id");
        DBopeartion dBopeartion = new DBopeartion();
        String sql = null;

        try{
            dBopeartion.Connection();

            if (index == 0){
                sql = "update article set likes = likes + 1 where id = '"+ article_id +"'";
                dBopeartion.update(sql);
                sql = "update record set likeornot = 2 where article_id = '"+ article_id +"' and user_id ='"+ user_id +"'";
                dBopeartion.update(sql);
            }else if (index == 1){
                sql = "update article set dislikes = dislikes + 1 where id = '"+ article_id +"'";
                dBopeartion.update(sql);
                sql = "update record set likeornot = 0 where article_id = '"+ article_id +"' and user_id ='"+ user_id +"'";
                dBopeartion.update(sql);
            }

            dBopeartion.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user_id = (String)request.getSession().getAttribute("id");
        int index = Integer.parseInt(request.getParameter("param"));
        String article_id = request.getParameter("id");
        String sql = null;
        DBopeartion dBopeartion = new DBopeartion();

        try{
            dBopeartion.Connection();

            if (index == 0){
                sql = "update article set likes = likes - 1 where id = '"+ article_id +"'";
                dBopeartion.update(sql);
                sql = "update record set likeornot = 1 where article_id = '"+ article_id +"' and user_id ='"+ user_id +"'";
                dBopeartion.update(sql);
            }else if (index == 1){
                sql = "update article set dislikes = dislikes - 1 where id = '"+ article_id +"'";
                dBopeartion.update(sql);
                sql = "update record set likeornot = 1 where article_id = '"+ article_id +"' and user_id ='"+ user_id +"'";
                dBopeartion.update(sql);
            }

            dBopeartion.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
