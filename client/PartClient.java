package client;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Locale;
import java.util.Scanner;

public class PartClient {
    private static final Scanner sc = new Scanner(System.in);
    private static final Client client = new Client();
    private static boolean running = false;

    private enum Command {
        BIND("Conectar a um repositorio"){
            @Override
            public void run() throws RemoteException{
                System.out.println("Digite o nome do repositorio que deseja se conectar");
                System.out.print("> ");
                String serverName = sc.nextLine();
                try{
                    System.out.println("Procurando o repositorio '" + serverName + "'...");
                    client.bind(serverName);
                    System.out.println("Repositorio '" + serverName + "' conectado!");

                } catch (NotBoundException e){
                    System.out.println("Nenhum repositorio com o nome '" + serverName + "' encontrado");
                }
            }
        },
        LISTP("Listar as peças do repositorio conectado"){
            @Override
            public void run() throws RemoteException, ServerNotSelectedException {
                client.listp();
            }
        },
        GETP("Procurar uma peça por codigo"){
            @Override
            public void run() throws RemoteException, ServerNotSelectedException {
                client.checkServer();
                try {
                    System.out.println("Digite o codigo da Part que deseja buscar");
                    System.out.print("> ");
                    int code = Integer.parseInt(sc.nextLine());
                    client.getp(code);
                } catch (NumberFormatException e) {
                    System.out.println("Codigo invalido digitado: não é um numero.");
                }
            }
        },
        SHOWP("Mostrar os detalhes da peça selecionada"){
            @Override
            public void run() throws PartNotSelectedException {
                client.showp();
            }
        },
        CLEARLIST("Limpa a lista de sub pecas"){
            @Override
            public void run() {
                client.clearlist();
            }
        },
        ADDSUBPART("Adicionar n unidades da peca selecionada na lista de sub pecas"){
            @Override
            public void run() throws PartNotSelectedException {
                client.checkCurrentPart();
                try {
                    System.out.println("Digite a quantidade que deseja adicionar");
                    System.out.print("> ");
                    int qtd = Integer.parseInt(sc.nextLine());
                    client.addsubpart(qtd);
                } catch (NumberFormatException e) {
                    System.out.println("Quantidade invalida digitada: nao eh um numero.");
                }
            }
        },
        ADDP("Adicionar uma nova peca ao repositorio conectado"){
            @Override
            public void run() throws RemoteException, ServerNotSelectedException {
                client.checkServer();

                System.out.println("Digite o nome da peca a ser adicionada");
                System.out.print("> ");
                String name = sc.nextLine();

                System.out.println("Digite a descricao da peca a ser adicionada");
                System.out.print("> ");
                String description = sc.nextLine();

                client.addp(name, description);
            }
        },
        LISTREPO("Lista repositorios disponiveis"){
            @Override
            public void run() throws RemoteException {
                client.listrepo();
            }
        },
        LISTSUB("Lista subpecas a serem adicionadas"){
            @Override
            public void run() {
                client.listsub();
            }
        },
        QUIT("Encerra o programa"){
            @Override
            public void run() {
                running = false;
                sc.close();
                System.out.println("Encerrando cliente...");
                System.out.println("Obrigado por usar o Sistema de Pecas Distribuidas!");
            }
        },
        HELP("Exibe essa mensagem"){
            @Override
            public void run() {
                int maxCommandNameLength = 0;
                int maxCounterLength = String.valueOf(Command.values().length).length();

                for(Command command : Command.values()){
                    int commandNameLength = command.toString().length();
                    if(commandNameLength > maxCommandNameLength){
                        maxCommandNameLength = commandNameLength;
                    }
                }

                int counter = 1;
                for(Command command : Command.values()){
                    String commandName = command.toString().toLowerCase(Locale.ROOT);
                    String spaceBetweenIndexAndCommandName = " ".repeat(maxCounterLength - String.valueOf(counter).length() + 1);
                    String spaceBetweenCommandNameAndDescription = " ".repeat(maxCommandNameLength - commandName.length() + 1);
                    System.out.println(counter
                            + "."
                            + spaceBetweenIndexAndCommandName
                            + commandName
                            + spaceBetweenCommandNameAndDescription
                            + "(" + command.getDescription() + ")");
                    counter++;
                }
            }
        };

        private final String description;

        Command(String description){
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

        public abstract void run() throws RemoteException, ServerNotSelectedException, PartNotSelectedException;
    }

    private static void printWelcomeMessage(){
        String message = " Ola, este eh o Sistema de Pecas Distribuidas! ";

        System.out.println("\n" + "=".repeat(message.length()));
        System.out.println(message);
        System.out.println("=".repeat(message.length()));
    }

    public static void main(String[] args) {
        printWelcomeMessage();

        running = true;

        while (running) {
            try {
                System.out.println("\nDigite o comando que deseja executar");
                System.out.println("Digite 'help' para visualizar os comandos disponiveis");
                System.out.println(client.getSummary());
                System.out.print("> ");

                String inputCommand = sc.nextLine().strip().toUpperCase(Locale.ROOT);
                    Command foundedCommand = Command.valueOf(inputCommand);
                    foundedCommand.run();
            } catch (RemoteException e) {
                System.out.println("Erro ao conectar ao repositorio");
            } catch (IllegalArgumentException e){
                System.out.println("Comando invalido, tente novamente!");
            } catch (ServerNotSelectedException e) {
                System.out.println("Se conecte a um repositorio primeiro com o comando 'bind'!");
            } catch (PartNotSelectedException e) {
                System.out.println("Selecione uma peça primeiro com o comando 'getp'!");
            }
        }
    }
}
