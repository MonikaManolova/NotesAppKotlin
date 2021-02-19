package com.example.my_notes

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter(val arrayList: ArrayList<Note>, val context: Context) : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_notes_rv, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {

        holder.title.text = arrayList[position].title
        holder.description.text = arrayList[position].description
        holder.date.text = arrayList[position].date

        //Click item to redirect for edit
        holder.rvItem.setOnClickListener{
           val databaseHelper = DataBaseHelper(context)

            databaseHelper.deleteNote(arrayList[position].id)
            this.notifyItemRangeChanged(position, arrayList.size)
            arrayList.removeAt(position)
        }
    }

    class NotesViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val title = view!!.findViewById<TextView>(R.id.tvTitle)
        val description = view!!.findViewById<TextView>(R.id.tvDescription)
        val date = view!!.findViewById<TextView>(R.id.tvDate)
        val rvItem = view!!.findViewById<LinearLayout>(R.id.rvItem)
    }

}