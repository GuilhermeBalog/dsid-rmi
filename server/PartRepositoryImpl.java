package server;

import java.util.List;
import java.util.Optional;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class PartRepositoryImpl implements PartRepository {
	private List<Part> parts;

	@Override
	public Part add(Part part){
		parts.add(part)
		return part
	}

	@Override
	public Optional<Part> findByCode(int code) {
		for(Part part : parts) {
			if(part.getCode() == code){
				return Optional.of(part);
			}
		}

		return Optional.empty();
	}

	@Override
	public List<Part> findAll(){
		return parts;
	}

	@Override
	public List<Part> findAll(PartRepository repository){
		return repository.findAll();
	}

	/**
	 * Inicia o servidor
	 */
	public static void main(String[] args){
		try {
			PartRepository server = new PartRepositoryImpl();
			PartRepository stub = (PartRepository) UnicastRemoteObject.exportObject(server, 0);

			// Bind the remote object's stub in the registry
			Registry registry = LocateRegistry.getRegistry();
			registry.bind("Hello", stub);

			System.out.println("Servidor pronto!");
		} catch (Exception e) {
			System.err.println("Exceção no servidor: " + e.toString());
			e.printStackTrace();
		}
	}
}
