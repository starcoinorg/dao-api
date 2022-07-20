package org.starcoin.dao.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Dao implements Serializable {

    public static final String OFF_CHAIN_DAO_ID_PREFIX = "o_";

    /**
     * Add Off-chain prefix to the dao id.
     *
     * @param id Raw dao id without prefix.
     * @return Dao id with off-chain prefix.
     */
    public static String offChainDaoIdWithPrefix(String id) {
        return OFF_CHAIN_DAO_ID_PREFIX + id;
    }

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

    @Column(length = 100)
    private String onChainAddress;

    @Column
    private Boolean deactivated;

    @Column(length = 70)
    private String createdBy;

    @Column(length = 70)
    private String updatedBy;

    @Column
    private Long createdAt;

    @Column
    private Long updatedAt;

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

    public String getOnChainAddress() {
        return onChainAddress;
    }

    public void setOnChainAddress(String onChainAddress) {
        this.onChainAddress = onChainAddress;
    }

    public Boolean getDeactivated() {
        return deactivated;
    }

    public void setDeactivated(Boolean deactivated) {
        this.deactivated = deactivated;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }
}
