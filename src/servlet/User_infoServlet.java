package servlet;

import bean.UserBean;
import db.ConPools;
import net.sf.json.JSONObject;

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

@WebServlet(name = "User_infoServlet" ,urlPatterns = "/User_infoServlet")
public class User_infoServlet extends HttpServlet {

    /**
     *更新个人信息
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String user_id = (String) session.getAttribute("id");
        Connection con = null;
        PreparedStatement pst = null;
        String sql = "update user_info set sex = ?,birth = ?,address_province = ?,address_city = ?,address_district = ?,address_detailed = ? where id = ?";

        try{
            con = ConPools.getInstance().getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1,request.getParameter("sexIndex"));
            pst.setString(2,request.getParameter("year")+"-"+request.getParameter("month")+"-"+request.getParameter("day"));
            pst.setString(3,request.getParameter("province"));
            pst.setString(4,request.getParameter("city"));
            pst.setString(5,request.getParameter("sector"));
            pst.setString(6,request.getParameter("area"));
            pst.setString(7,user_id);

            pst.executeUpdate();

            pst.close();
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
    * 加载个人信息
    * */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String user_id = (String) session.getAttribute("id");
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "select * from user_info where id = ?";
        UserBean userBean = new UserBean();
        JSONObject jsonObject = null;

        try{

            con = ConPools.getInstance().getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1,user_id);
            rs = pst.executeQuery();
            if (rs.next()){
                userBean.setSexIndex(rs.getString("sex"));
                userBean.setYear(rs.getString("birth").split("-")[0]);
                userBean.setMonth(rs.getString("birth").split("-")[1]);
                userBean.setDay(rs.getString("birth").split("-")[2]);
                userBean.setProvince(rs.getString("address_province"));
                userBean.setCity(rs.getString("address_city"));
                userBean.setSector(rs.getString("address_district"));
                userBean.setArea(rs.getString("address_detailed"));
            }
            rs.close();
            pst.close();
            con.close();
            jsonObject = JSONObject.fromObject(userBean);
        }catch (Exception e){
            e.printStackTrace();
        }

        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonObject.toString());
    }



}
