package com.jiaopeng.jputils.input_filters

import android.text.InputFilter
import android.text.Spanned
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * 描述：禁止输入框输入数字
 *
 * @author JiaoPeng by 3/4/21
 */
class BanNumberFilter : InputFilter {

    var num: Pattern = Pattern.compile(
        "[0-9]",
        Pattern.UNICODE_CASE or Pattern.CASE_INSENSITIVE
    )

    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        val numMatcher: Matcher = num.matcher(source)
        if (numMatcher.find()) {
            return ""
        }
        return null
    }

}