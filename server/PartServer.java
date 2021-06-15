package server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class PartServer {
    public static void main(String[] args){
        try {
            PartRepository server = new PartRepositoryImpl();
            PartRepository stub = (PartRepository) UnicastRemoteObject.exportObject(server, 0);

            Registry registry = LocateRegistry.createRegistry(15000);
            registry.bind(args[0], stub);

            System.out.println("Servidor pronto!");
        } catch (Exception e) {
            System.err.println("Exceção no servidor: " + e);
            e.printStackTrace();
        }
    }
}
