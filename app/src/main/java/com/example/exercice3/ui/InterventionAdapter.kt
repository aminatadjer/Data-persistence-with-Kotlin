package com.example.exercice3.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.exercice3.R
import com.example.exercice3.db.InterventionDataBase
import com.example.exercice3.db.entities.Intervention
import kotlinx.android.synthetic.main.intervention_layout.view.*
import kotlinx.android.synthetic.main.plombier_layout.view.*

class InterventionAdapter(private val interventions:List<Intervention>): RecyclerView.Adapter<InterventionAdapter.InterventionViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InterventionAdapter.InterventionViewHolder {
        return InterventionAdapter.InterventionViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.intervention_layout, parent, false)
        )
    }
    override fun getItemCount(): Int =interventions.size

    override fun onBindViewHolder(holder: InterventionViewHolder, position: Int) {
        holder.view.textView_date.text=interventions[position].date
        holder.view.textView_num.text=interventions[position].num
        holder.view.textView_type.text=interventions[position].type
        holder.view.textView_NomP.text=interventions[position].plombier
        holder.view.setOnClickListener{

            val action=InterventionFragmentDirections.actionAddIntervention()
            action.intervention=interventions[position]
            Navigation.findNavController(it).navigate(action)

        }
    }

    class InterventionViewHolder(val view: View):RecyclerView.ViewHolder(view)

}