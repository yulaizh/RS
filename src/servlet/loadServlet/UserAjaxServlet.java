package servlet.loadServlet;


import db.ConPools;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet(name = "UserAjaxServlet" , urlPatterns = "/UserAjaxServlet")
public class UserAjaxServlet extends HttpServlet {
    /**
     * 加载头像，上传头像还没弄
     * */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String name = (String)session.getAttribute("account");
        long id = Long.parseLong((String) session.getAttribute("id"));
        String sql = "select pic_url from user_info where id = '"+ id +"'";
        String json = null;
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try{
            con = ConPools.getInstance().getConnection();
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()){
                json = "{\"name\":\""+name +"\",\"url\":\""+ rs.getString("pic_url") +"\"}";
            }else {
                json = "{\"name\":\""+name +"\",\"url\":\""+ null +"\"}";
            }
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
