package com.example.declutter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.thing_row.view.*

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

        var thing = thingAdapter.returnArray()[position]
        holder.view.thingHeadlineTextView.text = thing?.headline
        holder.view.thingDescTextView.text = thing?.desc
        holder.view.widthTextView.text = thing?.width.toString()
        holder.view.depthTextView.text = thing?.depth.toString()
        holder.view.heightTextView.text = thing?.height.toString()
        holder.view.weightTextView.text = thing?.weight.toString()
        holder.view.thingTypeView.text = thing?.type
        holder.view.thingContactTextView.text = thing?.contact
        thingAdapter.setImageOnView(thing?.headline, holder.view.thingImageView)


        holder.view.moreInfoBtn.setOnClickListener {
            holder.view.thingTypeView.visibility = View.VISIBLE
            holder.view.typeView.visibility = View.VISIBLE
            holder.view.descTextView.visibility = View.VISIBLE
            holder.view.thingDescTextView.visibility = View.VISIBLE
            holder.view.contactTextView.visibility = View.VISIBLE
            holder.view.thingContactTextView.visibility = View.VISIBLE
            holder.view.dimTextView.visibility = View.VISIBLE
            holder.view.widthTextView.visibility = View.VISIBLE
            holder.view.depthTextView.visibility = View.VISIBLE
            holder.view.heightTextView.visibility = View.VISIBLE
            holder.view.weightTextView.visibility = View.VISIBLE
            holder.view.moreInfoBtn.visibility = View.GONE
            holder.view.backCollapse.visibility = View.VISIBLE
            notifyDataSetChanged()

        }

        holder.view.backCollapse.setOnClickListener {
            holder.view.thingTypeView.visibility = View.GONE
            holder.view.typeView.visibility = View.GONE
            holder.view.descTextView.visibility = View.GONE
            holder.view.thingDescTextView.visibility = View.GONE
            holder.view.contactTextView.visibility = View.GONE
            holder.view.thingContactTextView.visibility = View.GONE
            holder.view.dimTextView.visibility = View.GONE
            holder.view.widthTextView.visibility = View.GONE
            holder.view.depthTextView.visibility = View.GONE
            holder.view.heightTextView.visibility = View.GONE
            holder.view.weightTextView.visibility = View.GONE
            holder.view.backCollapse.visibility = View.GONE
            holder.view.moreInfoBtn.visibility = View.VISIBLE
            notifyDataSetChanged()
        }


    }

    class ThingViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    }
}