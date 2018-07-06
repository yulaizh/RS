package bean;
/**
 * 文章基本信息
 * */
public class ArticleListBean {

    String id = null;
    String title = null;
    String content = null;
    String origin = null;
    String keywords = null;
    String tag = null;
    String description = null;
    String image_list = null;
    long crawl_time = 0;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_list() {
        return image_list;
    }

    public void setImage_list(String image_list) {
        this.image_list = image_list;
    }

    public long getCrawl_time() {
        return crawl_time;
    }

    public void setCrawl_time(long crawl_time) {
        this.crawl_time = crawl_time;
    }


}
