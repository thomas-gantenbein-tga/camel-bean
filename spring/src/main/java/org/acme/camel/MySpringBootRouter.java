package org.acme.camel;

import org.apache.camel.BeanScope;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class MySpringBootRouter extends RouteBuilder {

    @Override
    public void configure() {
        // will log the same bean for each test1, test2, test3, test4
        from("seda:perRequest")
                .bean(MyNotSpringManagedBean.class, "getHash", BeanScope.Request)
                .log("Processing ----> Exchange: ${header.message}, Bean: ${body}")
                .delay(simple("${random(500)}"))
                .bean(MyNotSpringManagedBean.class, "getHash", BeanScope.Request)
                .log("Done ----------> Exchange: ${header.message}, Bean: ${body}");

        // will log the same bean for each endpoint
        from("seda:singleton")
                .bean(MyNotSpringManagedBean.class, "getHash", BeanScope.Singleton)
                .log("Processing ----> Exchange: ${header.message}, Bean: ${body}")
                .delay(simple("${random(500)}"))
                .bean(MyNotSpringManagedBean.class, "getHash", BeanScope.Singleton)
                .log("Done ----------> Exchange: ${header.message}, Bean: ${body}");

        // will always log a different bean
        from("seda:prototypeUnmanaged")
                .bean(MyNotSpringManagedBean.class, "getHash", BeanScope.Prototype)
                .log("Processing ----> Exchange: ${header.message}, Bean: ${body}")
                .delay(simple("${random(500)}"))
                .bean(MyNotSpringManagedBean.class, "getHash", BeanScope.Prototype)
                .log("Done ----------> Exchange: ${header.message}, Bean: ${body}");

        // will always log a different bean
        from("seda:prototypeManagedScopeExplicit")
                .bean(MySpringManagedBean.class, "getHash", BeanScope.Prototype)
                .log("Processing ----> Exchange: ${header.message}, Bean: ${body}")
                .delay(simple("${random(500)}"))
                .bean(MySpringManagedBean.class, "getHash", BeanScope.Prototype)
                .log("Done ----------> Exchange: ${header.message}, Bean: ${body}");

        // will always log the same bean, because default bean scope in spring is "singleton"
        from("seda:prototypeManagedDefaultScope")
                .bean(MySpringManagedBeanDefaultScope.class, "getHash", BeanScope.Prototype)
                .log("Processing ----> Exchange: ${header.message}, Bean: ${body}")
                .delay(simple("${random(500)}"))
                .bean(MySpringManagedBeanDefaultScope.class, "getHash", BeanScope.Prototype)
                .log("Done ----------> Exchange: ${header.message}, Bean: ${body}");
    }
}
