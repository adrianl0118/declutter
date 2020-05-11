package com.example.declutter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
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

        binding.findPageButton.setOnClickListener{view:View ->
            if(!(binding.editCity.text.toString().equals(""))) {
                thingAdapter.sortBy(binding.editCity.text.toString())
            } else {
                thingAdapter.reset()
            }
            view.findNavController().navigate(R.id.action_homePage_to_thingummyRecyclerFragment)
        }

        binding.giveAwayButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_homePage_to_makeAdFragment)
        }

        return binding.root
    }
}