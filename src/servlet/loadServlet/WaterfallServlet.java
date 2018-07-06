package servlet.loadServlet;

import bean.ArticleListBean;
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

@WebServlet(name = "WaterfallServlet",urlPatterns = "/WaterfallServlet")
public class WaterfallServlet extends HttpServlet {

    /**
     * 瀑布流加载
     * */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        String param = request.getParameter("param");
        String current_id = request.getParameter("counter");
        String sql = null;
        String result = null;

        switch (param){
            case "推荐": sql = "select * from article where id < ? order by id desc limit 4";break;   //推荐待做
            case "综合": sql = "select * from article where id < ? and tag not in ('社会','娱乐','财经','科技','文化','教育','时事','国际','旅游','体育','汽车','时尚','') order by id desc limit 4";break;
            case "社会": sql = "select * from article where id < ? and tag = '社会' order by id desc limit 4 ";break;
            case "娱乐": sql = "select * from article where id < ? and tag = '娱乐' order by id desc limit 4 ";break;
            case "财经": sql = "select * from article where id < ? and tag = '财经' order by id desc limit 4 ";break;
            case "科技": sql = "select * from article where id < ? and tag = '科技' order by id desc limit 4 ";break;
            case "文化": sql = "select * from article where id < ? and tag = '文化' order by id desc limit 4 ";break;
            case "教育": sql = "select * from article where id < ? and tag = '教育' order by id desc limit 4 ";break;
            case "时事": sql = "select * from article where id < ? and tag = '时事' order by id desc limit 4 ";break;
            case "国际": sql = "select * from article where id < ? and tag = '国际' order by id desc limit 4 ";break;
            case "旅游": sql = "select * from article where id < ? and tag = '旅游' order by id desc limit 4 ";break;
            case "体育": sql = "select * from article where id < ? and tag = '体育' order by id desc limit 4 ";break;
            case "汽车": sql = "select * from article where id < ? and tag = '汽车' order by id desc limit 4 ";break;
            case "时尚": sql = "select * from article where id < ? and tag = '时尚' order by id desc limit 4 ";break;
            case "其他": sql = "select * from article where id < ? and tag = '' ";
        }

        try {
            con = ConPools.getInstance().getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1,current_id);
            rs = pst.executeQuery();
            List<ArticleListBean> list = new ArrayList<>();
            while (rs.next()){
                ArticleListBean articleBean = new ArticleListBean();
                if(rs.getString("id").contains("\"")||rs.getString("title").contains("\"")||rs.getString("image_list").contains("\"")||rs.getString("image_list").equals("")){
                    continue;
                }
                articleBean.setId(rs.getString("id"));
                articleBean.setTitle(rs.getString("title"));
                articleBean.setImage_list(rs.getString("image_list"));
                articleBean.setCrawl_time(Long.parseLong(rs.getString("crawl_time")));
                articleBean.setTag(rs.getString("tag"));
                articleBean.setOrigin(rs.getString("origin"));
                list.add(articleBean);
            }

            result = JSONArray.fromObject(list).toString();
            con.close();
        }catch (Exception e) {
            System.out.println("WaterfallServlet"+e);
        }

        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(result);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
