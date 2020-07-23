package com.teodyulgerov.breakingbadcharacters.bindings

import android.view.Gravity
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexboxLayoutManager
import com.squareup.picasso.Picasso
import com.teodyulgerov.breakingbadcharacters.R
import com.teodyulgerov.breakingbadcharacters.utils.PICASSO_IMG_HEIGHT
import com.teodyulgerov.breakingbadcharacters.utils.PICASSO_IMG_WIDTH

@BindingAdapter("src")
fun setImageDrawable(imgView: ImageView, imgUrl: String) {
    Picasso.get()
        .load(imgUrl)
        .resize(PICASSO_IMG_WIDTH, PICASSO_IMG_HEIGHT)
        .onlyScaleDown()
        .centerCrop(Gravity.TOP)
        .error(R.drawable.fallback)
        .into(imgView)

    // Update the params for the card view images in the Overview screen.
    // Only they are using the Flexbox Layout Manager.
    val params = imgView.layoutParams
    if (params is FlexboxLayoutManager.LayoutParams) {
        params.flexGrow = 1.0f
        params.alignSelf = AlignItems.FLEX_START
    }
}
