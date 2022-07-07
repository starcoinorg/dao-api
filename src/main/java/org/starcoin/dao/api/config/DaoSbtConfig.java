package org.starcoin.dao.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.starcoin.dao.service.DaoSbtSettings;

@Configuration
public class DaoSbtConfig {

    @Bean
    public DaoSbtSettings daoSbtSettings(@Value("${starcoin.dao-sbt-resource-struct-tag-format}") String sbtResourceStructTagFormat,
                                         @Value("${starcoin.identifier-nft-sbt-value-bcs-path}") String identifierNftSbtValueBcsPath) {
        DaoSbtSettings settings = new DaoSbtSettings();
        settings.setDaoSbtResourceStructTagFormat(sbtResourceStructTagFormat);
        settings.setIdentifierNftSbtValueBcsPath(identifierNftSbtValueBcsPath);
        return settings;
    }
}
