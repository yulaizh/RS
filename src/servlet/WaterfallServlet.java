package servlet;

import db.DBopeartion;
import jsonPackage.PackagetoJson;

import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;

@WebServlet(name = "WaterfallServlet",urlPatterns = "/WaterfallServlet")
public class WaterfallServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int param = Integer.parseInt(request.getParameter("param"));
        int num = Integer.parseInt(request.getParameter("num"));
        long counter = Long.parseLong(request.getParameter("counter"));
        String sql = null;
        ResultSet rs = null;
        String result = null;
        DBopeartion dBopeartion = new DBopeartion();
        PackagetoJson packagetoJson = new PackagetoJson();
        if (param == 0){
            sql = "select id,title,image_list,crawl_time,tag from article where crawl_time < '"+counter+"' order by crawl_time desc limit "+num;
        }else if (param == 1){
            sql = "select id,title,image_list,crawl_time,user_add_flag from article where crawl_time < '"+counter+"' order by crawl_time desc limit "+num;
        }else if (param > 1){
            param = param - 2;
            sql = "select id,title,image_list,crawl_time,user_add_flag from article where user_add_flag = '"+param+"' and crawl_time < '"+counter+"' order by crawl_time desc limit "+num;
        }

        try {
            dBopeartion.Connection();
            rs =  dBopeartion.select(sql);
            result = packagetoJson.indexPackage(rs);
            dBopeartion.close();
        }catch (Exception e) {
            System.out.println("2"+e);
        }

        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(result);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
