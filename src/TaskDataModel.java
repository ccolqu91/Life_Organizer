import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    public List<Task> getTasksForDate(LocalDate date){

        List<Task> tasksForDate = new ArrayList<>();
        for (Task task : tasks) {
            if (task.date.equals(date.toString())) {
                tasksForDate.add(task);
            }
        }
        return tasksForDate;
    }
    public List<Task> getSortedTasks() {
        Collections.sort(tasks);
        return tasks;
    }

    public List<Task> getTasksForDateSortedByTime(LocalDate date) {
        List<Task> tasksForDate = new ArrayList<>();

        for (Task task : tasks) {
            if (task.date.equals(date.toString())) {
                tasksForDate.add(task);
            }
        }

        Collections.sort(tasksForDate, new Comparator<Task>() {
            @Override
            public int compare(Task t1, Task t2) {
                return t1.startTime.compareTo(t2.startTime);
            }
        });

        return tasksForDate;
    }
}
