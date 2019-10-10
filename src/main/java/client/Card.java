package client;

public class Card {

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
