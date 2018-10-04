package net.chuzarski.moviebucket.repository;

public class ListingConfiguration {
    private int feedType;
    private String isoLanguage;
    private String isoRegion;
    private String searchQuery;

    public void setFeedType(int type) {
        feedType = type;
    }

    public int getFeedType() {
        return feedType;
    }

    public String getIsoLanguage() {
        return isoLanguage;
    }

    public void setIsoLanguage(String isoLanguage) {
        this.isoLanguage = isoLanguage;
    }

    public String getIsoRegion() {
        return isoRegion;
    }

    public void setIsoRegion(String isoRegion) {
        this.isoRegion = isoRegion;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }
}
