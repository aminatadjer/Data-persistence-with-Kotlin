package com.example.exercice3.ui


import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.annotation.RequiresApi
import androidx.navigation.Navigation

import com.example.exercice3.R
import com.example.exercice3.db.InterventionDataBase
import com.example.exercice3.db.entities.Plombier
import kotlinx.android.synthetic.main.fragment_add_plombier2.*
import kotlinx.coroutines.launch
import org.jetbrains.anko.toast
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class AddPlombierFragment : BaseFragment() {
    private var plumber:Plombier? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_plombier2, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        arguments?.let {
            plumber=AddPlombierFragmentArgs.fromBundle(it).plumber
            NomPlumberEdit.setText(plumber?.Nom)//recuperation des donnée
            PrenomPlumberEdit.setText(plumber?.Prenom)
            ExperPlumberEdit.setText(plumber?.Experience)

        }//to verify ig args not null
        button_save_plumber.setOnClickListener {
            val nom= NomPlumberEdit.text.toString().trim()
            val prenom=PrenomPlumberEdit.text.toString().trim()
            var exper=ExperPlumberEdit.text.toString().trim()

            if(nom.isEmpty()){
                NomPlumberEdit.error="title required"
                NomPlumberEdit.requestFocus()
                return@setOnClickListener
            }
            if(prenom.isEmpty()){
                PrenomPlumberEdit.error="text required"
                PrenomPlumberEdit.requestFocus()
                return@setOnClickListener
            }
            launch {
                context?.let {//to verify if context is not null it contain context
                    val mPlombier= Plombier(nom,prenom,exper)
                    if (plumber==null){
                        InterventionDataBase(it).getPlombierDao().addPlumber( mPlombier)
                        it.toast("Plumber Saved")
                    }else{
                        mPlombier.id=plumber!!.id
                        InterventionDataBase(it).getPlombierDao().updatePlumber( mPlombier)
                        it.toast("Plumber updated")
                    }

                    val action =AddPlombierFragmentDirections.actionPlombierRetour()
                    view?.let { it1 -> Navigation.findNavController(it1).navigate(action) }




                }
            }
        }
    }


    fun deletePlombier(){
        AlertDialog.Builder(context).apply{
            setTitle("are you sure")
            setMessage("you can't undo this operation")
            setPositiveButton("yes"){  _,_ ->
                launch {
                    InterventionDataBase(context).getPlombierDao().deletePlumber(plumber!!)
                    val action=AddPlombierFragmentDirections.actionPlombierRetour()
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
            R.id.delete -> if(plumber!=null)deletePlombier() else context?.toast("plumber not created yet")
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu,menu)
    }


}
