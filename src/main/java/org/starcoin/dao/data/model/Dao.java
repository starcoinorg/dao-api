package org.starcoin.dao.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Dao {

    @Id
    @Column(length = 100, nullable = false)
    private String daoId;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 500, nullable = false)
    private String description;

    @Column(length = 1000)
    private String longDescription;

    @Column(length = 100)
    private String purposeId;

    @Column(length = 200)
    private String tags;

    @Column(length = 200)
    private String communityLinksTwitter;

    @Column(length = 200)
    private String communityLinksDiscord;

    @Column(length = 200)
    private String communityLinksTelegram;

    public String getDaoId() {
        return daoId;
    }

    public void setDaoId(String daoId) {
        this.daoId = daoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getPurposeId() {
        return purposeId;
    }

    public void setPurposeId(String purposeId) {
        this.purposeId = purposeId;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getCommunityLinksTwitter() {
        return communityLinksTwitter;
    }

    public void setCommunityLinksTwitter(String communityLinksTwitter) {
        this.communityLinksTwitter = communityLinksTwitter;
    }

    public String getCommunityLinksDiscord() {
        return communityLinksDiscord;
    }

    public void setCommunityLinksDiscord(String communityLinksDiscord) {
        this.communityLinksDiscord = communityLinksDiscord;
    }

    public String getCommunityLinksTelegram() {
        return communityLinksTelegram;
    }

    public void setCommunityLinksTelegram(String communityLinksTelegram) {
        this.communityLinksTelegram = communityLinksTelegram;
    }
}
