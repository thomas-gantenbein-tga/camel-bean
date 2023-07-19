package org.acme.camel;

import org.apache.camel.BeanScope;
import org.apache.camel.builder.RouteBuilder;

public class MyRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        // will log the same bean for each test1, test2, test3, test4
        from("seda:perRequest")
            .bean(MyBean.class, "getHash", BeanScope.Request)
            .log("Processing ----> Exchange: ${header.message}, Bean: ${body}")
            .delay(simple("${random(500)}"))
            .bean(MyBean.class, "getHash", BeanScope.Request)
            .log("Done ----------> Exchange: ${header.message}, Bean: ${body}");

        // will log the same bean for each endpoint
        from("seda:singleton")
            .bean(MyBean.class, "getHash", BeanScope.Singleton)
            .log("Processing ----> Exchange: ${header.message}, Bean: ${body}")
            .delay(simple("${random(500)}"))
            .bean(MyBean.class, "getHash", BeanScope.Singleton)
            .log("Done ----------> Exchange: ${header.message}, Bean: ${body}");

        // will always log a new bean
        from("seda:prototype")
            .bean(MyBean.class, "getHash", BeanScope.Prototype)
            .log("Processing ----> Exchange: ${header.message}, Bean: ${body}")
            .delay(simple("${random(500)}"))
            .bean(MyBean.class, "getHash", BeanScope.Prototype)
            .log("Done ----------> Exchange: ${header.message}, Bean: ${body}");
    }
}
