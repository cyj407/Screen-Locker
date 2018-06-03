package screenLocker.autoOpen;

import java.rmi.server.UnicastRemoteObject;

import screenLocker.MyTimer;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.*;

public class RMIServer extends UnicastRemoteObject implements RmiServerIntf {
	public RMIServer() throws RemoteException {
		super(0);
	}

	public int GetRemainTime() {
		try {
			return MyTimer.getLargeTime();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
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
