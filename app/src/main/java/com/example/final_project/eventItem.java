package com.example.final_project;

public class eventItem {
    private String mInfo;
    private String mName;
    private String mLocation;
    private String mSDescription;

    public eventItem(String info_url, String name, String location_extra_info, String short_description) {
        mInfo = info_url;
        mName = name;
        mLocation = location_extra_info;
        mSDescription = short_description;
    }

    public String getInfo() {
        return mInfo;
    }

    public String getName() {
        return mName;
    }

    public String getLocation() {
        return mLocation;
    }

    public String getSDescription() {
        return mSDescription;
    }

}
