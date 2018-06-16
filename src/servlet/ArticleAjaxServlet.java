package servlet;

import db.DBopLogAndSign;
import db.DBopeartion;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

@WebServlet(name = "ArticleAjaxServlet",urlPatterns = "/ArticleAjaxServlet")
public class ArticleAjaxServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String sql = "select title,content from article where id = '"+ id +"'";
        String json = "";
        //先用这个
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br= null;
        StringBuilder sb = new StringBuilder();
        URL url = new URL("http://www.textvalve.com/htdatasub/subscribe/articles/v2/article-"+id);
        is = url.openStream();
        isr = new InputStreamReader(is,"UTF-8");
        br = new BufferedReader(isr);
        String str = null;
        while((str = br.readLine())!=null) {
            sb.append(str);
        }
        json = sb.toString();
        sb.setLength(0);


//        DBopeartion dBopeartion = new DBopeartion();
//        try{
//            dBopeartion.Connection();
//            json = dBopeartion.select2(sql);
//            dBopeartion.close();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        System.out.println("json"+json);
        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
