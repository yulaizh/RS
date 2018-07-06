package bean;
/**
 * 用户行为
 * */
public class RhroughBean {
    long time = 0;
    String title ;
    int likeornot = 1;
    String tag ;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLikeornot() {
        return likeornot;
    }

    public void setLikeornot(int likeornot) {
        this.likeornot = likeornot;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
