package com.example.my_notes

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


class CreateNoteFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_home, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            CreateNoteFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val date = view!!.findViewById<TextView>(R.id.date)
        val btnBack = view!!.findViewById<ImageView>(R.id.imgBack)
        val et_title = view!!.findViewById<EditText>(R.id.etNoteTitle)
        val et_description = view!!.findViewById<EditText>(R.id.etNoteDescription)
        val btnSave = view!!.findViewById<Button>(R.id.btnSave)

        val sdf = SimpleDateFormat("dd/M/yyyy")
        val currentDate = sdf.format(Date())
        date.text = currentDate

        btnBack.setOnClickListener{
            replaceFragment(HomeFragment.newInstance())
        }

        btnSave.setOnClickListener{
            saveNote(et_title, et_description, date, view.context)
        }
    }

    private fun saveNote(et_title: EditText, et_description: EditText, date: TextView, context: Context){

        when {
            et_title.text.isNullOrEmpty() -> {
                Toast.makeText(context, "Title is Required", Toast.LENGTH_SHORT).show()
            }
            et_description.text.isNullOrEmpty() -> {
                Toast.makeText(context, "Description is Required", Toast.LENGTH_SHORT).show()
            }
            else -> {
                var dbHelper = DataBaseHelper(context)

                try {
                    dbHelper.addNote(et_title.text.toString(), et_description.text.toString(), date.text.toString())
                    replaceFragment(HomeFragment.newInstance())
                }catch (e: Exception){
                    e.printStackTrace()
                    Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    fun replaceFragment(fragment: Fragment){
        val fragmentTransition = activity!!.supportFragmentManager.beginTransaction()

        fragmentTransition.replace(R.id.frame_layout,fragment).addToBackStack(fragment.javaClass.simpleName).commit()
    }
}