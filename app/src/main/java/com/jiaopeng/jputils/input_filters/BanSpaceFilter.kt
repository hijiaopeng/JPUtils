package com.jiaopeng.jputils.input_filters

import android.text.InputFilter
import android.text.Spanned

/**
 * 描述：禁止输入框输入空格
 *
 * @author JiaoPeng by 3/4/21
 */
class BanSpaceFilter : InputFilter {

    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        return if (source!! == " " || source.toString().contentEquals("\n")) {
            ""
        } else {
            null
        }
    }

}