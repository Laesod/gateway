package com.rest;

import com.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

/**
 * Created by aautushk on 11/5/2015.
 */
@Component
@RestController
@RequestMapping("/gateway")
public class ConfigRest {

    @Autowired
    private Config configBean;

    @RequestMapping(value = "/config", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Config getConfig(){
         return configBean;
    }
}
