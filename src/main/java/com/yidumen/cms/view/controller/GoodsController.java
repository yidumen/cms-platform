package com.yidumen.cms.view.controller;

import com.jfinal.core.Controller;

/**
 * Created by cdm on 15/2/23.
 */
public class GoodsController extends Controller {
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
