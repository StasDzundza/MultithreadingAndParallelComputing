public class BearFinder implements Runnable {
    private TasksCollection tasksCollection;
    public BearFinder(TasksCollection taskCollection){
        this.tasksCollection = taskCollection;
    }
    @Override
    public void run() {
        while (true){
            int[]task = tasksCollection.getTask();
            if(task == null)
                return;

            for(int i = 0;i < task.length;i++){
                if(task[i]==1) {
                    tasksCollection.setFound(true);
                    System.out.println("Bear found in column " + i);
                    return;
                }
            }
        }
    }
}
