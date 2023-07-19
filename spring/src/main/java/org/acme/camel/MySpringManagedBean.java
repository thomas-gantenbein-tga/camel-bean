package org.acme.camel;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "prototype")
public class MySpringManagedBean {


    public String getHash() {
        return String.valueOf(this.hashCode());
    }

}
