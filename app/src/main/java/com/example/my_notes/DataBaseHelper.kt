package com.example.my_notes

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataBaseHelper(context: Context) : SQLiteOpenHelper(context, "notes.db", null, 1) {

    private val NOTES_TABLE = "NOTES_TABLE"
    private val TITLE = "TITLE"
    private val DESCRIPTION = "DESCRIPTION"
    private val NOTE_ID = "NOTE_ID"
    private val DATE = "DATE"

    override fun onCreate(db: SQLiteDatabase) {
       db.execSQL("CREATE TABLE $NOTES_TABLE ($NOTE_ID INTEGER PRIMARY KEY AUTOINCREMENT, $TITLE TEXT, $DESCRIPTION TEXT, $DATE TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $NOTES_TABLE")
        onCreate(db)
    }

    fun addNote(title: String, description: String, date: String){
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(TITLE, title)
            put(DESCRIPTION, description)
            put(DATE, date)
        }

        db.insert(NOTES_TABLE,null, contentValues)
    }

    fun updateNote(id: Int, title: String, description: String, date: String) : Boolean {
        val db = this.writableDatabase

        val contentValues = ContentValues().apply {
            put(TITLE, title)
            put(DESCRIPTION, description)
            put(DATE, date)
        }

        db.update(NOTES_TABLE, contentValues, "$NOTE_ID = $id", null)

        return  true
    }

    fun deleteNote(id: Int) : Int{
        val db = this.writableDatabase

        return db.delete(NOTES_TABLE, "$NOTE_ID = $id", null)
    }

    fun getNotes() : ArrayList<Note>{
        var list : ArrayList<Note> = ArrayList()
        val db = this.readableDatabase
        val result = db.rawQuery("SELECT * FROM $NOTES_TABLE", null)

        if(result.moveToFirst()) {
            do {
                var Note = Note(
                        result.getInt(0),
                        result.getString(1),
                        result.getString(2),
                        result.getString(3))

                        list.add(Note)

            }while (result.moveToNext())
        }

        result.close()
        db.close()
        return list
    }

}