package code;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Optional;
import java.util.Scanner;

public class Client extends Thread {

    private final Socket socket;
    private final DataInputStream reader;
    private final DataOutputStream speaker;

    public Client(String host, Integer port) throws IOException {
        socket = new Socket(host, port);
        reader = new DataInputStream(this.socket.getInputStream());
        speaker = new DataOutputStream(this.socket.getOutputStream());
    }





    /**
     * To simulate a conversation log, we add '>' before typing a message as an input and a '<' before the response
     */
    @Override
    public void run() {


        boolean keepTalking = true;

        do {
            try {
                keepTalking = listenAndSpeak();
            } catch (IOException e) {
                System.out.println("There were problems during the comunication. Conection lost");
                keepTalking = false;
            }
        } while (true == keepTalking);
        try {
            this.disconnect(); //we don't know if we got here because there was an error or someone wanted to end comunication
            //so we try to close the socket anyways
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Thread.currentThread().interrupt();//stop the thread.
        }


    }

    private boolean listenAndSpeak() throws IOException {
        System.out.print("> ");
        String outMessage = new Scanner(System.in).nextLine();
        if ("X".equalsIgnoreCase(outMessage)) {
            System.out.println("You ended the conection");
            this.speaker.writeUTF(outMessage);
            return false;
        }
        this.speaker.writeUTF("< " + outMessage);

        String inMessage = Optional.ofNullable(this.reader.readUTF()).orElseThrow(IOException::new);
        if ("X".equalsIgnoreCase(inMessage)) {
            System.out.println("The server ended the conection");
            return false;
        }
        System.out.println(inMessage);

        return true;
    }

    /**
     * to close coneection
     *
     * @throws IOException if there a problem closing coneection
     */
    private void disconnect() throws IOException {
        this.socket.close();
    }






    public static void main(String[] args) {
        String host = null;
        Integer port = null;

        System.out.print("Ingrese el host al que intenta conectarse: ");
        host = new Scanner(System.in).next();
        System.out.print("Ingrese el puerto al que desea conectarse: ");
        port = new Scanner(System.in).nextInt();
        System.out.println("Cliente intentando conectarse...");
        try {
            Client c = new Client(host, port);
            System.out.println("Cliente conectado, piede enviar un mensaje y esperar respuesta del servidor " +
                    " Use X para cerrar la conexion");
            c.start();

        } catch (IOException e) {

            System.out.println("Hubo un problema durante la conexion, verificar host y port y reintentar nuevamente");

        }

    }
}
