package badang.android.taskit.common

import android.content.Context

/**
 * Lấy chuỗi string trong thư mục `res` từ resource ID.
 *
 * Thường dùng để truy cập chuỗi trong phần code (thay vì hard-code).
 *
 * @param context Context dùng để truy cập resource, có thể là Application, Activity, Fragment, v.v.
 * Trong một số thành phần như Fragment có thể sử dụng [requireContext] để lấy context.
 * @param res ID của chuỗi string trong thư mục `res/values/string_task.xml`.
 * @return Chuỗi String tương ứng với resource ID đã truyền vào.
 */

object StringResource {
    fun execute(context: Context, res: Int): String{
        return context.getString(res)
    }
}





