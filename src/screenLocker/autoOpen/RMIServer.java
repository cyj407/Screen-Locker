package screenLocker.autoOpen;

import java.rmi.server.UnicastRemoteObject;

import screenLocker.MyTimer;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.*;

public class RMIServer extends UnicastRemoteObject implements RmiServerIntf {
	private static RMIServer _server;

	public RMIServer() throws RemoteException {
		super(0);
	}

	public int GetRemainTime() {
		try {
			return MyTimer.getLargeTime();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static RMIServer StartServer() {
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
			_server = new RMIServer();
			Naming.rebind("//localhost/ReOpenServer", _server);
		} catch (Exception e) {
			System.out.println("cannot create or rebind new server");
			e.printStackTrace();
		}
		return _server;
	}

	public void CloseServer() {
		try {
			Naming.unbind("//localhost/ReOpenServer");
		} catch (RemoteException | MalformedURLException | NotBoundException e1) {
			e1.printStackTrace();
		}

		try {
			UnicastRemoteObject.unexportObject(this, true);
		} catch (NoSuchObjectException e) {
			e.printStackTrace();
		}
	}
}
