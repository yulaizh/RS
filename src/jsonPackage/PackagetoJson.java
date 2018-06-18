package jsonPackage;

import bean.ArticleListBean;
import net.sf.json.JSONArray;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PackagetoJson {


    public static String indexPackage(ResultSet rs) throws SQLException {
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

        JSONArray result = JSONArray.fromObject(list);
        return result.toString();
    }

}
