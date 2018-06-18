package servlet;

import bean.HotNewsBean;
import db.ConPools;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "HotNewsServlet" ,urlPatterns ="/HotNewsServlet")
public class HotNewsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sql = "select id,title,image_list from article order by readss desc limit 4";
        List<HotNewsBean> list = new ArrayList<>();
        JSONArray jsonArray;
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            con = ConPools.getInstance().getConnection();
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()){
                HotNewsBean hotNewsBean = new HotNewsBean();
                hotNewsBean.setHref("article.html?id="+rs.getString("id"));
                hotNewsBean.setTitle(rs.getString("title"));
                hotNewsBean.setImage(rs.getString("image_list").split(",")[0]);
                list.add(hotNewsBean);
            }

            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        jsonArray = JSONArray.fromObject(list);
        System.out.println(jsonArray);

        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonArray.toString());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
