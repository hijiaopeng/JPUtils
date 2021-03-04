package com.jiaopeng.jputils.input_filters

import android.text.InputFilter
import android.text.Spanned

/**
 * 描述：设置输入框输入小数点后两位
 *
 * @author JiaoPeng by 3/3/21
 */
class DotTwoFilter(val length: Int) : InputFilter {

    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence? {

        // 如果输入的是小数点，那么就认为输入的是0.
        if (source == "." && dest.toString().isEmpty()) {
            return "0."
        }

        // 长度限制12
        if (dest.toString().length >= length) {
            return ""
        }

        // 如果输入的包含小数点，小数点后超过两位则不允许继续输入
        if (dest.toString().contains(".")) {
            val index = dest.toString().indexOf(".")
            val length = dest.toString().substring(index).length

            if (length == 3) {
                // 光标位置小于已输入长度的两位  已经输入的不是0开头的  并且输入的不是0
                if (dstart < dest.toString().length - 2 && dstart > 0 && !dest.toString()
                        .startsWith("0")
                ) {
                    return source
                }
                if (dstart == 0 && source != "0") {
                    return source
                }
                return ""
            }
        }
        // 如果输入的内容是0并且继续输入的不是小数点，那么就不允许继续输入
        if (dest.toString() == "0" && source != ".") {
            // 如果光标在最开始位置 且输入的不是0
            if (dstart == 0 && source != "0") {
                return source
            }
            return ""
        }

        // 如果输入的是0  并且 已经输入的不是空  并且  光标在最开始位置
        return if (source == "0" && dest.toString().isNotEmpty() && dstart == 0) {
            ""
        } else {
            source
        }
        return null
    }

}