package org.example.model;

import java.util.Iterator;

public class Task implements Comparable{
    private int ID;
    private int arrivalTime;
    private int serviceTime;

    public Task(int ID, int arrivalTime, int serviceTime) {
        this.ID = ID;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }
    public int getID() {
        return ID;
    }
    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    @Override
    public int compareTo(Object o) {
        Task task = (Task)o;
        if(this.getArrivalTime() == task.getArrivalTime())
            return Integer.compare(this.getServiceTime(), task.getServiceTime());
        return Integer.compare(this.getArrivalTime(), task.getArrivalTime());

    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }

    @Override
    public String toString() {
        return "Task{" +
                "ID=" + ID +
                ", arrivalTime=" + arrivalTime +
                ", serviceTime=" + serviceTime +
                '}';
    }
}
