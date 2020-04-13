package com.example.asyncmethod.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AsyncService {
    private static final Logger logger = LoggerFactory.getLogger(AsyncService.class);

    private static Set<String> taskList = Collections.synchronizedSet(new HashSet<String>());
    public boolean existsTask(String taskName)
    {
        return taskList.contains(taskName);
    }

    public boolean stopTask(String taskName)
    {
        return taskList.remove(taskName);
    }

    public Set<String> getTaskNames()  { return taskList;  }

    @Async
    public void startTask(String taskName) throws InterruptedException {
        taskList.add(taskName);
        while (true)
        {
            logger.info(String.format("[%s]任务正在执行，线程名称:%s", taskName, Thread.currentThread().getName()));
            Thread.sleep(2000L);
            if (!taskList.contains(taskName))
            {
                logger.info(String.format("[%s]任务结束，线程名称:%s", taskName, Thread.currentThread().getName()));
                return;
            }
        }
    }

    public void clearTask()
    {
        taskList.clear();
    }
}
