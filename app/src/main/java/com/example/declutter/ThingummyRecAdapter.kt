package com.example.declutter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ThingummyRecAdapter : RecyclerView.Adapter<ThingummyRecAdapter.ThingViewHolder>() {

    override fun getItemCount(): Int {
        return thingAdapter.arraySize()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThingViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.thing_row, parent, false)
        return ThingViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: ThingViewHolder, position: Int) {

        var pet = thingAdapter.returnArray()[position]
        holder.view.petNameTextView.text = pet?.name
        holder.view.petBioTextView.text = pet?.bio
        holder.view.petTypeView.text = pet?.type
        holder.view.petContactTextView.text = pet?.contact
        thingAdapter.setImageOnView(pet?.name, holder.view.petImageView)


        holder.view.adoptThatBoi.setOnClickListener {
            holder.view.petTypeView.visibility = View.VISIBLE
            holder.view.typeView.visibility = View.VISIBLE
            holder.view.bioTextView.visibility = View.VISIBLE
            holder.view.petBioTextView.visibility = View.VISIBLE
            holder.view.contactTextView.visibility = View.VISIBLE
            holder.view.petContactTextView.visibility = View.VISIBLE
            holder.view.adoptThatBoi.visibility = View.GONE
            holder.view.backCollapse.visibility = View.VISIBLE
            notifyDataSetChanged()

        }

        holder.view.backCollapse.setOnClickListener {
            holder.view.petTypeView.visibility = View.GONE
            holder.view.typeView.visibility = View.GONE
            holder.view.bioTextView.visibility = View.GONE
            holder.view.petBioTextView.visibility = View.GONE
            holder.view.contactTextView.visibility = View.GONE
            holder.view.petContactTextView.visibility = View.GONE
            holder.view.backCollapse.visibility = View.GONE
            holder.view.adoptThatBoi.visibility = View.VISIBLE
            notifyDataSetChanged()
        }


    }

    class ThingViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    }
}