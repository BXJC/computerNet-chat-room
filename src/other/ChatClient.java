package other;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ChatClient {
            private BufferedReader input;
            private BufferedWriter output;
            String message;
        public ChatClient(int userId) throws IOException {
            Socket socket = new Socket("127.0.0.1",10010);
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
            input = new BufferedReader(isr);
            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os, StandardCharsets.UTF_8);
            output = new BufferedWriter(osw);
        }

        public String getMessage() throws Exception{
            return input.readLine();
        }

        public void sendMessage(String msg) throws Exception{
            output.write(msg + "\n");
            output.flush();
        }
}
