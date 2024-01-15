package com.AlertSystem.backendSiteDiplom.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MyLogger {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(
            "MyLogger");
    public void sendDebug(String message) {
        logger.debug(message);
    }
    public void sendInfo(String message) {
        logger.info(message);
//        logger.info("message {}, {}, {}", 1, 2, 3);
    }
    public void sendWarn(String message) {
        logger.warn(message);
    }
    public void sendError(String message) {
        logger.error(message);
    }
}
