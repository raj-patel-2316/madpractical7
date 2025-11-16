package com.example.madpractical7

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PersonAdapter(
    val persons: MutableList<Person>,
    private val onDelete: (Int) -> Unit
) : RecyclerView.Adapter<PersonAdapter.ViewHolder>() {

    class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.listview_component, parent, false)
    ) {
        val name: TextView = itemView.findViewById(R.id.name)
        val phone: TextView = itemView.findViewById(R.id.phone)
        val email: TextView = itemView.findViewById(R.id.email)
        val address: TextView = itemView.findViewById(R.id.address)
        val delete: ImageButton = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val person = persons[position]
        holder.name.text = person.name
        holder.phone.text = person.phoneNo
        holder.email.text = person.emailId
        holder.address.text = person.address
        holder.delete.setOnClickListener { onDelete(position) }
    }

    override fun getItemCount() = persons.size

    fun remove(position: Int) {
        persons.removeAt(position)
        notifyItemRemoved(position)
    }

    fun update(list: List<Person>) {
        persons.clear()
        persons.addAll(list)
        notifyDataSetChanged()
    }
}
