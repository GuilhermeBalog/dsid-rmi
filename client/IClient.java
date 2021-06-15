package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public interface IClient {
  /**
   * Faz o cliente se conectar a outro servidor e muda o repositório corrente.
   * (Exibir o número de peças)
   */
  void bind(String serverName) throws RemoteException, NotBoundException;

  /**
   * Lista as peças do repositório corrente (Exibir o número de peças)
   */
  void listp() throws RemoteException;

  /**
   * Busca uma peça por código no repositório corrente e se encontrada, se torna a
   * peça corrente
   *
   * @return
   */
  void getp(int code) throws RemoteException;

  /**
   * Mostra atributos da peça corrente além do nome do repositório que a contém,
   * se é primitiva ou agregada
   */
  void showp();

  /**
   * Esvazia a lista de sub-peças corrente
   */
  void clearlist();

  /**
   * Adiciona n unidades da peça corrente na lista de sub-peças
   */
  void addsubpart(Integer qtd);

  /**
   * Adiciona uma peça ao repositório corrente utilizando a lista de sub-peças
   * corrente como conjunto de subcomponentes
   */
  void addp(String nome, String description) throws RemoteException;
}
