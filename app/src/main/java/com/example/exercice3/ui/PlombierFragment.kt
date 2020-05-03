package com.example.exercice3.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation

import com.example.exercice3.R
import com.example.exercice3.db.InterventionDataBase
import kotlinx.android.synthetic.main.fragment_plombier.*
import kotlinx.coroutines.launch
import androidx.recyclerview.widget.StaggeredGridLayoutManager


/**
 * A simple [Fragment] subclass.
 */
class PlombierFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_plombier, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recycler_view_Plombier.setHasFixedSize(true)
        recycler_view_Plombier.layoutManager=StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL)
        launch {
            context?.let {
                val plombiers=InterventionDataBase(it).getPlombierDao().getAllPlumbers()
                recycler_view_Plombier.adapter=PlombierAdapter(plombiers)
            }
        }







        button_add_plombier.setOnClickListener{
            val action=PlombierFragmentDirections.actionAddPlombier()
            Navigation.findNavController(it).navigate(action)
        }
    }


}
