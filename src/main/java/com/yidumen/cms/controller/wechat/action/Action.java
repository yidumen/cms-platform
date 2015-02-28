package com.yidumen.cms.controller.wechat.action;

import com.jfinal.weixin.sdk.msg.in.InMsg;
import com.jfinal.weixin.sdk.msg.out.OutMsg;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
public interface Action {
    OutMsg action(InMsg msg);
}
