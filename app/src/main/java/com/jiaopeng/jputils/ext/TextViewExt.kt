package com.jiaopeng.jputils.ext

import android.graphics.drawable.Drawable
import android.text.Html
import android.widget.TextView
import com.blankj.utilcode.util.ResourceUtils
import com.blankj.utilcode.util.SizeUtils

/**
 * 描述：TextView 的扩展函数
 *
 * @author JiaoPeng by 3/3/21
 */
/**
 * 设置TextView的左侧图标,不包含文字
 */
fun TextView.setStartDrawableImage(res: Int,
                                   bounds: Int = SizeUtils.dp2px(16F),
                                   clearPadding: Boolean = false,
                                   padding: Int = SizeUtils.dp2px(20F)
) {
    this.text = ""
    val drawable = ResourceUtils.getDrawable(res)
    drawable.setBounds(0, 0, bounds, bounds)
    if (clearPadding) {
        this.setPadding(padding, 0, 0, 0)
    }
    this.setCompoundDrawables(drawable, null, null, null)
}

/**
 * 设置TextView的左侧图标,包含文字
 */
fun TextView.setStartDrawableImageAndText(res: Int, text: String, bounds: Int = SizeUtils.dp2px(16F), drawablePadding: Float = 8F) {
    this.text = text
    val drawable = ResourceUtils.getDrawable(res)
    drawable.setBounds(0, 0, bounds, bounds)
    this.setCompoundDrawables(drawable, null, null, null)
    this.compoundDrawablePadding = SizeUtils.dp2px(drawablePadding)
}


/**
 * 设置TextView的左侧图标,包含文字
 */
fun TextView.setStartDrawableImageAndText(drawable: Drawable, text: String, bounds: Int = SizeUtils.dp2px(20F), drawablePadding: Float = 8F) {
    this.text = text
    drawable.setBounds(0, 0, bounds, bounds)
    this.setCompoundDrawables(drawable, null, null, null)
    this.compoundDrawablePadding = SizeUtils.dp2px(drawablePadding)
}

/**
 * 设置TextView的右侧图标,不包含文字
 */
fun TextView.setEndDrawableImage(res: Int,
                                 bounds: Int = SizeUtils.dp2px(16F),
                                 clearPadding: Boolean = false,
                                 padding: Int = SizeUtils.dp2px(20F)) {
    this.text = ""
    val drawable = ResourceUtils.getDrawable(res)
    drawable.setBounds(0, 0, bounds, bounds)
    if (clearPadding) {
        this.setPadding(0, 0, padding, 0)
    }
    this.setCompoundDrawables(null, null, drawable, null)
}

/**
 * 设置TextView的右侧图标,包含文字
 */
fun TextView.setEndDrawableImageAndText(res: Int, text: String, bounds: Int = SizeUtils.dp2px(16F), drawablePadding: Float = 8F) {
    this.text = text
    val drawable = ResourceUtils.getDrawable(res)
    drawable.setBounds(0, 0, bounds, bounds)
    this.setCompoundDrawables(null, null, drawable, null)
    this.compoundDrawablePadding = SizeUtils.dp2px(drawablePadding)
}

/**
 * 设置TextView的右侧文字
 */
fun TextView.setEndText(text: String, color: Int, padding: Int = SizeUtils.dp2px(20F)) {
    this.minHeight = 0
    this.minWidth = 0
    this.minimumHeight = 0
    this.minimumWidth = 0
    this.text = text
    this.setTextColor(color)
    this.setCompoundDrawables(null, null, null, null)
    this.setPadding(0, 0, padding, 0)
}

/**
 * 设置 TextView 值
 */
fun TextView.text(charSequence: CharSequence?) {
    this.text = charSequence ?: ""
}

/**
 * 设置 TextView Html 标签值
 */
fun TextView.text4Html(charSequence: String?) {
    this.text = Html.fromHtml(charSequence)
}