import java.rmi.Remote;
import java.rmi.RemoteException;

public interface restaurante extends Remote {
    
    String consulta_plato(String id) throws RemoteException;
    int termina_servicio() throws RemoteException;
    String lee_ingrediente(int id) throws RemoteException;
    String escribe_ingrediente(int id, int cantidad) throws RemoteException;
    boolean solicitar_testigo () throws RemoteException;
    void devolver_testigo () throws RemoteException;
}