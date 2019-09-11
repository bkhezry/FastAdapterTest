package com.github.bkhezry.fastadaptertest.model


import android.graphics.Color
import android.view.View
import android.widget.TextView
import com.github.bkhezry.fastadaptertest.R
import com.google.android.material.card.MaterialCardView
import com.google.gson.annotations.SerializedName
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import com.mikepenz.fastadapter.ui.utils.FastAdapterUIUtils
import com.mikepenz.materialize.util.UIUtils


data class Feature(
    @SerializedName("type")
    val typeString: String,
    @SerializedName("properties")
    val properties: Properties,
    @SerializedName("geometry")
    val geometry: Geometry,
    @SerializedName("id")
    val id: String
) : AbstractItem<Feature.ViewHolder>() {
    override fun getViewHolder(v: View): ViewHolder {
        return ViewHolder(v)
    }

    override val type: Int
        get() = R.id.fastadapter_sample_item_id

    /** defines the layout which will be used for this item in the list  */
    override val layoutRes: Int
        get() = R.layout.feature_item


    class ViewHolder(view: View) : FastAdapter.ViewHolder<Feature>(view) {
        private val view: MaterialCardView = view as MaterialCardView
        private val title: TextView = view.findViewById(R.id.title_text_view)
        override fun bindView(item: Feature, payloads: MutableList<Any>) {
            UIUtils.setBackground(
                view,
                FastAdapterUIUtils.getSelectableBackground(view.context, Color.RED, true)
            )
            title.text = item.properties.title
        }

        override fun unbindView(item: Feature) {
            title.text = null
        }
    }
}