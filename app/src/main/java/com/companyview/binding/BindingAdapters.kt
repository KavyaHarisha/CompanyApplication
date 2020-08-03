package com.companyview.binding

import android.widget.Button
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.companyview.R

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        Glide.with(view.context)
                .load(imageUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.mipmap.ic_launcher)
                .into(view)
    }
}

@BindingAdapter("imageFavorite")
fun bindImageFavorite(view: ImageView, isFavorite:Boolean) {
    view.setBackgroundResource(if(isFavorite)R.drawable.ic_favorite_24px
    else R.drawable.ic_favorite_border_24px)
}

@BindingAdapter("buttonTextFollow")
fun bindTextFollow(view: Button, isFollow:Boolean) {
    view.text = if(isFollow)view.resources.getString(R.string.txt_following) else
        view.resources.getString(R.string.txt_follow)
}


