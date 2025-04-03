package org.example.buisnessLogic;

import org.example.gui.SimulationInterface;
import org.example.model.Server;
import org.example.model.Task;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
public class SimulationManager {
    private final int numberOfTasks, numberOfQueues;
    private final int[] arrivalTime, serviceTime;
    private List<Task> tasks = new LinkedList<>();
    private List<Server> servers;
    private String strategy;
    private int maxTime, peakHour, counterFull;
    private SimulationInterface simulationInterface;
    private FileWriter fileWriter;
    private static int currentTime;
    private int answerWaitingTime, answerServiceTime;

    public SimulationManager(int nrTasks, int numberOfQueues, int[] arrivalTime, int[] serviceTime, String strategy, int maxTime){
        this.numberOfTasks = nrTasks;
        this.numberOfQueues = numberOfQueues;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
        this.strategy = strategy;
        this.maxTime = maxTime;
        servers = new ArrayList<>(numberOfQueues);
        simulationInterface = new SimulationInterface(maxTime, numberOfQueues, nrTasks, strategy);
        try {
            fileWriter = new FileWriter("output.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void start() throws Exception{
        currentTime = 0;
        fileWriter.append("Strategy: " + strategy + " for Input: NumberOfTasks: " + numberOfTasks+ ", NumberOfQueues: " + numberOfQueues + "\n");
        createServers();
        createTasks();
        beginSimulation();

        fileWriter.flush();
        fileWriter.append ("\nSimulation ended!\n");
        fileWriter.append("Average Waiting: " + ((double)answerWaitingTime/numberOfTasks) + ", Average Service: " + ((double)answerServiceTime/numberOfTasks) + ", Peak Hour: " + peakHour +"\n");
        fileWriter.close();
        setDataInterface(true);
    }

    private void createServers() throws Exception{
        fileWriter.append("Creating Servers.......................\n");
        for(int i=0; i< numberOfQueues; i++) {
            Server server = new Server("Number " + (i + 1), 10);
            servers.add(server);
            server.start();
            fileWriter.append("Server "+ (i+1) +" created......\n");
        }
    }
    private void createTasks() throws Exception{
        fileWriter.append("Creating Tasks.......................\n");
        Random random = new Random();
        for(int i=0; i<numberOfTasks; i++){
            int arrTime= random.nextInt(arrivalTime[1]-arrivalTime[0] + 1) + arrivalTime[0];
            int serTime= random.nextInt(serviceTime[1]-serviceTime[0] + 1) + serviceTime[0];
            tasks.add(new Task(i+1, arrTime, serTime));
        }
        for(Task index : tasks)
            fileWriter.append(index + " created.....\n");
        if(strategy.equals("Time Strategy"))
            Collections.sort(tasks);
        else
            Collections.sort(tasks, Comparator.comparingInt(Task::getArrivalTime));
    }
    private void beginSimulation() throws Exception{
        fileWriter.append("Begin simulation....\n");
        while (currentTime <= maxTime) {
            Thread.sleep(1000);
            currentTime++;
            while (!tasks.isEmpty() && tasks.getFirst().getArrivalTime() <= currentTime) {
                if (strategy.equals("Time Strategy")) {
                    Server server = null;
                    while (server == null) {
                        int minServiceTime = servers.get(0).getCurrentWaitingTime();
                        for (Server index : servers) {
                            if (index.getCurrentWaitingTime() == 0) {
                                server = index;
                                break;
                            } else if (!index.isFull() && index.getCurrentWaitingTime() <= minServiceTime) {
                                minServiceTime = index.getCurrentWaitingTime();
                                server = index;
                            }
                        }
                    }
                    server.addTask(tasks.getFirst());
                    tasks.removeFirst();
                } else {
                    Server server = null;
                    while (server == null) {
                        int min = servers.get(0).getCurrentTasks();
                        for (Server index : servers) {
                            if (index.getCurrentTasks() == 0) {
                                server = index;
                                break;
                            } else if (index.getCurrentTasks() <= min && !index.isFull()) {
                                min = index.getCurrentTasks();
                                server = index;
                            }
                        }
                        server.addTask(tasks.getFirst());
                        tasks.removeFirst();
                    }
                }
            }
            int counter =0;
            for(Server indexCounter: servers) {
                counter += indexCounter.getTasks().size();
                if (counter > counterFull) {
                    peakHour = currentTime;
                    counterFull = counter;
                }
            }

            fileWriter.flush();
            fileWriter.append("\nTime: "+ currentTime + "\n");
            fileWriter.append("Waiting Queue:" + StringOperation.getTasks(tasks));
            fileWriter.append(StringOperation.getQueueStatus(servers));
            setDataInterface(false);
            if(tasks.isEmpty()) {
                int ans = numberOfQueues;
                for (Server index : servers)
                    if (index.getTasks().isEmpty())
                        ans--;
                if(ans == 0)
                    break;
            }
        }
        for(Server index :servers) {
            answerServiceTime += index.getTotalServiceTime();
            answerWaitingTime += index.getTotalWaitingTime();
            index.terminate();
        }

        for(Server index: servers)
            index.join();


    }
    private void setDataInterface(boolean isFinished){
        LinkedList<String> taskStatus = new LinkedList<>();
        for (Iterator<Server> serverIterator = servers.iterator(); serverIterator.hasNext();) {
            Server server = serverIterator.next();
            StringBuilder answer = new StringBuilder("[");

            if(!server.getTasks().isEmpty()) {
                for (Iterator<Task> taskIterator = server.getTasks().iterator(); taskIterator.hasNext(); ) {
                    Task task = taskIterator.next();
                    answer.append(" ").append(task.getID());
                    if (taskIterator.hasNext()) {
                        answer.append(",");
                    }
                }
            }
            answer.append("]");
            taskStatus.add(answer.toString());
        }

        LinkedList<String> status = new LinkedList<>();
        for (Iterator<Server> iterator = servers.iterator(); iterator.hasNext();) {
            Server server = iterator.next();
            if (server.isFull()) {
                status.add("Active");
            } else {
                status.add("Inactive");
            }
        }
        simulationInterface.updateInterface(taskStatus, status, StringOperation.getTasksLog(tasks), tasks.size(), currentTime, isFinished);
    }

    public static int getCurrentTime() {
        return currentTime;
    }
}

