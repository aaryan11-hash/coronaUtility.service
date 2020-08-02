package com.aaryan.coronaUtility.service.Config;

import org.springframework.stereotype.Component;
import org.springframework.util.ErrorHandler;

@Component
public class QueueHandler implements ErrorHandler {
    @Override
    public void handleError(Throwable throwable) {
        System.out.println("error in listner");
        throwable.printStackTrace();
    }
}
