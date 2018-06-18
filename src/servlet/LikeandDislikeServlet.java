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

@WebServlet(name = "LikeandDislikeServlet" ,urlPatterns = "/LikeandDislikeServlet")
public class LikeandDislikeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user_id = (String)request.getSession().getAttribute("id");
        int index = Integer.parseInt(request.getParameter("param"));
        String article_id = request.getParameter("id");
        String sql = null;
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try{
            con = ConPools.getInstance().getConnection();

            if (index == 0){
                sql = "update article set likes = likes + 1 where id = '"+ article_id +"'";
                pst = con.prepareStatement(sql);
                pst.executeUpdate();
                sql = "update record set likeornot = 2 where article_id = '"+ article_id +"' and user_id ='"+ user_id +"'";
                pst = con.prepareStatement(sql);
                pst.executeUpdate();
            }else if (index == 1){
                sql = "update article set dislikes = dislikes + 1 where id = '"+ article_id +"'";
                pst = con.prepareStatement(sql);
                pst.executeUpdate();
                sql = "update record set likeornot = 0 where article_id = '"+ article_id +"' and user_id ='"+ user_id +"'";
                pst = con.prepareStatement(sql);
                pst.executeUpdate();
            }

            con.close();
        }catch (Exception e){ e.printStackTrace(); }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user_id = (String)request.getSession().getAttribute("id");
        int index = Integer.parseInt(request.getParameter("param"));
        String article_id = request.getParameter("id");
        String sql = null;
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try{
            con = ConPools.getInstance().getConnection();
            if (index == 0){
                sql = "update article set likes = likes - 1 where id = '"+ article_id +"'";
                pst = con.prepareStatement(sql);
                pst.executeUpdate();
                sql = "update record set likeornot = 1 where article_id = '"+ article_id +"' and user_id ='"+ user_id +"'";
                pst = con.prepareStatement(sql);
                pst.executeUpdate();
            }else if (index == 1){
                sql = "update article set dislikes = dislikes - 1 where id = '"+ article_id +"'";
                pst = con.prepareStatement(sql);
                pst.executeUpdate();
                sql = "update record set likeornot = 1 where article_id = '"+ article_id +"' and user_id ='"+ user_id +"'";
                pst = con.prepareStatement(sql);
                pst.executeUpdate();
            }

            con.close();
        }catch (Exception e){ e.printStackTrace(); }
    }
}
