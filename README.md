### �����첽�߳�

```
@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {
        // �����̳߳�
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        // �����߳���
        taskExecutor.setCorePoolSize(10);
        // �̳߳�����߳���
        taskExecutor.setMaxPoolSize(30);
        // �̶߳�������߳���
        taskExecutor.setQueueCapacity(2000);
        // ��ʼ��
        taskExecutor.initialize();
        return taskExecutor;
    }
}
```

### �����첽����

```
    @Async
    public void startTask(String taskName) throws InterruptedException {
        taskList.add(taskName);
        while (true)
        {
            logger.info(String.format("[%s]��������ִ�У��߳�����:%s", taskName, Thread.currentThread().getName()));
            Thread.sleep(2000L);
            if (!taskList.contains(taskName))
            {
                logger.info(String.format("[%s]����������߳�����:%s", taskName, Thread.currentThread().getName()));
                return;
            }
        }
    }

```

### �����������
