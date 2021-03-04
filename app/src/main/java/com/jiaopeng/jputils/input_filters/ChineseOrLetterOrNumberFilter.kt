package com.jiaopeng.jputils.input_filters

import android.text.InputFilter
import android.text.Spanned
import java.util.regex.Pattern

/**
 * 描述：限制只能输入汉字，字母，数字
 *
 * @author JiaoPeng by 3/3/21
 */
class ChineseOrLetterOrNumberFilter : InputFilter {

    override fun filter(
        source: CharSequence,
        start: Int,
        end: Int,
        dest: Spanned,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        val p = Pattern.compile("[0-9|a-zA-Z|\u4e00-\u9fa5]+")
        val m = p.matcher(source.toString())
        return if (!m.matches()) "" else null
    }

}