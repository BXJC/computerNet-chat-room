import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Server {
    private static Map<Integer,ClientHandler> clientMap = new HashMap<>();
    public static class ClientHandler extends Thread{
        private final Socket socket;
        BufferedReader input;
        BufferedWriter output;
        private int id;
        private ClientHandler(Socket socket){
            this.socket = socket;
        }
        @Override
        public void  run() {
            InputStream is = null;
            try {
                is = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
                input = new BufferedReader(isr);
                OutputStream os = socket.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os, StandardCharsets.UTF_8);
                output = new BufferedWriter(osw);
                String msg = null;
                id = Integer.parseInt(input.readLine());
                    System.out.println("连接到了id为" + id + "的用户");
                    clientMap.put(id,this);
                while ((msg = input.readLine()) != null) {
                    System.out.println("收到来自id:" + id + " 的消息：" + msg);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        public void sendMessage(String msg) throws Exception{
            output.write(msg + "\n");
            output.flush();
        }
    }



    public static void main(String[] args) throws Exception{
        int port = 10010;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("服务器已启动");
        while (true){
            Socket socket = serverSocket.accept();
            System.out.println("获取到新的连接");
            ClientHandler clientHandler = new ClientHandler(socket);
            clientHandler.start();
        }
    }
}
