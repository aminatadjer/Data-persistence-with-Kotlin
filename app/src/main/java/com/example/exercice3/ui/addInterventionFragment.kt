package com.example.exercice3.ui


import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner

import com.example.exercice3.R
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_add_intervention3.*
import android.widget.ArrayAdapter
import androidx.navigation.Navigation
import com.example.exercice3.db.InterventionDataBase
import com.example.exercice3.db.entities.Intervention
import com.example.exercice3.db.entities.Plombier
import kotlinx.android.synthetic.main.fragment_add_plombier2.*
import kotlinx.android.synthetic.main.fragment_plombier.*
import kotlinx.coroutines.launch
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class addInterventionFragment : BaseFragment() {
    private var intervention:Intervention? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_intervention3, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val plomb= arrayOf("nassil","mouhoub","aghiles")
        val plombierAdapter = ArrayAdapter(activity,android.R.layout.simple_spinner_item,plomb)
        spinnerPlombier.adapter=plombierAdapter
        spinnerPlombier.onItemSelectedListener=object :
            AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                chosedPlumber.setText(plomb[p2])
            }
        }


        val types=arrayOf("dépannage","maintenance","installation")
        val aa = ArrayAdapter(activity,android.R.layout.simple_spinner_item,types)
        spinnerType.adapter=aa
        spinnerType.onItemSelectedListener=object :
            AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                chosedType.setText(types[p2])
            }
        }

        val c= Calendar.getInstance()
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

        arguments?.let {
            intervention=addInterventionFragmentArgs.fromBundle(it).intervention
            date_add.setText(intervention?.date)
            NumEdit.setText(intervention?.num)
            chosedType.setText(intervention?.type)
            chosedPlumber.setText(intervention?.plombier)
        }









        button_save_Intervention.setOnClickListener {
            val date=date_add.text.toString().trim()
            val numero=NumEdit.text.toString().trim()
            val type=chosedType.text.toString().trim()
            val plumb=chosedPlumber.text.toString().trim()
            if(date.isEmpty()){
                date_add.error="date required"
                date_add.requestFocus()
                return@setOnClickListener
            }
            if(numero.isEmpty()){
                NumEdit.error="number required"
                NumEdit.requestFocus()
                return@setOnClickListener
            }
            launch {
                context?.let {


                    val mintervention=Intervention(date,numero,plumb,type)
                    if (intervention==null){
                        InterventionDataBase(it).getInterventionDao().addIntervention(mintervention)
                        it.toast("Intervention saved")
                    }
                    else{
                        mintervention.idInterv=intervention!!.idInterv
                        InterventionDataBase(it).getInterventionDao().updateIntervention(mintervention)
                        it.toast("Intervention updated")
                    }







                    val action=addInterventionFragmentDirections.actionInterventionRetour()
                    view?.let { it1 -> Navigation.findNavController(it1).navigate(action) }



                }
            }
        }



    }


}
