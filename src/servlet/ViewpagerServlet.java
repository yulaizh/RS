package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ViewpagerServlet" ,urlPatterns ="/ViewpagerServlet")
public class ViewpagerServlet extends HttpServlet {

    /**
     *轮播图加载
     * */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sql = "select id,image_list from article where tag exits in ('综合','社会','娱乐','财经','科技','文化')";

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
