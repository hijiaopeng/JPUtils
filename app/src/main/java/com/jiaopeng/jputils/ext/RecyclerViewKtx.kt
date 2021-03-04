package com.jiaopeng.jputils.ext

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.recyclerview.widget.*
import com.blankj.utilcode.util.SizeUtils.dp2px
import com.google.android.flexbox.FlexboxLayoutManager
import com.jiaopeng.jputils.utils.LayoutManagerUtil
import com.jiaopeng.jputils.widget.RecyclerViewDivider
import com.lxj.easyadapter.EasyAdapter
import com.lxj.easyadapter.ItemDelegate
import com.lxj.easyadapter.MultiItemTypeAdapter
import com.lxj.easyadapter.ViewHolder
import java.util.*

/**
 * 描述：RecyclerView 的扩展函数
 *
 * @author JiaoPeng by 3/4/21
 */
/**
 * 绑定普通的Recyclerview
 */

/**
 * 使用
recyclerView.vertical() //设置垂直
            //.vertical(spanCount = 2, isStaggered = true) //设置垂直2列瀑布流
            .divider(color = Color.RED, size = 1) //设置分割线
            // 绑定数据，只需传数据和布局，然后实现绑定的方法，再也不用写Adapter
            .bindData(data, R.layout.adapter_rv) { holder, t, position ->
                holder.setText(R.id.text, "模拟数据 - $t")
                        .setImageResource(R.id.image, R.mipmap.ic_launcher_round)
            }
            .addHeader(headerView) //必须在bindData之后调用
            .addFooter(footerView) //必须在bindData之后调用
            .itemClick<String> { data, holder, position ->
                toast("click ${data[position]}")
            }
            //.itemLongClick<String> { data, holder, position ->
            //    toast("click ${data[position]}")
            //}

//notify
//recyclerView.adapter.notifyItemChanged()


// 多条目类型
recyclerView.vertical()
            .divider(color = Color.RED)
            .multiTypes(data, listOf(OneItem(), TwoItem()))
            .addHeader(headerView) //必须在multiTypes之后调用
            .addFooter(footerView) //必须在multiTypes之后调用
            .itemClick<String> { data, holder, position ->
                toast("click ${data[position]}")
            }
// 实现多个Item类型
class OneItem : ItemViewDelegate<String>{
        override fun bind(holder: ViewHolder, t: String, position: Int) {
                    holder.setText(android.R.id.text1, t)
        }
        override fun isThisType(item: String, position: Int): Boolean {
            return item != "header" && item != "footer"
        }
        override fun getLayoutId(): Int {
            return android.R.layout.simple_list_item_1
        }
    }
*/
fun RecyclerView.init(
    layoutManger: RecyclerView.LayoutManager,
    bindAdapter: RecyclerView.Adapter<*>,
    isScroll: Boolean = true
): RecyclerView {
    layoutManager = layoutManger
    setHasFixedSize(true)
    adapter = bindAdapter
    isNestedScrollingEnabled = isScroll
    return this
}

/**
 * 设置分割线
 * @param color 分割线的颜色，默认是#DEDEDE
 * @param size 分割线的大小，默认是1px
 * @param isReplace 是否覆盖之前的ItemDecoration，默认是true
 *
 */
fun RecyclerView.divider(color: Int = Color.parseColor("#f5f5f5"), size: Int = dp2px(1f), isReplace: Boolean = true): RecyclerView {
    val decoration = RecyclerViewDivider(context, orientation)
    decoration.setDrawable(GradientDrawable().apply {
        setColor(color)
        shape = GradientDrawable.RECTANGLE
        setSize(size, size)
    })
    if(isReplace && itemDecorationCount>0){
        removeItemDecorationAt(0)
    }
    addItemDecoration(decoration)
    return this
}

fun RecyclerView.flexbox(): RecyclerView {
    layoutManager = LayoutManagerUtil.getFlexBoxLayoutManager(context)
    return this
}

fun RecyclerView.vertical(spanCount: Int = 0, isStaggered: Boolean = false): RecyclerView {
    layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    if (spanCount != 0) {
        layoutManager = GridLayoutManager(context, spanCount)
    }
    if (isStaggered) {
        layoutManager = StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL)
    }
    return this
}

fun RecyclerView.horizontal(spanCount: Int = 0, isStaggered: Boolean = false): RecyclerView {
    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    if (spanCount != 0) {
        layoutManager = GridLayoutManager(context, spanCount, GridLayoutManager.HORIZONTAL, false)
    }
    if (isStaggered) {
        layoutManager = StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.HORIZONTAL)
    }
    return this
}


inline val RecyclerView.data
    get() = (adapter as EasyAdapter<*>).data

inline val RecyclerView.orientation
    get() = if (layoutManager == null) -1 else layoutManager.run {
        when (this) {
            is LinearLayoutManager -> orientation
            is GridLayoutManager -> orientation
            is StaggeredGridLayoutManager -> orientation
            else -> -1
        }
    }


fun <T> RecyclerView.bindData(data: List<T>, layoutId: Int, bindFn: (holder: ViewHolder, t: T, position: Int) -> Unit): RecyclerView {
    adapter = object : EasyAdapter<T>(data, layoutId) {
        override fun bind(holder: ViewHolder, t: T, position: Int) {
            bindFn(holder, t, position)
        }
    }
    return this
}

fun <T> RecyclerView.updateData(data: List<T>){
    val adapter = adapter as? EasyAdapter<T>?
    if(adapter!=null){
        adapter.data = data
        adapter.notifyDataSetChanged()
    }
}


/**
 * 必须在bindData之后调用，并且需要hasHeaderOrFooter为true才起作用
 */
fun RecyclerView.addHeader(headerView: View): RecyclerView {
    adapter?.apply {
        (this as EasyAdapter<*>).addHeaderView(headerView)
    }
    return this
}

/**
 * 必须在bindData之后调用，并且需要hasHeaderOrFooter为true才起作用
 */
fun RecyclerView.addFooter(footerView: View): RecyclerView {
    adapter?.apply {
        (this as EasyAdapter<*>).addFootView(footerView)
    }
    return this
}

fun <T> RecyclerView.multiTypes(data: List<T>, itemDelegates: List<ItemDelegate<T>>): RecyclerView {
    adapter = MultiItemTypeAdapter<T>(data).apply {
        itemDelegates.forEach { addItemDelegate(it) }
    }
    return this
}

fun <T> RecyclerView.itemClick(listener: (data: List<T>, holder: RecyclerView.ViewHolder, position: Int) -> Unit): RecyclerView {
    adapter?.apply {
        (adapter as MultiItemTypeAdapter<*>).setOnItemClickListener(object : MultiItemTypeAdapter.SimpleOnItemClickListener() {
            override fun onItemClick(view: View, holder: RecyclerView.ViewHolder, position: Int) {
                listener(data as List<T>, holder, position)
            }
        })
    }
    return this
}

fun <T> RecyclerView.itemLongClick(listener: (data: List<T>, holder: RecyclerView.ViewHolder, position: Int) -> Unit): RecyclerView {
    adapter?.apply {
        (adapter as MultiItemTypeAdapter<*>).setOnItemClickListener(object : MultiItemTypeAdapter.SimpleOnItemClickListener() {
            override fun onItemLongClick(view: View, holder: RecyclerView.ViewHolder, position: Int): Boolean {
                listener(data as List<T>, holder, position)
                return super.onItemLongClick(view, holder, position)
            }
        })
    }
    return this
}

fun RecyclerView.smoothScrollToEnd(){
    if(adapter!=null && adapter!!.itemCount>0){
        smoothScrollToPosition(adapter!!.itemCount-1)
    }
}

fun RecyclerView.scrollToEnd(){
    if(adapter!=null && adapter!!.itemCount>0){
        scrollToPosition(adapter!!.itemCount-1)
    }
}

/**
 * 滚动置顶，只支持线性布局
 */
fun RecyclerView.scrollTop(position: Int){
    if(layoutManager is LinearLayoutManager){
        (layoutManager as LinearLayoutManager).scrollToPositionWithOffset(position, 0)
    }
}

/**
 * 启用条目拖拽，必须在设置完adapter之后调用
 * @param isDisableLast 是否禁用最后一个拖拽
 */
fun RecyclerView.enableItemDrag(isDisableLast: Boolean = false, onDragFinish: (()->Unit)? = null ){
    ItemTouchHelper(object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
            if(adapter==null) return 0
            if(isDisableLast && viewHolder.adapterPosition == (adapter!!.itemCount-1) ) return 0
            return if (recyclerView.layoutManager is GridLayoutManager) {
                val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN or
                        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
                val swipeFlags = 0
                makeMovementFlags(dragFlags, swipeFlags)
            } else {
                val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
                val swipeFlags = 0
                makeMovementFlags(dragFlags, swipeFlags)
            }
        }
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            if(adapter==null) return false
            //得到当拖拽的viewHolder的Position
            val fromPosition = viewHolder.adapterPosition
            //拿到当前拖拽到的item的viewHolder
            val toPosition = target.adapterPosition
            if(isDisableLast && toPosition== (adapter!!.itemCount-1) )return false
            if (fromPosition < toPosition) {
                for (i in fromPosition until toPosition) {
                    Collections.swap((adapter as EasyAdapter<*>).data, i, i + 1)
                }
            } else {
                for (i in fromPosition downTo toPosition + 1) {
                    Collections.swap((adapter as EasyAdapter<*>).data, i, i - 1)
                }
            }
            recyclerView.adapter?.notifyItemMoved(fromPosition, toPosition)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}

        override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
            super.onSelectedChanged(viewHolder, actionState)
        }

        override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
            super.clearView(recyclerView, viewHolder)
            onDragFinish?.invoke()
        }

    }).attachToRecyclerView(this)
}

fun RecyclerView.disableItemChangeAnimation(){
    (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
}