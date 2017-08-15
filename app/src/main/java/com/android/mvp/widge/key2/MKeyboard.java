package com.android.mvp.widge.key2;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.inputmethodservice.Keyboard;
import android.support.annotation.XmlRes;

/**
 * ================================================
 * 项目名称：MVP1
 * 类 名 称：
 * 创 建 人：zhouchunyu
 * 描    述：
 * 创建时间：2017/8/15 0015  下午 3:16
 * 修改历史：
 * ================================================
 */

public class MKeyboard extends Keyboard {
    public MKeyboard(Context context, int xmlLayoutResId) {
        super(context, xmlLayoutResId);
    }

    public MKeyboard(Context context, @XmlRes int xmlLayoutResId, int modeId, int width, int height) {
        super(context, xmlLayoutResId, modeId, width, height);
    }

    public MKeyboard(Context context, @XmlRes int xmlLayoutResId, int modeId) {
        super(context, xmlLayoutResId, modeId);
    }

    @Override
    protected Key createKeyFromXml(Resources res, Row parent, int x, int y, XmlResourceParser parser) {
        return super.createKeyFromXml(res, parent, x, y, parser);
    }

    @Override
    protected Row createRowFromXml(Resources res, XmlResourceParser parser) {
        return super.createRowFromXml(res, parser);
    }
}
