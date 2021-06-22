package server;

import java.rmi.AlreadyBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class PartServer {
    public static final int PORT = 15000;

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

            Registry registry = LocateRegistry.getRegistry(PORT);

            try {
                registry.bind("#donotusethisnameTHANKS", stub);
                registry.unbind("#donotusethisnameTHANKS");
            }
            catch (Exception e) {
                registry = LocateRegistry.createRegistry(PORT);
            }

            try {
                registry.bind(serverName, stub);
                System.out.println("Servidor '" + serverName + "' pronto!");
            }
            catch (AlreadyBoundException e) {
                System.out.println("Já existe um servidor com este nome. Por favor escolha outro.");
                System.exit(0);
            }
        } catch (Exception e) {
            System.err.println("Exceção no servidor: " + e);
            e.printStackTrace();
        }
    }
}
