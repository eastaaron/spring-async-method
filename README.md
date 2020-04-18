### 配置异步线程

```
@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {
        // 定义线程池
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        // 核心线程数
        taskExecutor.setCorePoolSize(10);
        // 线程池最大线程数
        taskExecutor.setMaxPoolSize(30);
        // 线程队列最大线程数
        taskExecutor.setQueueCapacity(2000);
        // 初始化
        taskExecutor.initialize();
        return taskExecutor;
    }
}
```

### 定义异步方法

```
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

```

### 启动程序测试
