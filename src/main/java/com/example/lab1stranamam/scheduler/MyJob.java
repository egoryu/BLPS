package com.example.lab1stranamam.scheduler;

import com.example.lab1stranamam.service.CleanService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@Slf4j
@AllArgsConstructor
public class MyJob implements Job {
    private final CleanService cleanService;
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        cleanService.cleanDeleteMessage();
        log.info("Задача выполнена: " + new java.util.Date());
    }
}
