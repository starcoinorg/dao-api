package org.starcoin.dao.vo;

public class DaoDescriptor {

    private String language;

    private String description;

    private String longDescription;

    private String tags;

    private String communityLinksTwitter;

    private String communityLinksDiscord;

    private String communityLinksTelegram;

    /**
     * Logo URL of this organization.
     */
    private String logoUrl;

    private String moreTags;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
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
}
