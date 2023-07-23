package org.acme.camel;

import org.apache.camel.BeanScope;
import org.apache.camel.builder.RouteBuilder;

public class MyRouteBuilder extends RouteBuilder {

    @Override
    public void configure() {
        // will log the same bean for each test1, test2, test3, test4
        from("seda:perRequest")
            .bean(MyBeanObserver.class, "logMyBeanInstanceCount")
            .bean(MyBean.class, "getHash", BeanScope.Request)
            .log("Processing ----> Exchange: ${header.message}, Bean: ${body}")
            .delay(simple("${random(500)}"))
            .bean(MyBean.class, "getHash", BeanScope.Request)
            .bean(MyBeanObserver.class, "logMyBeanInstanceCount")
            .log("Done ----------> Exchange: ${header.message}, Bean: ${body}");

        // will log the same bean for each endpoint
        from("seda:singleton")
            .bean(MyBeanObserver.class, "logMyBeanInstanceCount")
            .bean(MyBean.class, "getHash", BeanScope.Singleton)
            .log("Processing ----> Exchange: ${header.message}, Bean: ${body}")
            .delay(simple("${random(500)}"))
            .bean(MyBean.class, "getHash", BeanScope.Singleton)
            .bean(MyBeanObserver.class, "logMyBeanInstanceCount")
            .log("Done ----------> Exchange: ${header.message}, Bean: ${body}");

        // will always log a new bean
        from("seda:prototype")
            .bean(MyBeanObserver.class, "logMyBeanInstanceCount")
            .bean(MyBean.class, "getHash", BeanScope.Prototype)
            .log("Processing ----> Exchange: ${header.message}, Bean: ${body}")
            .delay(simple("${random(500)}"))
            .bean(MyBean.class, "getHash", BeanScope.Prototype)
            .bean(MyBeanObserver.class, "logMyBeanInstanceCount")
            .log("Done ----------> Exchange: ${header.message}, Bean: ${body}");
    }
}
