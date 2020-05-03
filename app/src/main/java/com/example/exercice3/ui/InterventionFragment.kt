package com.example.exercice3.ui


import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager

import com.example.exercice3.R
import com.example.exercice3.db.InterventionDataBase
import kotlinx.android.synthetic.main.fragment_add_intervention3.*
import kotlinx.android.synthetic.main.fragment_intervention.*
import kotlinx.android.synthetic.main.fragment_plombier.*
import kotlinx.coroutines.launch
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class InterventionFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_intervention, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recycler_view_Intervention.setHasFixedSize(true)
        recycler_view_Intervention.layoutManager=
            StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        val c= Calendar.getInstance()
        val year=c.get(Calendar.YEAR)
        val month=c.get(Calendar.MONTH)
        val day=c.get(Calendar.DAY_OF_MONTH)
        button_Filter.setOnClickListener{
            val dpd = DatePickerDialog(context, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in TextView
                var dtt=("" + dayOfMonth + " " + month + ", " + year)
                if (dtt!=null){
                    date_add_filter.setText(dtt)
                }
            }, year, month, day)
            dpd.show()

        }

        launch {
            context?.let {

                    val interventions= InterventionDataBase(it).getInterventionDao().getAllInterventions()
                    recycler_view_Intervention.adapter=InterventionAdapter(interventions)




            }
        }




        button_add_intervention.setOnClickListener{
            val action=InterventionFragmentDirections.actionAddIntervention()
            Navigation.findNavController(it).navigate(action)

        }
        button_search.setOnClickListener{
            var d=date_add_filter.text
            if (d!=null){
                val action=InterventionFragmentDirections.actionFilter()
                action.dte=date_add_filter.text.toString()
                Navigation.findNavController(it).navigate(action)
            }

        }




    }


}
