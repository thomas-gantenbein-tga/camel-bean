package org.acme.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.builder.NotifyBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

@SpringBootTest
@CamelSpringBootTest
public class MySpringBootApplicationTest {

	@Autowired
	private CamelContext camelContext;

	@Autowired
	private ProducerTemplate producerTemplate;

	@ParameterizedTest
	@ValueSource(strings = {"singleton", "perRequest", "prototypeUnmanaged", "prototypeManagedScopeExplicit", "prototypeManagedDefaultScope"})
	void beanSpringTest(String target) {
		producerTemplate.sendBodyAndHeader("seda:" + target, "", "message", "test1");
		producerTemplate.sendBodyAndHeader("seda:" + target, "", "message", "test2");
		producerTemplate.sendBodyAndHeader("seda:" + target, "", "message", "test3");
		producerTemplate.sendBodyAndHeader("seda:" + target, "", "message", "test4");

		NotifyBuilder notification = new NotifyBuilder(camelContext)
				.from("seda:" + target).whenDone(4)
				.create();
		notification.matches(5, TimeUnit.SECONDS);
	}
}
