package servlet;

import bean.ArticleBean;
import db.DBopeartion;
import jsonPackage.PackagetoJson;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.ResultSet;
import java.util.Date;

/*
* 还缺获取文章评论
* */

@WebServlet(name = "Article_infoServlet",urlPatterns = "/Article_infoServlet")
public class Article_infoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String article_id = request.getParameter("id");
        HttpSession session = request.getSession();
        String user_id = (String)session.getAttribute("id");
        JSONObject jsonObject = null;
        String sql = null;
//        String sql = "select title,content from article where id = '"+ id +"'";
        //先用这个
        String json = "";
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br= null;
        StringBuilder sb = new StringBuilder();
        URL url = new URL("http://www.textvalve.com/htdatasub/subscribe/articles/v2/article-"+article_id);
        is = url.openStream();
        isr = new InputStreamReader(is,"UTF-8");
        br = new BufferedReader(isr);
        String str = null;
        while((str = br.readLine())!=null) {
            sb.append(str);
        }
        json = sb.toString();
        sb.setLength(0);

        ArticleBean articleBean = new ArticleBean();
        jsonObject = JSONObject.fromObject(json);
        jsonObject = jsonObject.getJSONObject("data");
        articleBean.setId(jsonObject.getString("id"));
        articleBean.setTitle(jsonObject.getString("title"));
        articleBean.setContent(jsonObject.getString("content"));


        ResultSet rs = null;
        DBopeartion dBopeartion = new DBopeartion();
        try{
            dBopeartion.Connection();
            sql = "select * from article where id ='"+ article_id +"'";
            rs = dBopeartion.select(sql);
            if (rs.next()){
                articleBean.setCrawl_time(Long.parseLong(rs.getString("crawl_time")));
                articleBean.setTag(rs.getString("tag"));
                articleBean.setKeywords(rs.getString("keywords"));
                articleBean.setDislikes(rs.getInt("dislikes"));
                articleBean.setLikes(rs.getInt("likes"));
                articleBean.setReadss(rs.getInt("readss"));
                articleBean.setOrigin(rs.getString("origin"));
            }

            sql = "select likeornot from record where user_id = '"+ user_id + "' and article_id = '"+ article_id +"'";
            String likeornot = dBopeartion.select(sql,"likeornot");
            if (likeornot != null){
                articleBean.setLikeornot(Integer.parseInt(likeornot));
            }
            sql = "update article set readss  = readss + 1 where id  ='"+ article_id +"'";
            dBopeartion.update(sql);
            sql = "insert into record(user_id,article_id,tag,read_time) values (\""+ user_id + "\",\""+ article_id +"\",\""+ articleBean.getTag() +"\",\""+ System.currentTimeMillis()  +"\")";
            System.out.println(sql);
            dBopeartion.insert(sql);

            dBopeartion.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        jsonObject = JSONObject.fromObject(articleBean);

        System.out.println("jsonObject"+jsonObject.toString());
        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonObject.toString());

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
