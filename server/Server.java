public class Server {
	public static void main(String[] args){
		PartRepository server = new PartRepositoryImpl();
		PartRepository stub = (PartRepository) UnicastRemoteObject
  .exportObject((PartRepository) server, 0)
	}
}
