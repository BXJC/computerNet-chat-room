package other;

import entity.ClientMap;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static class ClientHandler extends Thread{
        private final Socket socket;
        private final ClientMap clientMap;
        private BufferedWriter output;
        int id;
        private ClientHandler(Socket socket,ClientMap clientMap){
            this.socket = socket;
            this.clientMap = clientMap;
        }
        @Override
        public void  run() {
            InputStream is = null;
            try {
                is = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
                BufferedReader input = new BufferedReader(isr);
                OutputStream os = socket.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os, StandardCharsets.UTF_8);
                output = new BufferedWriter(osw);
                String msg = null;
                id = Integer.parseInt(input.readLine());
                System.out.println("连接到了id为" + id + "的用户");
                clientMap.clientHandlerMap.put(id,this);
                while ((msg = input.readLine()) != null) {
                    System.out.println("收到来自id:" + id + " 的消息：" + msg);
                    String []whole = msg.split("\\|");
                    String content = whole[0];
                    if(whole.length == 2){
                        int userId = Integer.parseInt(whole[1]);
                        if(clientMap.getClientHandler(userId) != null)
                        {
                            clientMap.getClientHandler(userId).sendMessage(content);
                            System.out.println("发送消息：" + content + "给id为" + clientMap.getClientHandler(userId).id + "的用户");
                        }
                    }
                    else {
                            List<Integer> idList = new ArrayList<>();
                            for(int i = 1; i < whole.length ; i++)
                                idList.add(Integer.parseInt(whole[i]));
                            System.out.println("idList : " + idList);
                            synchronized (this){
                                for(ClientHandler clientHandler : clientMap.getClientHandlerList(idList))
                                {
                                    clientHandler.sendMessage(content);
                                    System.out.println("发送消息：" + content + "给id为" + clientHandler.id + "的用户");
                                }
                            }
                    }

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
        ClientMap clientMap = new ClientMap();
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("服务器已启动");
        while (true){
            Socket socket = serverSocket.accept();
            System.out.println("获取到新的连接");
            ClientHandler clientHandler = new ClientHandler(socket,clientMap);
            clientHandler.start();
        }
    }
}
