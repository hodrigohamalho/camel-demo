package org.acme;

import org.acme.helpers.OrderService;
import org.apache.camel.builder.RouteBuilder;

public class HelloRouter extends RouteBuilder{

    @Override
    public void configure() throws Exception {
        
        from("timer:live-demo?repeatCount=2")
            .log("first camel application")
            .bean(OrderService.class, "generateOrder")
            .log("Pedido gerado ${body}")
            .choice()
                .when(simple("${body.item} == 'Camel'"))
                    .log("Book tipo Camel")
                    .marshal().json()
                    .to("file:/tmp/demo?fileName=camel.json")
                .otherwise()
                    .log("Book tipo ActiveMQ")
                    .marshal().jacksonxml()
                    .to("file:/tmp/demo?fileName=activemq.xml");
        
    }
    
}
