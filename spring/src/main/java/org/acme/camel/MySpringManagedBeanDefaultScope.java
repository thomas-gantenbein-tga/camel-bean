package org.acme.camel;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
public class MySpringManagedBeanDefaultScope {


    public String getHash() {
        return String.valueOf(this.hashCode());
    }

}
