package xin.codedream.eshop.inventory.request;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 请求内存队列
 *
 * @author LeiXinXin
 * @date 2020/3/25$
 */
public class RequestQueue {
    private static RequestQueue instance;
    private static List<ArrayBlockingQueue<Request>> queues;
    private Map<Integer, Boolean> flagMap = new ConcurrentHashMap<>();

    public static RequestQueue getInstance() {
        return Singleton.getInstance();
    }

    public void addQueue(ArrayBlockingQueue<Request> queue) {
        queues.add(queue);
    }

    public Map<Integer, Boolean> getFlagMap() {
        return flagMap;
    }

    public int getQueueSize() {
        return queues.size();
    }

    public ArrayBlockingQueue<Request> getQueue(int index) {
        return queues.get(index);
    }

    private static class Singleton {
        static {
            queues = new ArrayList<>();
            instance = new RequestQueue();
        }

        public static RequestQueue getInstance() {
            return instance;
        }
    }
}
