package code;

import java.io.IOException;
import java.net.Socket;

public class Client extends Thread {

    private final Socket socket;

    public Client(String host, Integer port) throws IOException {
        socket = new Socket(host, port);
    }

    @Override
    public void run() {
        System.out.println("Soy el cliente y estoy corriendo");
    }
}
