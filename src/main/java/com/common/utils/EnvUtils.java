package com.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class EnvUtils {


    private static final Logger logger = LoggerFactory.getLogger(EnvUtils.class);

    @Autowired
    Environment env;


    public  boolean isDevActive(){
        String[] activeProfiles = env.getActiveProfiles();
        for (String profile: activeProfiles) {
            logger.info("profile {"+profile+"},{"+logger.isDebugEnabled()+"}");
            if ("dev".equalsIgnoreCase(profile)){
                return true;
            }
        }
        return false;
    }
}