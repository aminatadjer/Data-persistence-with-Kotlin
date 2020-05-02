package com.example.exercice4.ui
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.exercice4.R
import com.example.exercice4.db.Note
import com.example.exercice4.db.NoteDataBase
import kotlinx.android.synthetic.main.fragment_add_note.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.note_layout.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*


class AddNoteFragment : BaseFragment() {

    private var note:Note? =null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_note, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val c=Calendar.getInstance()
        val year=c.get(Calendar.YEAR)
        val month=c.get(Calendar.MONTH)
        val day=c.get(Calendar.DAY_OF_MONTH)
        buttonDate.setOnClickListener{
            val dpd = DatePickerDialog(context, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in TextView
                date_add.setText("" + dayOfMonth + " " + month + ", " + year)
            }, year, month, day)
            dpd.show()
        }
        // NoteDataBase(activity!!).getNoteDao().//call the invoke make sure that its not null
        arguments?.let {
            note=AddNoteFragmentArgs.fromBundle(it).note
            title.setText(note?.title)// si on a clické sur un item dans la liste on récupére d'abords ce qui y a dedans
            text_note.setText(note?.note)
           date_add.setText(note?.date)

        }
        button_save.setOnClickListener {
            val noteTitle= title.text.toString().trim()
            val noteBody=text_note.text.toString().trim()
            var notedate=date_add.text.toString().trim()

            if(noteTitle.isEmpty()){
                title.error="title required"
                title.requestFocus()
                return@setOnClickListener
            }
            if(noteTitle.isEmpty()){
                text_note.error="text required"
                text_note.requestFocus()
                return@setOnClickListener
            }
            launch {
                context?.let {//to verify if context is not null it contain context
                    val mNote= Note(noteTitle,noteBody,notedate)

                    if(note==null){
                        //val mNote= Note(noteTitle,noteBody,"12")
                        NoteDataBase(it).getNoteDao().addNote(mNote)
                        it.toast("Note Saved")
                    }else{
                        //val mNote= Note(noteTitle,noteBody,"12")
                        mNote.id=note!!.id
                        NoteDataBase(it).getNoteDao().updateNote(mNote)
                        it.toast("Note updated")
                    }

                    val action=AddNoteFragmentDirections.actionSaveNote()
                    view?.let { it1 -> Navigation.findNavController(it1).navigate(action) }
                }
            }
        }
    }

    fun deleteNote(){
        AlertDialog.Builder(context).apply{
            setTitle("are you sure")
            setMessage("you can't undo this operation")
            setPositiveButton("yes"){  _,_ ->
                launch {
                    NoteDataBase(context).getNoteDao().deleteNote(note!!)
                    val action=AddNoteFragmentDirections.actionSaveNote()
                    view?.let { it1 -> Navigation.findNavController(it1).navigate(action) }

                }
            }
            setNegativeButton("No"){
                _,_ ->

            }
        }.create().show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.delete -> if(note!=null)deleteNote() else context?.toast("note not created yet")
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu,menu)
    }
    //setBackroungColor

}
