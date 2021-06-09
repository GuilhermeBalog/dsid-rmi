public interface IClient {
  /**
   * Faz o cliente se conectar a outro servidor
   * e muda o repositório corrente.
   * (Exibir o número de peças)
   */
  void bind(String serverName);

  /**
   * Lista as peças do repositório corrente
   * (Exibir p número de peças)
   */
  void listp();

  /**
   * Busca uma peça por código
   * no repositório corrente
   * e se encontrada, se torna a peça corrente
   */
  Part getp(int code);

  /**
   * Mostra atributos da peça corrente
   * além do nome do repositório que a contém,
   * se é primitiva ou agregada
   */
  void showp();

  /**
   * Esvazia a lista de sub-peças corrente
   */
  void clearlist();

  /**
   * Adiciona n unidades da peça corrente
   * na lista de sub-peças
   */
  void addsubpart();

  /**
   * Adiciona uma peça ao repositório corrente
   * utilizando a lista de sub-peças corrente
   * como conjunto de subcomponentes
   */
  void addp();

  /**
   * Encerra a execução do cliente
   */
  void quit();
}