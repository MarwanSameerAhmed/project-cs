package com.cs.shop.main_classes;

import android.content.Context;

import com.cs.shop.Interfaces.Filter;
import com.cs.shop.data.DataSetup;

import java.util.ArrayList;

public class ManageClient extends DataSetup {
    private ArrayList<ClientDetails> clients;

    public ManageClient(Context context) {
        super(context);
        clients = super.clients();
    }

    public ArrayList<ClientDetails> getClients() {
        return clients;
    }

    public ArrayList<ClientDetails> getClients(Filter.Clients filter) {
        filter.sort(this.clients);
        return this.clients;
    }


    public ClientDetails searchClient(int clientId){
        for(ClientDetails client : clients)
            if (client.getClientId() == clientId)
                return client;
        return null;
    }

    public ClientDetails searchClient(String name){
        for(ClientDetails client : clients)
            if (client.getName().equals(name))
                return client;
        return null;
    }

    public boolean newClient(ClientDetails client){
        boolean result = super.insert(client);
        refresh();
        return result;
    }

    public boolean updateClient(ClientDetails client){
        boolean result = super.update(client);
        refresh();
        return result;
    }

    public boolean deleteClient(ClientDetails client){
        boolean result = super.delete(client);
        refresh();
        return result;
    }

    public void refresh(){this.clients = super.clients();}
}
