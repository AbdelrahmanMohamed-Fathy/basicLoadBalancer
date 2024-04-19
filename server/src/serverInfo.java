import java.net.InetAddress;

public class serverInfo {
    private InetAddress ip;
    private int port;

    public serverInfo(InetAddress ip, int port) {
        this.ip = ip;
        this.port = port;
    }
}
