package autoOpen;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.*;
import java.util.Timer;
import java.util.TimerTask;

public class RMIServer extends UnicastRemoteObject implements RmiServerIntf {
	private static long lock_time;
	private static long remain_time;

	public RMIServer() throws RemoteException {
		super(0);
	}

	public long RMI_getRemainTime() throws RemoteException {
		return remain_time;
	}

	public static long getRemainTime() {
		return remain_time;
	}

	public long getLockTime() throws RemoteException {
		return lock_time;
	}

	public static void setLockTime(long sec) {
		lock_time = sec;
		remain_time = sec;
	}

	private static void decTime() {
		--remain_time;
	}

	public static void startServer(long sec) {
		/** set the lock_time **/
		setLockTime(sec);

		/** set timer **/
		Timer timer = new Timer();
		TimerTask oneSec = new TimerTask() {
			public void run() {
				decTime();
			}
		};
		timer.schedule(oneSec, 0, 1000);

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
