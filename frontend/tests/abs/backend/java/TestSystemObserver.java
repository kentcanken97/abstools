package abs.backend.java;

import abs.backend.java.lib.types.ABSString;
import abs.backend.java.lib.types.ABSValue;
import abs.backend.java.observing.COGView;
import abs.backend.java.observing.EmptyTaskObserver;
import abs.backend.java.observing.GuardView;
import abs.backend.java.observing.ObjectCreationObserver;
import abs.backend.java.observing.ObjectView;
import abs.backend.java.observing.SystemObserver;
import abs.backend.java.observing.TaskObserver;
import abs.backend.java.observing.TaskSchedulerObserver;
import abs.backend.java.observing.TaskView;

public class TestSystemObserver implements SystemObserver, ObjectCreationObserver {

    @Override
    public void systemStarted() {
        System.out.println("SYSTEM STARTED");
    }

    @Override
    public void newCOGCreated(COGView cog, ObjectView initialObject) {
        System.out.println("NEW COG CREATED");
        COGView mainCOG = cog;
        mainCOG.registerObjectCreationListener(this);
        mainCOG.getScheduler().registerTaskSchedulerObserver(new TaskSchedulerObserver() {
            TaskView mainTask;

            @Override
            public void taskCreated(TaskView task) {
                if (mainTask == null) {
                    mainTask = task;
                    mainTask.registerTaskListener(new EmptyTaskObserver() {
                        @Override
                        public void taskFinished(TaskView task) {
                            if (task == mainTask)
                                System.out.println("SYSTEM TERMINATED");
                        }
                    });
                }
                String sourceClass = "INIT";
                if (task.getSource() != null)
                    sourceClass = task.getSource().getClassName();
                System.out.print("TASK CREATED: " + sourceClass + " --> " + task.getTarget().getClassName() + "."
                        + task.getMethodName() + "(");
                int i = 0;
                for (ABSValue v : task.getArgs()) {
                    if (i > 0)
                        System.out.print(", ");
                    System.out.print(v);
                    i++;
                }
                System.out.println(")");
                
                
            }

            @Override
            public void taskReady(TaskView view) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void taskResumed(TaskView runningTask, GuardView view) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void taskSuspended(TaskView task, GuardView guard) {
                // TODO Auto-generated method stub
                
            }
        });

    }

    @Override
    public void objectCreated(ObjectView o) {
        System.out.println("OBJECT CREATED: " + o.getClassName());
    }
    
    @Override
    public void objectInitialized(ObjectView o) {
        if (o.getClassName().equals("FieldClass")) {
            try {
                ABSString s = (ABSString) o.getFieldValue("field");
                System.out.println("FIELD VALUE=" + s.getString());
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
    }

}
