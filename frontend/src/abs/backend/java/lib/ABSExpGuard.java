package abs.backend.java.lib;

public abstract class ABSExpGuard extends ABSGuard {

	public abstract ABSBool evaluateExp();
	
	
	@Override
	public void await() {
		while (!isTrue()) {
			ABSRuntime.suspend();
		}
	}
	
	@Override
	public boolean isTrue() {
	   return evaluateExp().toBoolean();
	}
}
