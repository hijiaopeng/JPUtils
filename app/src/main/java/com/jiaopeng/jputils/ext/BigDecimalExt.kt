package com.jiaopeng.jputils.ext

import java.math.BigDecimal

/**
 * 描述：BigDecimal 的扩展函数
 *
 * @author JiaoPeng by 3/3/21
 */
/**
 * 显示BigDecimal去除0之后的字符串
 */
fun BigDecimal?.showNoZeroString(): String {
    val result = (this ?: BigDecimal.ZERO).stripTrailingZeros().toPlainString()
    return if (result == "") "0" else result
}