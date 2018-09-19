package net.chuzarski.moviebucket.common;

import java.util.Observable;

public class FeedListingCriteria extends Observable {
    private int feedType;
    private String isoLanguage;
    private String isoRegion;

    public void setFeedType(int type) {
        feedType = type;
        onChanged();
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

    private void onChanged() {
        setChanged();
        notifyObservers();
    }
}
