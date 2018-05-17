package screenLocker;

public final class LinuxLoader extends Loader {
	private static LinuxLoader _instance;
	public static Loader getInstance() {
		if(_instance == null){
            synchronized(LinuxLoader.class){
                if(_instance == null){
                    _instance = new LinuxLoader();
                }    
            }
        } 
        return _instance;
	}
	@Override
	public boolean loadApplication() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public int loadProgressPercentage() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public String loadStatus() {
		// TODO Auto-generated method stub
		return null;
	}
}
