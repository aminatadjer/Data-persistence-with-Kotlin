package com.example.plombier

import android.annotation.TargetApi
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.dashboard.*
import kotlinx.android.synthetic.main.dashboard_content.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
class Dashboard : AppCompatActivity(){
    lateinit var toolbar: Toolbar
    var interventionManager: InterventionManager = InterventionManager()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard)
        toolbar = findViewById(R.id.dashboard_toolbar)
        setSupportActionBar(toolbar)
        rv_dashboard.layoutManager = LinearLayoutManager(this)
        fab.setOnClickListener { view ->
            val dialog = AlertDialog.Builder(this)
            val view = layoutInflater.inflate(R.layout.layout_dialogue, null)
            val plombier = view.findViewById<Spinner>(R.id.ev_plombier)
            val type = view.findViewById<Spinner>(R.id.ev_type)

            ArrayAdapter.createFromResource(
                this,
                R.array.plumbers,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                plombier.adapter = adapter
            }

            ArrayAdapter.createFromResource(
                this,
                R.array.type,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                type.adapter = adapter
            }


            val datePicker = view.findViewById<DatePicker>(R.id.dp_interventions)
            dialog.setView(view)
            dialog.setPositiveButton("Add") { _: DialogInterface, _: Int ->
                    val intervention = Intervention()
                    intervention.plombier = plombier.selectedItem.toString()
                    intervention.type = type.selectedItem.toString()
                    val calendar: Calendar = Calendar.getInstance()
                    calendar.set(datePicker.year, datePicker.month, datePicker.dayOfMonth)
                    val sdf = SimpleDateFormat("dd-MM-yyyy")
                    intervention.date = sdf.format(calendar.time)
                    interventionManager.addIntervention(filesDir.toString(),intervention)
                    refresh(interventionManager.getInterventions(filesDir.toString()))

            }
            dialog.setNegativeButton("Cancel") { _: DialogInterface, _: Int ->
            }
            dialog.show()
        }


        fab_search.setOnClickListener{
            val dialog = AlertDialog.Builder(this)
            val view = layoutInflater.inflate(R.layout.search, null)
            val dateSearch = view.findViewById<DatePicker>(R.id.date_search)
            dialog.setView(view)
            dialog.setPositiveButton("rechercher") { _: DialogInterface, _: Int ->
                val calendar: Calendar = Calendar.getInstance()
                calendar.set(dateSearch.year, dateSearch.month, dateSearch.dayOfMonth)
                val sdf = SimpleDateFormat("dd-MM-yyyy")
                val date = sdf.format(calendar.time)
                refresh(interventionManager.searchPerDate(filesDir.toString(),date))
            }
            dialog.setNegativeButton("Cancel") { _: DialogInterface, _: Int ->
            }
            dialog.show()

        }
    }

    override fun onResume() {
        refresh(interventionManager.getInterventions(filesDir.toString()))
        super.onResume()
    }
    fun refresh(interventions:MutableList<Intervention>){
        rv_dashboard.adapter = DashboardAdapter(this,interventions)
    }
    class DashboardAdapter(val activity: Dashboard , val list: MutableList<Intervention>) :
        RecyclerView.Adapter<DashboardAdapter.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(activity).inflate(R.layout.item, parent, false))
        }

        override fun getItemCount(): Int {
            return list.size
        }

        @TargetApi(Build.VERSION_CODES.O)
        @RequiresApi(Build.VERSION_CODES.N)
        override fun onBindViewHolder(holder: ViewHolder, p1: Int) {
            holder.plombier.text = list[p1].plombier
            holder.date.text = list[p1].date
            holder.type.text = list[p1].type
            val dir = activity.filesDir.toString()

            holder.deleteBtn.setOnClickListener{
                activity.refresh(activity.interventionManager.deleteInterventions(dir,list[p1].num))
            }

            holder.editBtn.setOnClickListener{
                val dialog = AlertDialog.Builder(activity)
                val view = activity.layoutInflater.inflate(R.layout.layout_dialogue, null)
                dialog.setView(view)
                val plombier = view.findViewById<Spinner>(R.id.ev_plombier)
                val type = view.findViewById<Spinner>(R.id.ev_type)
                var arrayAdapter = ArrayAdapter.createFromResource(
                    activity,
                    R.array.plumbers,
                    android.R.layout.simple_spinner_item
                ).also { adapter ->
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    plombier.adapter = adapter
                }
                plombier.setSelection(arrayAdapter.getPosition(list[p1].plombier))
                arrayAdapter =ArrayAdapter.createFromResource(
                    activity,
                    R.array.type,
                    android.R.layout.simple_spinner_item
                ).also { adapter ->
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    type.adapter = adapter
                }
                type.setSelection(arrayAdapter.getPosition(list[p1].type))

                val datePicker = view.findViewById<DatePicker>(R.id.dp_interventions)
                val date = LocalDate.parse(list[p1].date, DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                datePicker.updateDate(date.year,date.monthValue-1,date.dayOfMonth)
                dialog.setPositiveButton("modifier") { _: DialogInterface, _: Int ->
                    val dir = activity.filesDir.toString()
                    var intervention = Intervention()
                    intervention.type = type.selectedItem.toString()
                    intervention.plombier = plombier.selectedItem.toString()
                    val calendar: Calendar = Calendar.getInstance()
                    calendar.set(datePicker.year, datePicker.month, datePicker.dayOfMonth)
                    val sdf = SimpleDateFormat("dd-MM-yyyy")
                    intervention.date = sdf.format(calendar.time)
                    activity.refresh(activity.interventionManager.editIntervention(dir,p1,intervention))
                }
                dialog.setNegativeButton("annuler") { _: DialogInterface, _: Int ->
                }
                dialog.show()
            }
        }

        class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
            val plombier:TextView = v.findViewById(R.id.tv_plombier)
            val date:TextView = v.findViewById(R.id.tv_date)
            val type:TextView = v.findViewById(R.id.tv_type)
            val deleteBtn : FloatingActionButton = v.findViewById(R.id.rv_delete)
            val editBtn : FloatingActionButton = v.findViewById(R.id.rv_edit)


        }

    }
}