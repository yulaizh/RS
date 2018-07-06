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

@WebServlet(name = "Article_commentServlet" ,urlPatterns = "/Article_commentServlet")
public class Article_commentServlet extends HttpServlet {

    /**
     * 给文章添加评论
     * */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user_id = (String) request.getSession().getAttribute("id");
        if (user_id == null){
            response.getWriter().write("请重新登录");
            return;
        }

        String article_id = request.getParameter("id");
        String comment = request.getParameter("comment");
        Connection con = null;
        PreparedStatement pst = null;

        String sql = "insert into comment(article_id,user_id,comment_time,comment_content) values (?,?,\""+System.currentTimeMillis()+"\",?)";
        try{
            con = ConPools.getInstance().getConnection();
            pst = con.prepareStatement(sql);

            pst.setString(1,article_id);
            pst.setString(2,user_id);
            pst.setString(3,comment);
            pst.executeUpdate();

            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("成功");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
