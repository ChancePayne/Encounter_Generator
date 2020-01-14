package com.cmpayne.dnd5e

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cmpayne.dnd5e.models.Monster


class BestiaryListAdapter(
    val data: MutableList<Monster>,
    val view: BestiaryListInterface,
    val count: MutableList<Int> = mutableListOf(),
    val counted: Boolean = false,
    val requestCode: Int = -1
    ) :
    RecyclerView.Adapter<BestiaryListAdapter.BestiaryViewHolder>() {

    var displayData = data.toList()

    fun refreshData() {
        displayData = data.toList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestiaryViewHolder {
        return BestiaryViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.beast_list_item,
                parent,
                false
            )
        )
    }

    fun filterData(filter: String) {
        if(!counted) {
            displayData = data.filter {
                it.name.toLowerCase().contains(filter.toLowerCase())
            }
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return displayData.size
    }

    override fun onBindViewHolder(holder: BestiaryViewHolder, position: Int) {
        val monster = displayData[position]

        holder.name.text = monster.name
        holder.challenge.text = monster.challenge[0].rating
//        holder.description.text = "${monster.size} ${monster.typeObj}, ${monster.alignment.toString()}"
        holder.parent.setOnClickListener {
            //            Toast.makeText(it.context, monster.name, Toast.LENGTH_SHORT).show()
            view.monsterSelected(monster, requestCode)
        }

        holder.parent.setOnLongClickListener {
            view.monsterLongSelected(monster)
            true
        }

        if(counted) {
            holder.bestiaryItemQty.text = count[position].toString()
            holder.bestiaryAddButton.setOnClickListener {
                count[position]++
                holder.bestiaryItemQty.text = count[position].toString()
                view.monsterQtyChanged(monster, count[position])
            }
            holder.bestiarySubtractButton.setOnClickListener {
                count[position]--
                holder.bestiaryItemQty.text = count[position].toString()
                view.monsterQtyChanged(monster, count[position])
                if(count[position] < 0) {
                    data.removeAt(position)
                    count.removeAt(position)
                    refreshData()
                }
            }
        } else {
            holder.bestiaryQtyLayout.visibility = View.GONE
        }
    }

    class BestiaryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.bestiary_item_name)
        val challenge: TextView = view.findViewById(R.id.bestiary_item_challenge)
        val description: TextView = view.findViewById(R.id.bestiary_item_description)
        val parent: View = view.findViewById(R.id.bestiary_card_view)
        val bestiaryQtyLayout: ViewGroup = view.findViewById(R.id.bestiary_qty_layout)
        val bestiaryItemQty: TextView = view.findViewById(R.id.bestiary_item_qty)
        val bestiaryAddButton: ImageView = view.findViewById(R.id.bestiary_add_button)
        val bestiarySubtractButton: ImageView = view.findViewById(R.id.bestiary_subtract_button)
    }
}