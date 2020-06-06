package entity;

import other.Server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientMap {
    public Map<Integer, Server.ClientHandler> clientHandlerMap = new HashMap<>();
    private Server.ClientHandler clientHandler;
    public Server.ClientHandler getClientHandler(int id){
        return clientHandlerMap.get(id);
    }

    public List<Server.ClientHandler> getClientHandlerList(List<Integer> idList){
        List<Server.ClientHandler> clientHandlerList = new ArrayList<>();
        for(int a : idList){
            if((clientHandler = clientHandlerMap.get(a)) != null)
            clientHandlerList.add(clientHandler);
        }
        return clientHandlerList;
    }
}
