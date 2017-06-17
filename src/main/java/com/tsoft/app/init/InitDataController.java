/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.app.init;


import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author tchipi
 */
@RestController
@RequestMapping(value = "/api")
@Transactional
public class InitDataController {

    @GetMapping("/init")
    String initApp(HttpServletRequest req) throws Exception {
        WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(req.getServletContext());
        for (Map.Entry<String, RealData> ac : wac.getBeansOfType(RealData.class).entrySet()) {
            ac.getValue().populateData(req);
        }
        return "Initialisation Terminée";
    }

    @GetMapping("/demo")
    String demoApp(HttpServletRequest req) throws Exception {
        initApp(req);
        WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(req.getServletContext());
        for (Map.Entry<String, DemoData> ac : wac.getBeansOfType(DemoData.class).entrySet()) {
            ac.getValue().populateData(req);
        }
        return "Initialisation Terminée";
    }

}
