package org.starcoin.dao.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.starcoin.dao.utils.JsonRpcClient;
import org.starcoin.utils.StarcoinClient;

import java.net.MalformedURLException;

@Configuration
public class StarcoinClientConfig {

    @Bean
    public StarcoinClient starcoinClient(@Value("${starcoin.json-rpc-url}") String jsonRpcUrl,
                                         @Value("${starcoin.chain-id}") Integer chainId) throws MalformedURLException {
        return new StarcoinClient(jsonRpcUrl, chainId);
    }

}
