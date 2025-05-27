package br.com.schumaker.force.app.service;

import br.com.schumaker.force.framework.ioc.annotations.Scheduled;
import br.com.schumaker.force.framework.ioc.annotations.bean.Service;

@Service
public class Scheduler {

    @Scheduled(initialDelay = 9)
    public void scheduledTask() {
        System.out.println("Running scheduled task...");
    }
}
