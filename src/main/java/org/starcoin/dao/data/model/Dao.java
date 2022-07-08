package org.starcoin.dao.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Dao implements Serializable {

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

    /**
     * Logo URL of this organization.
     */
    @Column(length = 500)
    private String logoUrl;

    @Column(length = 500)
    private String moreTags;

    @Column
    private Integer sequenceId;

    /**
     * Move(Starcoin) DAO type tag.
     */
    @Column(length = 200)
    private String daoTypeTag;

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

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getMoreTags() {
        return moreTags;
    }

    public void setMoreTags(String moreTags) {
        this.moreTags = moreTags;
    }


    public Integer getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(Integer sequenceId) {
        this.sequenceId = sequenceId;
    }

    public String getDaoTypeTag() {
        return daoTypeTag;
    }

    public void setDaoTypeTag(String daoTypeTag) {
        this.daoTypeTag = daoTypeTag;
    }
}
