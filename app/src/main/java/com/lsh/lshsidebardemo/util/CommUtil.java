package com.lsh.lshsidebardemo.util;

import android.support.annotation.Nullable;

/**
 * Created by hua on 2016/10/16.
 */

public class CommUtil {
    public static boolean isEmpty(@Nullable CharSequence str) {
        return str == null || str.length() == 0;
    }
}
