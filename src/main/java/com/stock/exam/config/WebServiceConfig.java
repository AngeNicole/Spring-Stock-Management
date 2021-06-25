package com.stock.exam.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.ws.wsdl.wsdl11.Wsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;



//Enable Spring Web Services
@EnableWs
//Spring Configuration
@Configuration
public class WebServiceConfig{
    // MessageDispatcherServlet
    // ApplicationContext
    // url -> /ws/*
    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext context) {
        MessageDispatcherServlet messageDispatcherServlet = new MessageDispatcherServlet();
        messageDispatcherServlet.setApplicationContext(context);
        messageDispatcherServlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(messageDispatcherServlet, "/ws/nicole/*");
    }
    // /ws/school/supplier.wsdl
    // supplier-details.xsd
    @Bean(name = "suppliers")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema coursesSchema) {
        DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
        definition.setPortTypeName("SuppliersPort");
        definition.setTargetNamespace("http://exam.stock.com/suppliers");
        definition.setLocationUri("/ws/nicole");
        definition.setSchema(coursesSchema);
        return definition;
    }

    @Bean
    public XsdSchema suppliersSchema() {
        return new SimpleXsdSchema(new ClassPathResource("supplier-details.xsd"));
    }

    // course-details.xsd
    @Bean(name = "items")
    public Wsdl11Definition studentDefinition(XsdSchema studentsSchema) {
        DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
        definition.setPortTypeName("ItemsPort");
        definition.setTargetNamespace("http://exam.stock.com/items");
        definition.setLocationUri("/ws/nicole");
        definition.setSchema(studentsSchema);
        return definition;
    }
    @Bean
    public XsdSchema itemsSchema() {
        return new SimpleXsdSchema(new ClassPathResource("item-details.xsd"));
    }

}