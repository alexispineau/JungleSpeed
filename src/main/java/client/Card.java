package client;

import java.io.Serializable;

public class Card implements Serializable {

    private int id;
    private String picPath;

    public Card(int id, String picPath) {
        this.id = id;
        this.picPath = picPath;
    }

    public int getId() {
        return this.id;
    }

    public String getPicPath() {
        return this.picPath;
    }

}
