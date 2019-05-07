package com.example.secoo.webviewandviewpagerscrollsample

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private val viewPager: ViewPager by lazy {
        findViewById<ViewPager>(R.id.pager)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewPager.adapter = ScreenSlidePagerAdapter(supportFragmentManager)
    }


    private inner class ScreenSlidePagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            return if (position == 0) {
//                WebViewFragment.getInstance("https://asset.droidyue.com/content/scroll_page.html")
                WebViewFragment.getInstance("https://m.baidu.com/s?ie=UTF-8&wd=%E9%A9%AC%E4%BA%91%E7%85%A7%E7%89%87")
            } else {
                WebViewFragment.getInstance("https://droidyue.com")
            }
        }

        override fun getCount(): Int {
            return 2
        }
    }
}
