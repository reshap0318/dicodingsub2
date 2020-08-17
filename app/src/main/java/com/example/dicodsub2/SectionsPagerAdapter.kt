package com.example.dicodsub2

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.dicodsub2.Activity.FollowerFragment
import com.example.dicodsub2.Activity.FollowingFragment
import android.R.attr.fragment
import android.os.Bundle



class SectionsPagerAdapter(private val mContext: Context, fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    @StringRes
    private val TAB_TITLES = intArrayOf(R.string.tab_follower, R.string.tab_following)
    var username: String? = null

    override fun getItem(position: Int): Fragment {

        var fragment: Fragment? = null
        when (position) {
            0 -> {
                fragment = FollowerFragment()
                val bundle = Bundle()
                bundle.putString("USERNAME", username)
                fragment.setArguments(bundle)
            }
            1 -> {
                fragment = FollowingFragment()
                val bundle = Bundle()
                bundle.putString("USERNAME", username)
                fragment.setArguments(bundle)
            }
        }
        return fragment as Fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mContext.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return 2
    }

}