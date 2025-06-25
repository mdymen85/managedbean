package com.mdymen85.managedbean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MyStartupProcess implements CommandLineRunner {

    private final NewManagedBeanService newManagedBeanService;

    public MyStartupProcess(NewManagedBeanService newManagedBeanService) {
        this.newManagedBeanService = newManagedBeanService;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("MyStartupProcess started");
        while (true) {
            Thread.sleep(1000);
            newManagedBeanService.setSystemMode("NORMAL");
            newManagedBeanService.setFeatureToggleEnabled(true);
            newManagedBeanService.performHealthCheck();
            newManagedBeanService.isFeatureToggleEnabled();
        }

    }
}
