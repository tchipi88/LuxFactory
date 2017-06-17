package com.tsoft.app.config;

import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang3.CharEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
public class ThymeleafConfiguration {

    @SuppressWarnings("unused")
    private final Logger log = LoggerFactory.getLogger(ThymeleafConfiguration.class);

//    @Bean
//    @Description("Thymeleaf template resolver serving HTML 5 emails")
//    public ClassLoaderTemplateResolver emailTemplateResolver() {
//        ClassLoaderTemplateResolver emailTemplateResolver = new ClassLoaderTemplateResolver();
//        emailTemplateResolver.setPrefix("mails/");
//        emailTemplateResolver.setSuffix(".html");
//        emailTemplateResolver.setTemplateMode("HTML5");
//        emailTemplateResolver.setCharacterEncoding(CharEncoding.UTF_8);
//        emailTemplateResolver.setOrder(1);
//        return emailTemplateResolver;
//    }

    @Bean
    @Description("Thymeleaf template resolver serving Java Templates")
    public ClassLoaderTemplateResolver javaTemplateResolver() {
        ClassLoaderTemplateResolver javaTemplateResolver = new ClassLoaderTemplateResolver();
        javaTemplateResolver.setPrefix("templates/");
      //  javaTemplateResolver.setSuffix(".txt");
        javaTemplateResolver.setTemplateMode(TemplateMode.TEXT);
        javaTemplateResolver.setCharacterEncoding(org.apache.commons.lang.CharEncoding.UTF_8);
        javaTemplateResolver.setOrder(2);
        Set<String> patterns = new HashSet();
        //patterns.add("Template*");
        javaTemplateResolver.setResolvablePatterns(patterns);
        return javaTemplateResolver;
    }

    @Bean
    public TemplateEngine myTemplateEngine() {
        TemplateEngine te = new SpringTemplateEngine();
      //  te.addTemplateResolver(emailTemplateResolver());
        te.addTemplateResolver(javaTemplateResolver());
        return te;

    }
}
