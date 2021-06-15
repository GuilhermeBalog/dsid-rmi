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

public class Client implements IClient{
    // TODO: Colocar o nome do server como atributo do server
    private PartRepository server;
    private Part currentPart;
    private final List<AbstractMap.SimpleEntry<Part, Integer>> subPartsToBeAdded = new ArrayList<>();

    @Override
    public void bind(String serverName) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry();

        this.server = (PartRepository) registry.lookup(serverName);
    }

    @Override
    public void listp() throws RemoteException {
        List<Part> parts = server.findAll();
        parts.forEach((part) -> {
            System.out.println(part.toString());
        });
    }

    @Override
    public void getp(int code) throws RemoteException {
        Optional<Part> optPart = server.findByCode(code);

        optPart.ifPresent(partValue -> this.currentPart = partValue);
    }

    @Override
    public void showp(){
        System.out.println(currentPart.toString());
    }

    @Override
    public void clearlist(){
        this.subPartsToBeAdded.clear();
    }

    @Override
    public void addsubpart(Integer qtd){
        this.subPartsToBeAdded.add(new AbstractMap.SimpleEntry<>(currentPart, qtd));
    }

    @Override
    public void addp() throws RemoteException {
        // TODO: Implementar classe Part
        // TODO: Receber por parametros os dados da part
        Part newPart = new Part();
        newPart.setSubParts(this.subPartsToBeAdded);
        server.add(newPart);
    }

    //TODO: Garantir que nao existe um método quitServer ou algo assim
}
