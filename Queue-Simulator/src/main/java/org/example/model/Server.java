package org.example.model;

import org.example.buisnessLogic.SimulationManager;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class Server extends Thread {
    private Queue<Task> tasks = new LinkedList<>();
    private String nameOfQueue;
    private int totalServiceTime;
    private long totalWaitingTime;
    private boolean running = true;
    private int capacity, currentTasks, currentWaitingTime;
    public Server(String nameOfQueue, int capacity) {
        this.nameOfQueue = nameOfQueue;
        this.capacity = capacity;
    }

    public synchronized void addTask(Task task) {
        tasks.add(task);
        this.currentTasks++;
        totalServiceTime += task.getServiceTime();
        currentWaitingTime+= task.getServiceTime();
        notify();
    }

    public synchronized Queue<Task> getTasks() {
        return tasks;
    }

    public synchronized int getTotalServiceTime() {
        return this.totalServiceTime;
    }

    public synchronized long getTotalWaitingTime(){
        return this.totalWaitingTime;
    }

    public synchronized boolean isFull() {
        return currentTasks == capacity;
    }

    public synchronized int getCurrentTasks() {
        return currentTasks;
    }

    public synchronized int getCurrentWaitingTime() {
        return currentWaitingTime;
    }

    public void terminate() {
        running = false;
        synchronized (this) {
            this.notifyAll();
        }
    }

    private synchronized Task nextClient() throws InterruptedException {
        while (tasks.isEmpty() && running) {
            wait();
        }
        return tasks.peek();
    }
    private synchronized void removeTask() {
        if (!tasks.isEmpty()) {
            Task removedTask = tasks.poll();
            this.currentTasks--;
            currentWaitingTime -= removedTask.getServiceTime();
        }
    }

    @Override
    public void run() {
        try {
            while (running) {
                Task client = nextClient();
                if (client != null) {
                    totalWaitingTime= totalWaitingTime + (SimulationManager.getCurrentTime() - client.getArrivalTime());
                    System.out.println("Start: Client nr. " + client.getID() + " in queue " + nameOfQueue);
                    while(client.getServiceTime()!=0) {
                        Thread.sleep(1000L);
                        client.setServiceTime(client.getServiceTime() - 1);
                    }
                    System.out.println("Leave: Client nr. " + client.getID() + " from " + nameOfQueue);
                    removeTask();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
