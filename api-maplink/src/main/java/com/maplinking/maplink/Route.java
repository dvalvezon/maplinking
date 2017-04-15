package com.maplinking.maplink;

public class Route {

    private Summary summary;

    public Summary getSummary() {
        return summary;
    }

    public void setSummary(Summary summary) {
        this.summary = summary;
    }

    @Override
    public String toString() {
        return "Route{" +
                "summary=" + summary +
                '}';
    }
}
