package com.matrixdroplet.waterdrop.ioc.config;

import java.util.Map;

/**
 * Created by li on 2016/4/14.
 */
public class Config {
    private Boolean web;
    private String[] packageNames;
    private String view_pref;
    private String view_suffix;
    private String[] staticFilePaths;
    private Map<String, String> database;

    public Config(Boolean web, String[] packageNames, String view_pref, String view_suffix, String[] staticFilePaths, Map<String, String> database) {
        this.web = web;
        this.packageNames = packageNames;
        this.view_pref = view_pref;
        this.view_suffix = view_suffix;
        this.staticFilePaths = staticFilePaths;
        this.database = database;
    }

    public Boolean getWeb() {
        return web;
    }

    public String[] getPackageNames() {
        return packageNames;
    }

    public String getView_pref() {
        return view_pref;
    }

    public String getView_suffix() {
        return view_suffix;
    }

    public String[] getStaticFilePaths() {
        return staticFilePaths;
    }

    public Map<String, String> getDatabase() {
        return database;
    }
}
