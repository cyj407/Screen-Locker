package autoOpen;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RmiServerIntf extends Remote{
	public long RMI_getRemainTime() throws RemoteException;
}
