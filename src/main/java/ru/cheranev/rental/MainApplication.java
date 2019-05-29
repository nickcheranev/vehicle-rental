package ru.cheranev.rental;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.util.Assert;
import ru.cheranev.rental.service.DemoService;

@SpringBootApplication
public class MainApplication {

    private final DemoService demoService;

    public MainApplication(@Autowired DemoService demoService) {
        this.demoService = demoService;
    }

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @EventListener
    public void handleContextRefreshEvent(ContextRefreshedEvent contextRefreshedEvent) {
        Assert.notNull(demoService);
//        demoService.populateDemoExample();
        demoService.populateVehicleType();
    }
}
