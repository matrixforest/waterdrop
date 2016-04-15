package com.matrixdroplet.waterdrop.web.view;

/**
 * Created by li on 2016/4/14.
 */
public class View {
    private String view;
    private boolean isStaticFile;

    public View(String view){
        this.view=view;
        this.isStaticFile=false;
    }

    public View(String view,boolean isStaticFile){
        this.view=view;
        this.isStaticFile=isStaticFile;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public void setIsStaticFile(boolean isStaticFile) {
        this.isStaticFile = isStaticFile;
    }

    public boolean getIsStaticFile() {
        return isStaticFile;
    }
}
