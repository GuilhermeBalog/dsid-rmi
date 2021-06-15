package client;

import server.Part;
import server.PartRepository;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Client implements IClient {
    private PartRepository server;
    private String serverName;
    private Part currentPart;
    private final List<AbstractMap.SimpleEntry<Part, Integer>> subPartsToBeAdded = new ArrayList<>();

    @Override
    public void bind(String serverName) throws RemoteException, NotBoundException {
        System.out.println("Procurando o servidor " + serverName);
        Registry registry = LocateRegistry.getRegistry(15000);

        this.serverName = serverName;

        System.out.println("Conectando ao servidor " + serverName);
        this.server = (PartRepository) registry.lookup(serverName);
    }

    @Override
    public void listp() throws RemoteException {
        List<Part> parts = server.findAll();

        System.out.println("Listando " + parts.size() + " peças...");

        parts.forEach((part) -> {
            System.out.println(part.getCode() + ". " + part.getNome() + " - " + part.getDescription());
        });
    }

    @Override
    public void getp(int code) throws RemoteException {
        Optional<Part> optPart = server.findByCode(code);

        optPart.ifPresentOrElse(partValue -> this.currentPart = partValue,
                () -> System.out.println("Nenhuma parte encontrada com o código " + code));
    }

    @Override
    public void showp() {
        System.out.println(currentPart.toString());
    }

    @Override
    public void clearlist() {
        this.subPartsToBeAdded.clear();
        System.out.println("Lista de sub peças esvaziada");
    }

    @Override
    public void addsubpart(Integer qtd) {
        this.subPartsToBeAdded.add(new AbstractMap.SimpleEntry<>(currentPart, qtd));
    }

    @Override
    public void addp(String nome, String description) throws RemoteException {
        Part newPart = new Part(nome, description, subPartsToBeAdded, serverName);
        newPart.setSubParts(this.subPartsToBeAdded);
        Part part = server.add(newPart);

        System.out.println("Parte criada:");
        System.out.println(part.toString());
    }
}
