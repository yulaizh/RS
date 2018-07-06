package servlet.loadServlet;

import bean.ArticleBean;
import bean.CommentBean;
import db.ConPools;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
@WebServlet(name = "Article_LoadServlet",urlPatterns = "/Article_LoadServlet")
public class Article_LoadServlet extends HttpServlet {
    /**
     *分三步获取文章 基本信息，评论，用户行为，以及增加文章阅读数
     * */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String article_id = request.getParameter("id");
        HttpSession session = request.getSession();
        String user_id = (String)session.getAttribute("id");

        Connection con = null;
        JSONObject jsonObject = null;
        String sql = null;
        String likeornot = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
        ArticleBean articleBean = new ArticleBean();
        List<CommentBean> list = new ArrayList<>();


        //数据库操作
        try{
            con = ConPools.getInstance().getConnection();
            con.setAutoCommit(false);
            //提取文章基本信息
            sql = "select * from article where id = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1,article_id);
            rs = pst.executeQuery();
            if (rs.next()){
                articleBean.setContent(rs.getString("content").replaceAll("<br>","\n"));
                articleBean.setId(rs.getString("id"));
                articleBean.setTitle(rs.getString("title"));
                articleBean.setCrawl_time(Long.parseLong(rs.getString("crawl_time")));
                articleBean.setTag(rs.getString("tag"));
                articleBean.setKeywords(rs.getString("keywords"));
                articleBean.setDislikes(rs.getInt("dislikes"));
                articleBean.setLikes(rs.getInt("likes"));
                articleBean.setReadss(rs.getInt("readss"));
                articleBean.setOrigin(rs.getString("origin"));
            }

            //提取用户对文章的评价
            sql = "select likeornot from record where user_id = ? and article_id = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1,user_id);
            pst.setString(2,article_id);

            rs = pst.executeQuery();
            if (rs.next()){
                likeornot =  rs.getString("likeornot");
            }else {
                likeornot =  null;
            }

            if (likeornot != null){
                articleBean.setLikeornot(Integer.parseInt(likeornot));
            }


            //提取评论信息
            sql = "select * from comment where article_id = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1,article_id);
            rs = pst.executeQuery();
            while (rs.next()){
                CommentBean commentBean = new CommentBean();
                System.out.println(rs.getString("article_id"));
                commentBean.setArticle_id(rs.getString("article_id"));
                commentBean.setComment_id(rs.getString("comment_id"));
                commentBean.setUser_id(rs.getString("user_id"));
                commentBean.setComment_content(rs.getString("comment_content"));
                commentBean.setComment_time(rs.getLong("comment_time"));
                list.add(commentBean);
            }
            articleBean.setList(list);

            //增加阅读数，添加阅读记录
            sql = "update article set readss  = readss + 1 where id  = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1,article_id);
            pst.executeUpdate();
            sql = "insert into record(user_id,article_id,tag,read_time) values (?,?,?,\""+ System.currentTimeMillis() +"\")";
            pst = con.prepareStatement(sql);
            pst.setString(1,user_id);
            pst.setString(2,article_id);
            pst.setString(3,articleBean.getTag());
            pst.executeUpdate();

            con.commit();
            con.close();
        }catch (Exception e){
            try{
                con.rollback();
            }catch (Exception e1){
                e1.printStackTrace();
            }
            e.printStackTrace();
        }

        //bean转json，发送到前端
        jsonObject = JSONObject.fromObject(articleBean);
        System.out.println("jsonObject"+jsonObject.toString());
        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonObject.toString());
    }




    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
    //    private String getContent(String article_id) throws IOException{
//        String json = null;
//        InputStream is = null;
//        InputStreamReader isr = null;
//        BufferedReader br= null;
//        JSONObject jsonObject = null;
//        StringBuilder sb = new StringBuilder();
//        URL url = new URL("http://www.textvalve.com/htdatasub/subscribe/articles/v2/article-"+article_id);
//        is = url.openStream();
//        isr = new InputStreamReader(is,"UTF-8");
//        br = new BufferedReader(isr);
//        String str = null;
//        while((str = br.readLine())!=null) {
//            sb.append(str);
//        }
//        json = sb.toString();
//        sb.setLength(0);
//        jsonObject = JSONObject.fromObject(json);
//        jsonObject = jsonObject.getJSONObject("data");
//
//
//        is.close();
//        isr.close();
//        br.close();
//        return jsonObject.getString("content");
//    }
}
