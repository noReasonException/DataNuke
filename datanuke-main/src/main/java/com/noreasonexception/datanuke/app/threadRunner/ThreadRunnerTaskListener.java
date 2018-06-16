package com.noreasonexception.datanuke.app.threadRunner;

public abstract class ThreadRunnerTaskListener  {
    /***
     * Called when a class info read from config file
     */
    public void onClassReadInfo(String classname){}

    /***
     * Called when the AbstractThreadRunner waits until the deadline of class
     */
    public void onClassWaitUntillDeadline(String classname){}

    /***
     * Called when the classloader is loading the class in memory
     */
    public void onClassLoading(String classname){}

    /***
     * Called when the class instance is created
     */
    public void onClassInstanceCreated(String classname){}

    /***
     * Called when the thread is started
     */
    public void onTaskThreadStarted(String classname){}

    /***
     * Called when the new value is retrieved
     */
    public void onTaskThreadValueRetrieved(String classname){}

    /***
     * Called when the task is terminated
     */
    public void onTaskThreadTerminated(String classname){}

    /***
     * Called when the garbage collector releases the thread object
     */
    public void onTaskThreadReleased(String classname){}

    /***
     * Called when the gerbage collector releases the class object
     */
    public void onClassReleased(String classname){}
}
