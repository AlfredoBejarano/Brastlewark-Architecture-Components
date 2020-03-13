package me.alfredobejarano.brastlewarkarchitecturecomponents.utils

import androidx.databinding.BindingAdapter
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar

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
    }
}