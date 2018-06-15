package servlet;

import bean.LoadBean;
import db.DBopeartion;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet(name = "IndexLoadingServlet",urlPatterns = "/IndexLoadingServlet")
public class IndexLoadingServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int param = Integer.parseInt(request.getParameter("param"));
        String sql = null;
        if (param == 0){
            sql = "select id,title,image_list,crawl_time,tag from article order by crawl_time desc limit 4";
        }else if (param == 1){
            sql = "select id,title,image_list,crawl_time,tag from article order by crawl_time desc limit 4";
        }else if (param > 1){
            param = param - 2;
            sql = "select id,title,image_list,crawl_time,tag from article where user_add_flag = '" + param + "' order by crawl_time desc limit 4";
        }

        String result = null;
        DBopeartion dBopeartion = new DBopeartion();

        try {
            dBopeartion.Connection();
            result =  dBopeartion.select(sql);

            dBopeartion.close();
        }catch (Exception e) {
            System.out.println("1"+e);
        }

        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(result);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
