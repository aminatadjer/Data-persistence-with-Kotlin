package com.example.exercice3.ui

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager

import com.example.exercice3.R
import com.example.exercice3.db.InterventionDataBase
import kotlinx.android.synthetic.main.fragment_intervention.*
import kotlinx.android.synthetic.main.fragment_plombier.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.date_add_filter
import kotlinx.android.synthetic.main.fragment_search.recycler_view_Intervention_search
import kotlinx.coroutines.launch

class Search : BaseFragment() {


//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//            arguments?.let {
//                dattte=InterventionFragmentArgs.fromBundle(it).dte
//                if(dattte!=null){
//                    date_add_filter.setText(dattte)
//
//                }
//
//            }
//            val interventions= InterventionDataBase(it).getInterventionDao().getInterventionByDate(dt)
//            recycler_view_Intervention.adapter=InterventionAdapter(interventions)
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view_Intervention_search.setHasFixedSize(true)
        recycler_view_Intervention_search.layoutManager=
            StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        var dattte:String?=null
        arguments?.let {
            dattte=SearchArgs.fromBundle(it).dte
            date_add_filter.setText(dattte)
        }
        var dt=date_add_filter.text.toString().trim()
        launch {
            context?.let {

                val interventions= InterventionDataBase(it).getInterventionDao().getInterventionByDate(dt)
                recycler_view_Intervention_search.adapter=InterventionAdapter(interventions)




            }
        }


}
}

