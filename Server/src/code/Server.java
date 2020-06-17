package code;


import java.io.IOException;
import java.net.ServerSocket;

/**
 * This class will be one who open a socket server on a port and accepts conection from costumers
 * Each costumer is attended in a separated thread to handle multiple clients
 */
public class Server {

    private final ServerSocket serverSocket;
    private final Integer PORT = 3000; //port that was requested

    /**
     * We create a serverSocket in the designated port
     * @throws IOException if anythings goes wrong.
     */
    public Server() throws IOException {
        this.serverSocket = new ServerSocket(PORT);
    }


    public static void main(String[] args) {
        try {
            Server server = new Server();
        } catch (IOException e) {
            System.out.println("There was a problem setting the server up");
        }
    }

}
