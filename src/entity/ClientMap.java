package entity;

import other.Server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientMap {
    public Map<Integer, Server.ClientHandler> clientHandlerMap = new HashMap<>();

    public Server.ClientHandler getClientHandler(int id){
        return clientHandlerMap.get(id);
    }

    public List<Server.ClientHandler> getClientHandlerList(List<Integer> idList){
        List<Server.ClientHandler> clientHandlerList = new ArrayList<>();
        for(int a : idList){
            clientHandlerList.add(clientHandlerMap.get(a));
        }
        return clientHandlerList;
    }
}
