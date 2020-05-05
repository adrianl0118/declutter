package com.example.declutter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.declutter.databinding.FragmentHomePageBinding

class HomePage : Fragment() {

    override  fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View? {
        //Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentHomePageBinding>(
            inflater,
            R.layout.fragment_home_page,
            container,
            false
        )


    }
}