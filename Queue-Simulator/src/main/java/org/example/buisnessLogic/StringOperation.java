package org.example.buisnessLogic;

import org.example.model.Server;
import org.example.model.Task;

import java.util.Iterator;
import java.util.List;

public interface StringOperation {
    public static String getQueueStatus(List<Server> servers){
        int index = 1;
        StringBuilder answer = new StringBuilder();

        for (Iterator<Server> serverIterator = servers.iterator(); serverIterator.hasNext();) {
            Server server = serverIterator.next();
            answer.append("Queue " + index +" :[");
            if(!server.getTasks().isEmpty()) {
                for (Iterator<Task> taskIterator = server.getTasks().iterator(); taskIterator.hasNext(); ) {
                    Task task = taskIterator.next();
                    answer.append("(" + task.getID() + "," + task.getArrivalTime() + "," + task.getServiceTime() + ")");
                    if (taskIterator.hasNext()) {
                        answer.append(",");
                    }
                }
            }
            index++;
            answer.append("]\n");
        }
        return String.valueOf(answer);
    }
    public static String getTasks(List<Task> tasks){
        StringBuilder waitingQueue = new StringBuilder("[ ");
        for (Iterator<Task> iterator = tasks.iterator(); iterator.hasNext();) {
            Task task = iterator.next();
            waitingQueue.append("(" + task.getID() + "," + task.getArrivalTime() + "," + task.getServiceTime() + ")");
            if (iterator.hasNext()) {
                waitingQueue.append(" ");
            }
        }
        waitingQueue.append("]\n");
        return String.valueOf(waitingQueue);
    }
    static String getTasksLog(List<Task> tasks){
        int counter = 0;
        StringBuilder waitingQueue = new StringBuilder("[ ");
        for (Iterator<Task> iterator = tasks.iterator(); iterator.hasNext();) {
            Task task = iterator.next();
            waitingQueue.append(task.getID());
            if(counter % 55 == 0 && counter !=0)
                waitingQueue.append("\n");
            if (iterator.hasNext()) {
                waitingQueue.append(" ");
            }
            counter++;
        }
        waitingQueue.append("]\n");
        return String.valueOf(waitingQueue);
    }
}
