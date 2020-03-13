package me.alfredobejarano.brastlewarkarchitecturecomponents.utils

import androidx.databinding.BindingAdapter
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar
import com.facebook.drawee.view.SimpleDraweeView
import me.alfredobejarano.brastlewarkarchitecturecomponents.R

class BindingAdapters {
    companion object {
        @JvmStatic
        @BindingAdapter("setRange")
        fun setRange(seekBar: CrystalRangeSeekbar, range: IntRange?) {
            seekBar.run {
                val min = range?.first?.toFloat() ?: 0f
                val max = range?.last?.toFloat() ?: 0f

                setMinValue(min)
                setMaxValue(max)
                setMinStartValue(min)
                setMaxStartValue(max)
            }
        }

        @JvmStatic
        @BindingAdapter("imageSrc")
        fun setImageSrc(draweeView: SimpleDraweeView, src: String?) =
            src?.run { draweeView.setImageURI(src) } ?: run {
                draweeView.setImageResource(R.drawable.ic_placeholder_picture)
            }
    }
}