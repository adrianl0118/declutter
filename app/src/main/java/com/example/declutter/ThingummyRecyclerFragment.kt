package com.example.declutter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.declutter.databinding.FragmentThingummyRecyclerBinding

// A catalogue of all things in the database presented using RecyclerView
class ThingummyRecyclerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentThingummyRecyclerBinding>(
            inflater,
            R.layout.fragment_thingummy_recycler,
            container,
            false
        )
        val recyclerViewThing = binding.recyclerForThing as RecyclerView
        recyclerViewThing.layoutManager = LinearLayoutManager(activity)
        recyclerViewThing.adapter = ThingummyRecAdapter()

        binding.backToHomeButton.setOnClickListener{view: View->
            view.findNavController().navigate(R.id.action_thingummyRecyclerFragment_to_homePage)
        }

        return binding.root
    }
}