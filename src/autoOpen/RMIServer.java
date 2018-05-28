package autoOpen;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.*;
import screenLocker.MyTimer;

public class RMIServer extends UnicastRemoteObject implements RmiServerIntf {
	public RMIServer() throws RemoteException {
		super(0);
	}

	public int GetRemainTime() throws RemoteException {
		return MyTimer.getLargetTime();
	}

	public static void StartServer() {

		/** create Registry **/
		try {
			LocateRegistry.createRegistry(1099);
			System.out.println("created ReOpenServer");
		} catch (RemoteException e) {
			System.out.println("cannot create RMI registry");
			e.printStackTrace();
		}

		/** create Server, this will invoke a new thread **/
		try {
			RMIServer server = new RMIServer();
			Naming.rebind("//localhost/ReOpenServer", server);
		} catch (Exception e) {
			System.out.println("cannot create or rebind new server");
			e.printStackTrace();
		}
	}
}
