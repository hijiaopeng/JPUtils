package com.jiaopeng.jputils.ext

import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import android.R

/**
 * 描述：
 *
 * @author JiaoPeng by 3/4/21
 */
/**
 * 设置图片的着色器
 * PorterDuff.Mode.CLEAR 所绘制不会提交到画布上。
 * PorterDuff.Mode.SRC 显示上层绘制图片
 * PorterDuff.Mode.DST 显示下层绘制图片
 * PorterDuff.Mode.SRC_OVER 正常绘制显示，上下层绘制叠盖。
 * PorterDuff.Mode.DST_OVER 上下层都显示。下层居上显示。
 * PorterDuff.Mode.SRC_IN 取两层绘制交集。显示上层。
 * PorterDuff.Mode.DST_IN 取两层绘制交集。显示下层。
 * PorterDuff.Mode.SRC_OUT 取上层绘制非交集部分。
 * PorterDuff.Mode.DST_OUT 取下层绘制非交集部分。
 * PorterDuff.Mode.SRC_ATOP 取下层非交集部分与上层交集部分
 * PorterDuff.Mode.DST_ATOP 取上层非交集部分与下层交集部分
 * PorterDuff.Mode.XOR 取两层绘制非交集。两层绘制非交集。
 * PorterDuff.Mode.DARKEN 上下层都显示。变暗
 * PorterDuff.Mode.LIGHTEN 上下层都显示。变亮
 * PorterDuff.Mode.MULTIPLY 取两层绘制交集
 * PorterDuff.Mode.SCREEN 上下层都显示。
 */
fun ImageView?.tint(
    @DrawableRes drawableId: Int,
    @ColorRes colorId: Int,
    tintMode: PorterDuff.Mode = PorterDuff.Mode.SRC_IN
) {
    this?.let { imageView ->
        ContextCompat.getDrawable(imageView.context, drawableId)?.let {
            val tintDrawable = DrawableCompat.wrap(it).mutate()
            DrawableCompat.setTintMode(tintDrawable, tintMode)
            DrawableCompat.setTint(tintDrawable, ContextCompat.getColor(imageView.context, colorId))
            imageView.setImageDrawable(tintDrawable)
        }
    }
}

/**
 * 设置选中和没选中的样式
 * val colors = intArrayOf(ContextCompat.getColor(imageView.context, R.color.black), ContextCompat.getColor(imageView.context, colorId))
 */
fun ImageView?.tint4Select(
    @DrawableRes drawableId: Int,
    colors: IntArray,
    tintMode: PorterDuff.Mode = PorterDuff.Mode.SRC_IN
) {
    this?.let { imageView ->
        ContextCompat.getDrawable(imageView.context, drawableId)?.let {
            val tintDrawable = DrawableCompat.wrap(it).mutate()
            DrawableCompat.setTintMode(tintDrawable, tintMode)
            val states = arrayOfNulls<IntArray>(2)
            states[0] = intArrayOf(R.attr.state_selected)
            states[1] = intArrayOf()
            val colorStateList = ColorStateList(states, colors)
            DrawableCompat.setTintList(tintDrawable, colorStateList)
            imageView.setImageDrawable(tintDrawable)
        }
    }
}