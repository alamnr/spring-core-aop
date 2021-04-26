package com.in28Minutes.spring;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Configuration
//@ComponentScan(basePackages = "com.in28Minutes.spring")
class TestContext{
	@Bean(name = "goodMorningServiceImpl")
	public HiService getMorningService() {
		return new GoodMorningServiceImpl();
	}
	@Bean(name = "goodNightServiceImpl")
	public HiService getNightService() {
		return new GoodNightServiceImpl();
	}
}

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"/testContext.xml"})
@ContextConfiguration(classes = TestContext.class)
public class DependencyInjectionExample {

	@Autowired
	@Qualifier("goodNightServiceImpl") 
	private HiService service ;
	
	@Test
	public void dummyTest() {
		
		assertEquals("Good night",service.sayHi());
	}	
}

@Component
//@Primary
class GoodNightServiceImpl implements HiService {
	@Override
	public String sayHi() {		
		return "Good night";
	}
	
}

@Component
class GoodMorningServiceImpl implements HiService {
	public String sayHi() {
		return "Good morning";
	}
}