package servlet;

import bean.HotNewsBean;
import db.DBopeartion;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "HotNewsServlet" ,urlPatterns ="/HotNewsServlet")
public class HotNewsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sql = "select id,title,image_list from article order by readss desc limit 4";
        DBopeartion dBopeartion = new DBopeartion();
        ResultSet rs = null;
        List<HotNewsBean> list = new ArrayList<>();
        JSONArray jsonArray;
        try {
            dBopeartion.Connection();
            rs = dBopeartion.select(sql);
            while (rs.next()){
                HotNewsBean hotNewsBean = new HotNewsBean();
                hotNewsBean.setHref("article.html?id="+rs.getString("id"));
                hotNewsBean.setTitle(rs.getString("title"));
                hotNewsBean.setImage(rs.getString("image_list").split(",")[0]);
                list.add(hotNewsBean);
            }

            dBopeartion.close();
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
