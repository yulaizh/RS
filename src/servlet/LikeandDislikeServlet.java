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
import java.sql.SQLException;

@WebServlet(name = "LikeandDislikeServlet" ,urlPatterns = "/LikeandDislikeServlet")
public class LikeandDislikeServlet extends HttpServlet {

    /**
     *写入点赞点踩数据
     * */
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
            con.setAutoCommit(false);

            if (index == 0){
                sql = "update article set likes = likes + 1 where id = ?";
                pst = con.prepareStatement(sql);
                pst.setString(1,article_id);
                pst.executeUpdate();

                sql = "update record set likeornot = 2 where article_id = ? and user_id = ?";
                pst = con.prepareStatement(sql);
                pst.setString(1,article_id);
                pst.setString(2,user_id);
                pst.executeUpdate();

            }else if (index == 1){

                sql = "update article set dislikes = dislikes + 1 where id = ?";
                pst = con.prepareStatement(sql);
                pst.setString(1,article_id);
                pst.executeUpdate();

                sql = "update record set likeornot = 0 where article_id = ? and user_id = ?";
                pst = con.prepareStatement(sql);
                pst.setString(1,article_id);
                pst.setString(2,user_id);
                pst.executeUpdate();

            }

            con.commit();
            con.close();
        }catch (Exception e) {
            try {
                con.rollback();
            }catch (Exception e1){e1.printStackTrace();}
            e.printStackTrace();
        }

    }


    /**
     * 取消点赞样式
     * */
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
            con.setAutoCommit(false);

            if (index == 0){
                sql = "update article set likes = likes - 1 where id = ?";
                pst = con.prepareStatement(sql);
                pst.setString(1,article_id);
                pst.executeUpdate();

                sql = "update record set likeornot = 1 where article_id = ? and user_id = ?";
                pst = con.prepareStatement(sql);
                pst.setString(1,article_id);
                pst.setString(2,user_id);
                pst.executeUpdate();
            }else if (index == 1){
                sql = "update article set dislikes = dislikes - 1 where id = ?";
                pst = con.prepareStatement(sql);
                pst.setString(1,article_id);
                pst.executeUpdate();

                sql = "update record set likeornot = 1 where article_id = ? and user_id = ?";
                pst = con.prepareStatement(sql);
                pst.setString(1,article_id);
                pst.setString(2,user_id);
                pst.executeUpdate();
            }

            con.commit();
            con.close();
        }catch (Exception e){ e.printStackTrace();
            try {
                con.rollback();
            }catch (Exception e1){
                e1.printStackTrace();
            }
        }
    }
}
