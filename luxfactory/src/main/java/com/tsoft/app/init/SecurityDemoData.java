/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.app.init;

import com.tsoft.app.service.UserService;
import javax.servlet.http.HttpServletRequest;
import org.fluttercode.datafactory.impl.DataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author tchipi
 */
@Component
public class SecurityDemoData implements DemoData {

    @Autowired
    UserService UserService;

    @Override
    public void populateData(HttpServletRequest req) throws Exception {
        DataFactory df = new DataFactory();

        for (int i = 0; i < 15; i++) {
            String login = df.getName();
           // UserService.createUser(login, login, df.getFirstName(), df.getLastName(), df.getEmailAddress());

        }

    }

}
