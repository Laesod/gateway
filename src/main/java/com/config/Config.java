package com.config;

/*
 * #%L
 * Gateway
 * %%
 * Copyright (C) 2015 Powered by Sergey
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by aautushk on 11/5/2015.
 */
@Component
public class Config {
    @Value("${gatewayHost}")
    private String gatewayHost;

    @Value("${gatewayPort}")
    private String gatewayPort;

    @Value("${mainAppHost}")
    private String mainAppHost;

    @Value("${mainAppPort}")
    private String mainAppPort;

    public String getGatewayHost() {
        return gatewayHost;
    }

    public void setGatewayHost(String gatewayHost) {
        this.gatewayHost = gatewayHost;
    }

    public String getGatewayPort() {
        return gatewayPort;
    }

    public void setGatewayPort(String gatewayPort) {
        this.gatewayPort = gatewayPort;
    }

    public String getMainAppHost() {
        return mainAppHost;
    }

    public void setMainAppHost(String mainAppHost) {
        this.mainAppHost = mainAppHost;
    }

    public String getMainAppPort() {
        return mainAppPort;
    }

    public void setMainAppPort(String mainAppPort) {
        this.mainAppPort = mainAppPort;
    }
}