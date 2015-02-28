package com.yidumen.cms.controller;

import com.jfinal.core.Controller;

/**
 * Created by cdm on 15/2/23.
 */
public final class GoodsController extends Controller {
    public void info() {
        render("info.html");
    }
    public void process() {
        render("process.html");
    }
    public void trash() {
        render("trash.html");
    }
}
