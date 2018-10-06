package com.tsoft.app.config;

import java.util.HashSet;
import java.util.Set;
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

    
    private static final String CHARACTER_ENCODING = "UTF-8";
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

    /*@Bean
    public ViewResolver htmlViewResolver() {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(myTemplateEngine());
        resolver.setContentType("text/html");
        resolver.setCharacterEncoding(CHARACTER_ENCODING);
        resolver.setViewNames(new String[] { "*.html" });
        return resolver;
    }
    
    @Bean
    public ViewResolver javascriptViewResolver() {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(myTemplateEngine());
        resolver.setContentType("application/javascript");
        resolver.setCharacterEncoding(CHARACTER_ENCODING);
        resolver.setViewNames(new String[] { "*.js" });
        return resolver;
    }
    
    @Bean
    public ViewResolver txtViewResolver() {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(myTemplateEngine());
        resolver.setContentType("text");
        resolver.setCharacterEncoding(CHARACTER_ENCODING);
        resolver.setViewNames(new String[] { "*.txt" });
        return resolver;
    }*/

    
    @Bean
    @Description("Thymeleaf template resolver serving Java Templates")
    public ClassLoaderTemplateResolver javaTemplateResolver() {
        ClassLoaderTemplateResolver javaTemplateResolver = new ClassLoaderTemplateResolver();
        javaTemplateResolver.setPrefix("templates/");
        //javaTemplateResolver.setSuffix(".txt");
        				
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
        //te.addTemplateResolver(emailTemplateResolver());
        
        te.addTemplateResolver(javaTemplateResolver());
        return te;

    }
}
