package com.jiaopeng.jputils.utils

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import android.view.View.MeasureSpec.AT_MOST
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.blankj.utilcode.util.SizeUtils
import com.google.android.flexbox.*


/**
 * 描述：RecyclerView 设置LayoutManager 帮助类
 *
 * @author JiaoPeng by 3/4/21
 */
object LayoutManagerUtil {

    /**
     * 设置列表流式布局
     */
    fun getFlexBoxLayoutManager(context: Context): FlexboxLayoutManager {
        val manager = FlexboxLayoutManager(context)
        manager.flexWrap = FlexWrap.WRAP
        manager.flexDirection = FlexDirection.ROW
        manager.alignItems = AlignItems.STRETCH
        manager.justifyContent = JustifyContent.FLEX_START
        return manager
    }

}

/**
 * 描述：设置Recyclerview固定高度
 */
class MaxHeightGridLayoutManager(
    context: Context,
    span: Int,
    private var maxHeight: Int = SizeUtils.dp2px(300F)

) : GridLayoutManager(context, span) {

    override fun setMeasuredDimension(childrenBounds: Rect, wSpec: Int, hSpec: Int) {
        super.setMeasuredDimension(
            childrenBounds,
            wSpec,
            View.MeasureSpec.makeMeasureSpec(maxHeight, AT_MOST)
        )
    }

}

class MaxHeightLayoutManager(
    context: Context,
    private var maxHeight: Int = SizeUtils.dp2px(300F)
) : LinearLayoutManager(context) {

    override fun setMeasuredDimension(childrenBounds: Rect, wSpec: Int, hSpec: Int) {
        super.setMeasuredDimension(
            childrenBounds,
            wSpec,
            View.MeasureSpec.makeMeasureSpec(maxHeight, AT_MOST)
        )
    }

}

/**
 * 描述：为了解决RecyclerView原生Bug：
 * Inconsistency detected. Invalid view holder adapter positionViewHolder{a1bbfa3 position=2 id=-1, oldPos=-1, pLpos:-1 no parent}，recyclerviewholder
 */
class XLinearLayoutManager : LinearLayoutManager {

    constructor(context: Context?) : super(context)

    constructor(context: Context?, orientation: Int, reverseLayout: Boolean) : super(
        context,
        orientation,
        reverseLayout
    )

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    override fun onLayoutChildren(recycler: Recycler, state: RecyclerView.State) {
        try {
            super.onLayoutChildren(recycler, state)
        } catch (e: IndexOutOfBoundsException) {
            e.printStackTrace()
        }
    }

}
