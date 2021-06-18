package server;

import java.util.List;
import java.util.Optional;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PartRepository extends Remote {
	Part add(Part part) throws RemoteException;

	Part findByCode(int code) throws RemoteException;

	List<Part> findAll() throws RemoteException;
}
