package servlet.loadServlet;

import bean.RecordBean;
import bean.RhroughBean;
import db.ConPools;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


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

@WebServlet(name = "GetRecordServlet" ,urlPatterns = "/GetRecordServlet")
public class GetRecordServlet extends HttpServlet {
    /**
     *获取各类别浏览数量，生成可视化图形
     * */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user_id = (String) request.getSession().getAttribute("id");
        String sql = "select tag,count(tag) from record where user_id = "+ user_id +" and tag not in ('社会','娱乐','财经','科技','文化','教育','时事','国际','旅游','体育','汽车','时尚','') group by tag";
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        RecordBean recordBean = new RecordBean();
        JSONObject jsonObject = null;
        System.out.println(sql);
        try{
            con = ConPools.getInstance().getConnection();
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()){ recordBean.setSynthesize(rs.getInt(2)); System.out.println(rs.getString(2));}
            sql = "select tag,count(tag) from record where user_id = "+ user_id +" and tag = '社会'";
            rs = con.prepareStatement(sql).executeQuery();
            if (rs.next()){ recordBean.setSociety(rs.getInt(2)); System.out.println(rs.getString(2));}
            sql = "select tag,count(tag) from record where user_id = "+ user_id +" and tag = '娱乐'";
            rs = con.prepareStatement(sql).executeQuery();
            if (rs.next()){ recordBean.setEntertainment(rs.getInt(2));}
            sql = "select tag,count(tag) from record where user_id = "+ user_id +" and tag = '财经'";
            rs = con.prepareStatement(sql).executeQuery();
            if (rs.next()){ recordBean.setEconomics(rs.getInt(2));}
            sql = "select tag,count(tag) from record where user_id = "+ user_id +" and tag = '科技'";
            rs = con.prepareStatement(sql).executeQuery();
            if (rs.next()){ recordBean.setTechnology(rs.getInt(2));}
            sql = "select tag,count(tag) from record where user_id = "+ user_id +" and tag = '文化'";
            rs = con.prepareStatement(sql).executeQuery();
            if (rs.next()){ recordBean.setCivilization(rs.getInt(2));}
            sql = "select tag,count(tag) from record where user_id = "+ user_id +" and tag = '教育'";
            rs = con.prepareStatement(sql).executeQuery();
            if (rs.next()){ recordBean.setEducation(rs.getInt(2));}
            sql = "select tag,count(tag) from record where user_id = "+ user_id +" and tag = '时事'";
            rs = con.prepareStatement(sql).executeQuery();
            if (rs.next()){ recordBean.setCurrent_events(rs.getInt(2));}
            sql = "select tag,count(tag) from record where user_id = "+ user_id +" and tag = '国际'";
            rs = con.prepareStatement(sql).executeQuery();
            if (rs.next()){ recordBean.setInternational(rs.getInt(2));}
            sql = "select tag,count(tag) from record where user_id = "+ user_id +" and tag = '旅游'";
            rs = con.prepareStatement(sql).executeQuery();
            if (rs.next()){ recordBean.setTourism(rs.getInt(2));}
            sql = "select tag,count(tag) from record where user_id = "+ user_id +" and tag = '体育'";
            rs = con.prepareStatement(sql).executeQuery();
            if (rs.next()){ recordBean.setPhysical(rs.getInt(2));}
            sql = "select tag,count(tag) from record where user_id = "+ user_id +" and tag = '汽车'";
            rs = con.prepareStatement(sql).executeQuery();
            if (rs.next()){ recordBean.setCar(rs.getInt(2));}
            sql = "select tag,count(tag) from record where user_id = "+ user_id +" and tag = '时尚'";
            rs = con.prepareStatement(sql).executeQuery();
            if (rs.next()){ recordBean.setFashion(rs.getInt(2));}
            sql = "select tag,count(tag) from record where user_id = "+ user_id +" and tag = '' ";
            rs = con.prepareStatement(sql).executeQuery();
            if (rs.next()){ recordBean.setOther(rs.getInt(2));}

            rs.close();
            pst.close();
            con.close();
        }catch (Exception e){ e.printStackTrace(); }
        jsonObject = JSONObject.fromObject(recordBean);
        System.out.println(jsonObject.toString());
        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonObject.toString());
    }


    /**
     * 加载浏览记录
     * */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user_id = (String) request.getSession().getAttribute("id");
        String sql = "select read_time,title,likeornot,record.tag from record,article where article_id = article.id and user_id = ?";
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        List<RhroughBean> list = new ArrayList<>();
        JSONArray jsonArray = null;
        try{
            con = ConPools.getInstance().getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1,user_id);
            rs = pst.executeQuery();
            while (rs.next()){
                RhroughBean rhroughBean = new RhroughBean();
                rhroughBean.setTime(rs.getLong(1));
                rhroughBean.setTitle(rs.getString(2));
                rhroughBean.setLikeornot(rs.getInt(3));
                rhroughBean.setTag(rs.getString(4));
                list.add(rhroughBean);
            }

            con.close();
        }catch (Exception e){e.printStackTrace();}

        jsonArray = JSONArray.fromObject(list);
        System.out.println(jsonArray.toString());
        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonArray.toString());

    }




}
