package abs.backend.java.lib.runtime;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ABSThreadManager {
    private static Logger logger = Logging.getLogger(ABSThread.class.getName());
    private final List<ABSThread> threads = new ArrayList<ABSThread>();
    private final ABSRuntime runtime;
    ABSThreadManager(ABSRuntime r) {
        runtime = r;
    }
    
    synchronized void addThread(ABSThread t) {
        threads.add(t);
        if (logger.isLoggable(Level.FINEST)) logger.finest("Added thread "+t);
    }
    
    synchronized void removeThread(ABSThread t) {
        threads.remove(t);
        if (logger.isLoggable(Level.FINEST)) logger.finest("Removed thread "+t);
        if (threads.isEmpty()) {
            runtime.systemFinished();
        }
    }
    
    synchronized void shutdownAllThreads() {
        for (ABSThread t : threads) {
            t.shutdown();
        }
    }
}
