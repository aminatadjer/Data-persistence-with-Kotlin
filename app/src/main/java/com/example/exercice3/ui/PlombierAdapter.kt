package com.example.exercice3.ui





import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.exercice3.R
import com.example.exercice3.db.entities.Plombier
import kotlinx.android.synthetic.main.plombier_layout.view.*


class PlombierAdapter(private val plumbers:List<Plombier>):RecyclerView.Adapter<PlombierAdapter.PlumberViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlumberViewHolder {
        return PlumberViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.plombier_layout,parent,false)
        )
    }

    override fun getItemCount(): Int =plumbers.size

    override fun onBindViewHolder(holder: PlumberViewHolder, position: Int) {
        holder.view.textView_nomPlombier.text=plumbers[position].Nom
        holder.view.textView_prenomPlombier.text=plumbers[position].Prenom
        holder.view.textView_exper.text=plumbers[position].Experience
        holder.view.setOnClickListener{
            val action=PlombierFragmentDirections.actionAddPlombier()
            action.plumber=plumbers[position]
            Navigation.findNavController(it).navigate(action)
        }
    }

    class PlumberViewHolder(val view:View):RecyclerView.ViewHolder(view)
}