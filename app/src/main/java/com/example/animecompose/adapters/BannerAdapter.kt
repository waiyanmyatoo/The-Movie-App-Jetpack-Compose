package com.example.animecompose.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import coil.load
import com.example.animecompose.R
import com.example.animecompose.data.vos.MovieVO
import com.example.animecompose.utils.IMAGE_BASE_URL

class BannerAdapter(val nowPlayingMovies: List<MovieVO>?) : PagerAdapter() {

    private var mMovieList: List<MovieVO>? = null;

    override fun getCount(): Int {
        // Return the number of pages in the ViewPager
        return nowPlayingMovies?.size ?: 0;
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        // Create or inflate the view for the given position
//        val view = TextView(container.context)
//        view.text = "Page ${position + 1}"
        val itemView =
            LayoutInflater.from(container.context).inflate(R.layout.view_holder_banner, null, false)
        val titleView = itemView.findViewById<TextView>(R.id.tvBannerMovieName)
        val imageView = itemView.findViewById<ImageView>(R.id.ivBannerImage)
        imageView.load("$IMAGE_BASE_URL${nowPlayingMovies!![position].posterPath}")
        titleView.text = nowPlayingMovies[position].title ?: ""
        container.addView(itemView)
        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        // Remove the view for the given position from the container
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        // Check if the view belongs to the provided object
        return view == `object`
    }

    fun setMovies(movies: List<MovieVO>?) {
        mMovieList = nowPlayingMovies
        notifyDataSetChanged()
    }
}