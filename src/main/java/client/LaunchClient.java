package client;

public class LaunchClient {

    public static void main(String[] args) {
        try {
            new JungleController(8100, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
