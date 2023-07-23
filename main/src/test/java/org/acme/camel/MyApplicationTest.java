package org.acme.camel;

import java.util.concurrent.TimeUnit;

import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.NotifyBuilder;
import org.apache.camel.test.main.junit5.CamelMainTestSupport;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * A simple unit test showing how to test the application {@link MyApplication}.
 */
class MyApplicationTest extends CamelMainTestSupport {

    @Override
    protected Class<?> getMainClass() {
        return MyApplication.class;
    }

    @AfterEach
    void resetInstanceCount() {
        MyBean.instancesCreated = 0;
    }

    @ParameterizedTest
    @CsvSource({"singleton, 2", "perRequest, 7", "prototype, 12"})
    void beanMainTest(String target, int expectedInstance) throws InterruptedException {
        ProducerTemplate template = context.createProducerTemplate();
        template.sendBodyAndHeader("seda:" + target, "", "message", "test1");
        template.sendBodyAndHeader("seda:" + target, "", "message", "test2");
        template.sendBodyAndHeader("seda:" + target, "", "message", "test3");
        template.sendBodyAndHeader("seda:" + target, "", "message", "test4");

        Thread.sleep(2000);
        template.sendBodyAndHeader("seda:" + target, "", "message", "test5");

        NotifyBuilder notification = new NotifyBuilder(context)
                .from("seda:" + target).whenDone(4)
                .create();
        notification.matches(5, TimeUnit.SECONDS);
        Assertions.assertEquals(expectedInstance, MyBean.instancesCreated);
    }

}
