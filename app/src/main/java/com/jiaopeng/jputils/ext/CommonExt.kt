package com.jiaopeng.jputils.ext

/**
 * 描述：常用扩展函数
 *
 * @author JiaoPeng by 3/4/21
 */
/**
 * 判断是否为空 并传入相关操作
 */
inline fun <reified T> T?.notNull(notNullAction: (T) -> Unit, nullAction: () -> Unit = {}) {
    if (this != null) {
        notNullAction.invoke(this)
    } else {
        nullAction.invoke()
    }
}