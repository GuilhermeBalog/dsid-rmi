package server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class PartServer {
    public static void main(String[] args){
        String serverName;

        if(args.length >= 1){
            serverName = args[0];
        } else {
            System.out.println("Forneça um nome para o servidor");
            System.out.print("> ");
            Scanner sc = new Scanner(System.in);
            serverName = sc.nextLine().strip();
            sc.close();
        }

        try {
            PartRepository server = new PartRepositoryImpl();
            PartRepository stub = (PartRepository) UnicastRemoteObject.exportObject(server, 0);

            Registry registry = LocateRegistry.createRegistry(15000);
            registry.bind(serverName, stub);

            System.out.println("Servidor '" + serverName + "' pronto!");
        } catch (Exception e) {
            System.err.println("Exceção no servidor: " + e);
            e.printStackTrace();
        }
    }
}
