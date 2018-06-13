package baiduAPI;

import java.util.ArrayList;

public class Bean {
    ArrayList<String> index;
    ArrayList<String> content;

    public void setIndex(ArrayList<String> index) {
        this.index = index;
    }

    public void setContent(ArrayList<String> content) {
        this.content = content;
    }

    public ArrayList<String> getIndex() {

        return index;
    }

    public ArrayList<String> getContent() {
        return content;
    }
}
