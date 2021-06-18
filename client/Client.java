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

import static server.PartServer.PORT;

public class Client {
    private PartRepository server;
    private String serverName;
    private Part currentPart;
    private final List<AbstractMap.SimpleEntry<Part, Integer>> subPartsToBeAdded = new ArrayList<>();

    public String getSummary(){
        return "[repositório: " + (serverName != null ? serverName : "nenhum")
                + "; peça atual: " + (currentPart != null? currentPart.getNome() : "nenhum")
                + "; número de sub-peças na lista: " + subPartsToBeAdded.size() + "]";
    }

    public void checkServer() throws ServerNotSelectedException{
        if(server == null || serverName == null){
            throw new ServerNotSelectedException();
        }
    }

    public void checkCurrentPart() throws PartNotSelectedException{
        if(currentPart == null){
            throw new PartNotSelectedException();
        }
    }

    public void bind(String serverName) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(PORT);

        this.server = (PartRepository) registry.lookup(serverName);
        this.serverName = serverName;
    }

    public void listp() throws RemoteException, ServerNotSelectedException {
        checkServer();
        List<Part> parts = server.findAll();

        System.out.println("Listando " + parts.size() + " peças...");

        parts.forEach((part) -> System.out.println(part.getCode() + ". " + part.getNome() + " - " + part.getDescription()));
    }

    public void getp(int code) throws RemoteException, ServerNotSelectedException {
        checkServer();
        Part part = server.findByCode(code);
        if(part != null){
            this.currentPart = part;
            System.out.println("Peça encontrada!");
            System.out.println(this.currentPart);
        } else {
            System.out.println("Nenhuma peça encontrada com o código " + code);
        }
    }

    public void showp() throws PartNotSelectedException {
        checkCurrentPart();
        System.out.println(currentPart.toString());
    }

    public void clearlist() {
        this.subPartsToBeAdded.clear();
        System.out.println("Lista de sub peças esvaziada");
    }

    public void addsubpart(Integer qtd) throws PartNotSelectedException {
        checkCurrentPart();
        this.subPartsToBeAdded.add(new AbstractMap.SimpleEntry<>(currentPart, qtd));
    }

    public void addp(String nome, String description) throws RemoteException, ServerNotSelectedException {
        checkServer();

        Part newPart = new Part(nome, description, subPartsToBeAdded, serverName);
        newPart.setSubParts(this.subPartsToBeAdded);
        Part part = server.add(newPart);

        System.out.println("Peça criada:");
        System.out.println(part.toString());
    }

    public void listrepo() throws RemoteException {
        String[] repositories = LocateRegistry.getRegistry(PORT).list();

        for (String repository: repositories  ) {
            System.out.println(repository);
        }
    }

    public void listsub() {
        StringBuilder subPartsString = new StringBuilder();

        for (AbstractMap.SimpleEntry<Part, Integer> line : subPartsToBeAdded) {
			Part part = line.getKey();
			subPartsString.append("\nCódigo: ").append(part.getCode())
					.append(", nome: ").append(part.getNome())
					.append(", repositório: ").append(part.getServerName())
					.append(", quantidade: ").append(line.getValue());
		}

        System.out.println(subPartsString);
    }

}
