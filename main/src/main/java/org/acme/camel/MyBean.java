package org.acme.camel;

import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyBean {

    private static final Logger log = LoggerFactory.getLogger(MyBean.class);
    static int instancesCreated = 0;

    public MyBean() {
        log.info("Hello from new bean: {}", this.hashCode());
        instancesCreated++;
    }

    public String getHash(Exchange exchange) {
        return String.valueOf(this.hashCode());
    }

}
