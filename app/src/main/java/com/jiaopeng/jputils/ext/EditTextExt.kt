package com.jiaopeng.jputils.ext

import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.EditText

/**
 * 描述：
 *
 * @author JiaoPeng by 3/4/21
 */
/**
 * 获取文本
 */
fun EditText.textString(): String {
    return this.text.toString()
}

/**
 * 获取去除空字符串的文本
 */
fun EditText.textStringTrim(): String {
    return this.textString().trim()
}

/**
 * 文本是否为空
 */
fun EditText.isEmpty(): Boolean {
    return this.textString().isEmpty()
}

/**
 * 去空字符串后文本是否为空
 */
fun EditText.isTrimEmpty(): Boolean {
    return this.textStringTrim().isEmpty()
}

/**
 * 设置EditText值
 */
fun EditText.text(charSequence: CharSequence?) {
    this.text = Editable.Factory.getInstance().newEditable(charSequence ?: "")
}

/**
 * 监听EditText输入变化
 */
fun EditText.afterTextChange(afterTextChanged: (String) -> Unit) {

    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            afterTextChanged.invoke(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }
    })

}

/**
 * 显示密码文本
 */
fun EditText.showPassword(){
    transformationMethod = HideReturnsTransformationMethod.getInstance()
    setSelection(text.length)
}

/**
 * 隐藏密码文本
 */
fun EditText.hidePassword(){
    transformationMethod = PasswordTransformationMethod.getInstance()
    setSelection(text.length)
}