package com.example.animecompose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.viewpager.widget.ViewPager
import com.example.animecompose.adapters.BannerAdapter

@Composable
fun BannerView(modifier: Modifier = Modifier, mBannerAdapter: BannerAdapter) {
    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        factory = { context ->
            ViewPager(context).apply {
                adapter = mBannerAdapter

            }
        },
        update = { view ->
            mBannerAdapter.notifyDataSetChanged()
            view.adapter = mBannerAdapter
        }
    )

}