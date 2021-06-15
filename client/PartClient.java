package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Locale;
import java.util.Scanner;

public class PartClient {
    private static final Scanner sc = new Scanner(System.in);
    private static final Client client = new Client();

    private static void availableCommands() {
        System.out.println("1. bind       (Conectar a um servidor)");
        System.out.println("2. listp      (Listar as peças do servidor conectado)");
        System.out.println("3. getp       (Procurar uma peça por código)");
        System.out.println("4. showp      (Mostrar os detalhes da peça selecionada)");
        System.out.println("5. clearlist  (Limpa a lista de sub peças)");
        System.out.println("6. addsubpart (Adicionar n unidades da peça selecionada na lista de sub peças");
        System.out.println("7. addp       (Adicionar uma nova peça ao servidor conectado)");
        System.out.println("8. quit       (Encerra o programa)");
        System.out.println("9. help       (Exibe essa mensagem)");

    }

    private static void bind() throws NotBoundException, RemoteException {
        System.out.println("Digite o nome do servidor que deseja se conectar");
        String serverName = sc.nextLine();
        client.bind(serverName);
    }

    private static void getp() throws RemoteException {
        try {
            System.out.println("Digite o código da Part que deseja buscar");
            int code = Integer.parseInt(sc.nextLine());
            client.getp(code);
        } catch (NumberFormatException e) {
            System.out.println("Código invalido digitado: não é um número.");
        }
    }

    private static void addsubpart() throws RemoteException {
        try {
            System.out.println("Digite a quantidade que deseja adicionar");
            int qtd = Integer.parseInt(sc.nextLine());
            client.addsubpart(qtd);
        } catch (NumberFormatException e) {
            System.out.println("Quantidade invalida digitada: não é um número.");
        }
    }

    private static void addp() throws RemoteException {
        System.out.println("Digite o nome da peça a ser adicionada");
        String name = sc.nextLine();

        System.out.println("Digite a descrição da peça a ser adicionada");
        String description = sc.nextLine();

        client.addp(name, description);
    }

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            try {
                System.out.println("Digite o comando que deseja executar");
                System.out.println("Digite 'help' para visualizar os comandos disponíveis");

                String cmd = sc.nextLine();

                switch (cmd.toLowerCase(Locale.ROOT)) {
                    case "help" -> availableCommands();
                    case "bind" -> bind();
                    case "listp" -> client.listp();
                    case "getp" -> getp();
                    case "showp" -> client.showp();
                    case "clearlist" -> client.clearlist();
                    case "addsubpart" -> addsubpart();
                    case "addp" -> addp();
                    case "quit" -> running = false;
                    default -> System.out.println("Comando invalido, tente novamente!");
                }
            } catch (RemoteException | NotBoundException e) {
                System.out.println("Erro ao conectar ao servidor");
            }
        }
    }
}
