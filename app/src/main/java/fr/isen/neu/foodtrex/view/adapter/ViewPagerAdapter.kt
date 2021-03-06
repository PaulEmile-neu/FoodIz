package fr.isen.neu.foodtrex.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.squareup.picasso.Picasso
import fr.isen.neu.foodtrex.R

internal class ViewPagerAdapter (var context: Context, var images: List<String>): PagerAdapter() {

    lateinit var layoutInflater: LayoutInflater

    override fun getCount(): Int {
        return images.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view.equals(`object`)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        layoutInflater= LayoutInflater.from(context)
        var view = layoutInflater.inflate(R.layout.viewpager_images_adapter, container,false)
        val img : ImageView =view.findViewById(R.id.carrouselImg)


        if(images[position].isEmpty())
        {
            R.drawable.entry
        }
        else{
            Picasso.with(context)
                .load(images[position])
                .error(R.drawable.dishes)
                .into(img)
        }
        container.addView(view,0)
        return view

    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

}