package com.matrixdroplet.waterdrop.web.handle;

import com.matrixdroplet.waterdrop.web.view.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by li on 2016/4/15.
 */
public interface Handler {
    View handle(HttpServletRequest request, HttpServletResponse response);
}
