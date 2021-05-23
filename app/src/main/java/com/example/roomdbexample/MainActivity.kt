package com.example.roomdbexample

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), NotesRVAdapter.INotesRVAdapter {
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var editTxt: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val list = findViewById<RecyclerView>(R.id.recyclerView)
        editTxt = findViewById(R.id.input)

        list.layoutManager = LinearLayoutManager(this)
        val adapter = NotesRVAdapter(this, this)
        list.adapter = adapter

        noteViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )
            .get(NoteViewModel::class.java)
        noteViewModel.allNotes.observe(this, Observer { list ->
            list?.let {
                adapter.updateList(list)
            }
        })
    }

    override fun onItemClicked(note: Note) {
        noteViewModel.deleteNote(note)
        Toast.makeText(this, "${note.text} Deleted", Toast.LENGTH_SHORT).show()
    }

    fun submitData(view: View) {
        val noteText = editTxt.text.toString()
        if (noteText.isNotEmpty()) {
            noteViewModel.insertNote(Note(noteText))
            Toast.makeText(this, "$noteText Inserted", Toast.LENGTH_SHORT).show()
        }
    }

}