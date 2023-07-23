package org.acme.camel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyBeanObserver {
    private static final Logger log = LoggerFactory.getLogger(MyBeanObserver.class);

    public synchronized void logMyBeanInstanceCount() {
        log.info("Created instances of MyBean: {}", MyBean.instancesCreated);
    }
}
