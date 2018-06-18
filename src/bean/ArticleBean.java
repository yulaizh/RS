package bean;

import java.util.List;

public class ArticleBean {
    String id = null;
    String title = null;
    String content = null;
    String origin = null;
    String keywords = null;
    String tag = null;
    int readss = 0;
    int likes = 0;
    int dislikes = 0;
    long crawl_time = 0;
    int likeornot = 1;
    List<CommentBean> list;

    public List<CommentBean> getList() { return list; }

    public void setList(List<CommentBean> list) { this.list = list; }

    public int getLikeornot() { return likeornot; }

    public void setLikeornot(int likeornot) { this.likeornot = likeornot; }

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

    public int getReadss() {
        return readss;
    }

    public void setReadss(int readss) {
        this.readss = readss;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public long getCrawl_time() {
        return crawl_time;
    }

    public void setCrawl_time(long crawl_time) {
        this.crawl_time = crawl_time;
    }

}
