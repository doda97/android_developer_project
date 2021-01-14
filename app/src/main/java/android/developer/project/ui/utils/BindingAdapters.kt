package android.developer.project.ui.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

class BindingAdapters {

    companion object {
        @JvmStatic
        @BindingAdapter("android:src")
        fun setImageResource(imageView: ImageView, resource: String) {
            Glide.with(imageView.context)
                .load(resource)
                .into(imageView)
        }
    }
}