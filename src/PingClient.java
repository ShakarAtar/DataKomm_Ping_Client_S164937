import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class PingClient {

    private Socket socket;
    private BufferedReader fromServer;
    private DataOutputStream toServer;
    private long start;


    public PingClient() {
        socket = new Socket();
    }

    public static void main(String[] args) throws IOException {

        PingClient client = new PingClient();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please input the domain you want to connect to: ");
        String hostName = scanner.nextLine();
        System.out.println("Please input your chosen port: ");
        int port = scanner.nextInt();
        System.out.println("Please input your chosen timeout: ");
        int timeOut = scanner.nextInt();
        System.out.println("Please input how many times you want to ping: ");
        int amount = scanner.nextInt();

        client.connectSocket(hostName, port, timeOut);
        System.out.println("client.connectSocket");
        client.amountOfPings(amount);
        System.out.println("client.amount");

    }
    //method to get the start time
    public void setStartTime(){
        start = System.currentTimeMillis();

    }


    //method to get the elapsed time.
    public void setElapsedTime(){
        System.out.println("Time elapsed = "+(System.currentTimeMillis()-start));

    }



    //method to connect socket to the given hostname and port
    public void connectSocket (String hostName, int port, int timeOut) throws IOException{
//        Socket socket = new Socket();
        //Here we connect to the socket
        socket.connect(new InetSocketAddress(hostName, port), timeOut);
        System.out.println("socket.connect");
        //We use BufferedReader so we are able to wait for answer from the server
        fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        System.out.println("fromServer");
        toServer = new DataOutputStream(socket.getOutputStream());
        System.out.println("toSever");

    }

    public boolean sendPing() throws IOException{
        System.out.println("send ping reached");
        byte mes = 13;
        setStartTime();
        System.out.println("startTime");
        if (!socket.isConnected()){

            return false;
        }
        toServer.writeByte(mes);
        System.out.println("toServer bytes");
        fromServer.readLine();
        System.out.println("fromServer readline");
        setElapsedTime();
        System.out.println("setElapsed reached");
        return true;
    }

    public void amountOfPings(int amount) throws IOException {
        for (int i = 0; i < amount; i++){
            System.out.println("pinged");
            if (!sendPing()){
                System.out.println("Failed: " + i+1);
            }

        }
    }

}
