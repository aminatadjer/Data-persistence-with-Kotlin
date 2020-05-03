package com.example.exercice3.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation

import com.example.exercice3.R
import kotlinx.android.synthetic.main.fragment_home2.*


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home2, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        plombierButt.setOnClickListener{
            val action=HomeFragmentDirections.actionPlombier()
            Navigation.findNavController(it).navigate(action)
        }
        interventionButt.setOnClickListener{
            val action2=HomeFragmentDirections.actionIntervention()
            Navigation.findNavController(it).navigate(action2)
        }
    }


}
