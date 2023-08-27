import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TaskDataModel {

    private static TaskDataModel instance;
    public static TaskDataModel getInstance(){
        if(instance == null){
            instance = new TaskDataModel();
        }
        return instance;
    }
    private List<Task> tasks = new ArrayList<>();
    private List<TaskObserver> observers = new ArrayList<>();

    public void addTask(Task task){
        tasks.add(task);
        Collections.sort(tasks);
        notifyObservers(task);
    }
    public void addObserver(TaskObserver observer){
        observers.add(observer);
    }
    private void notifyObservers(Task task){
        for(TaskObserver observer:observers){
            observer.taskAdded(task);
        }
    }
}
