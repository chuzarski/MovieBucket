package net.chuzarski.moviebucket.network;

/**
 * Created by cody on 3/27/18.
 */

public class UpcomingMoviesParams {

    private String releaseDateRangeFrom;
    private String releaseDateRangeTo;
    private String language;
    private String region;
    private int page;


    private UpcomingMoviesParams(
            String releaseFrom,
            String releaseTo,
            String lang,
            String reg,
            int p
    ) {
        releaseDateRangeFrom = releaseFrom;
        releaseDateRangeTo = releaseTo;
        language = lang;
        region = reg;
        page = p;
    }

    public String getReleaseDateRangeFrom() {
        return releaseDateRangeFrom;
    }

    public String getReleaseDateRangeTo() {
        return releaseDateRangeTo;
    }

    public String getLanguage() {
        return language;
    }

    public String getRegion() {
        return region;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int p) {
        this.page = p;
    }

    public static class Builder {

        private String releaseFrom;
        private String releaseTo;
        private String lang = "en";
        private String reg = "US";
        private int p = 1;

        public Builder(String releaseFrom, String releaseTo) {
            this.releaseFrom = releaseFrom;
            this.releaseTo = releaseTo;
        }

        public Builder setLang(String lang) {
            this.lang = lang;
            return this;
        }

        public Builder setReg(String reg) {
            this.reg = reg;
            return this;
        }

        public Builder setP(int p) {
            this.p = p;
            return this;
        }

        public UpcomingMoviesParams build() {
            return new UpcomingMoviesParams(releaseFrom, releaseTo, lang, reg, p);
        }
    }
}
