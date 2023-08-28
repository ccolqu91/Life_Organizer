public class Task implements Comparable<Task>{
    public String name;
    public String startTime;
    public String endTime;
    public String date;

    public Task(String name, String startTime, String endTime, String date){
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
    }


    public int compareTo(Task otherTask){
        int dateComparison = this.date.compareTo(otherTask.date);
        if (dateComparison == 0){
            return this.startTime.compareTo(otherTask.startTime);
        }
        else{
            return dateComparison;
        }
    }


}
