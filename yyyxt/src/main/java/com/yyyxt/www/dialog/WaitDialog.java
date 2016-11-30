package com.yyyxt.www.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.Window;


/**
 * Created in Oct 23, 2015 1:19:04 PM.
 *
 * @author Yan Zhenjie.
 */
public class WaitDialog extends ProgressDialog {

    public WaitDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCanceledOnTouchOutside(false);
        setProgressStyle(STYLE_SPINNER);
        setMessage("loading...");
//        setMessage(context.getText(R.string.loading));
    }

}
