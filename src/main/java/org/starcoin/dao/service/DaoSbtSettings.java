package org.starcoin.dao.service;

public class DaoSbtSettings {
    //identifier-nft-sbt-value-bcs-path
    private String identifierNftSbtValueBcsPath;

    //dao-sbt-resource-struct-tag-format
    private String daoSbtResourceStructTagFormat;

    public String getIdentifierNftSbtValueBcsPath() {
        return identifierNftSbtValueBcsPath;
    }

    public void setIdentifierNftSbtValueBcsPath(String identifierNftSbtValueBcsPath) {
        this.identifierNftSbtValueBcsPath = identifierNftSbtValueBcsPath;
    }

    public String getDaoSbtResourceStructTagFormat() {
        return daoSbtResourceStructTagFormat;
    }

    public void setDaoSbtResourceStructTagFormat(String daoSbtResourceStructTagFormat) {
        this.daoSbtResourceStructTagFormat = daoSbtResourceStructTagFormat;
    }
}
