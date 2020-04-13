package com.example.asyncmethod.controller;

import com.example.asyncmethod.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@RestController
public class AsyncController {

    @Autowired
    private AsyncService service;

    private Map<String, Object> result(Boolean success, Object message) {
        Map<String, Object> result = new HashMap<>();
        result.put("success", success);
        result.put("message", message);
        return result;
    }

    @ResponseBody
    @GetMapping("/list")
    public String listTask() {
        StringBuffer Result = new StringBuffer("<p>运行任务：</p>");
        Set<String> taskNames = service.getTaskNames();
        synchronized (taskNames) {
            Iterator i = taskNames.iterator();
            while (i.hasNext())
            {
                Result.append("<p>");
                Result.append(i.next().toString());
                Result.append("</p>");
            }
        }
        return Result.toString();
    }

    @ResponseBody
    @GetMapping("/start")
    public Map<String, Object> startTask(String task) throws Exception {
        if (service.existsTask(task))
            return result(true, String.format("[%s]任务正在运行！", task));
        service.startTask(task);
        return result(true, String.format("[%s]任务启动成功！", task));
    }

    @ResponseBody
    @GetMapping("/stop")
    public Map<String, Object> stopTask(String task) {
        if (!service.existsTask(task))
            return result(true, String.format("[%s]任务不存在！", task));
        service.stopTask(task);
        return result(true, String.format("[%s]任务停止成功！", task));
    }

    @GetMapping("/clear")
    public void clearTask() {
        service.clearTask();
    }
}
