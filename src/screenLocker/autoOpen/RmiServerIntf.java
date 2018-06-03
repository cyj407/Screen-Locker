package screenLocker.autoOpen;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RmiServerIntf extends Remote{
	public int GetRemainTime() throws RemoteException;
}
