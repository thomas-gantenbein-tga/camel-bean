package org.acme.camel;

import org.apache.camel.Exchange;

public class MyBean {

    public MyBean() {
    }

    public String getHash() {
        return String.valueOf(this.hashCode());
    }

}
