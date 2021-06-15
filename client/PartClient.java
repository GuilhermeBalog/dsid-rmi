package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class PartClient {
    public static void main(String[] args) {
        Client client = new Client();

        try{
            //TODO: Finalizar implementacao, falando com o usuário via linha de comando
            //TODO: Fazer quit!
            client.bind(args[0]);
            client.listp();
        }
        catch(RemoteException | NotBoundException e){
            e.printStackTrace();
        }
    }
}
