package com.example.exo5

import android.Manifest
import android.content.ContentResolver
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.telephony.SmsManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.contact_child.view.*
import java.net.URI

class MainActivity : AppCompatActivity() {
    companion object {
        val PERMISSIONS_REQUEST_READ_CONTACTS = 100
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        contact_list.layoutManager=LinearLayoutManager(this)
        val PERMISSION_ALL = 1
        val PERMISSIONS = arrayOf(
            Manifest.permission.READ_CONTACTS,

            Manifest.permission.SEND_SMS

        )

        if (ActivityCompat.checkSelfPermission(
                this,
                PERMISSIONS.toString()
            ) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL)
        }

        btn_read_contact.setOnClickListener {

                var message="SPAM DEFAULT"
            if (messageEditText.text.toString() !=""){
                message=messageEditText.text.toString()
            }
                val contactList: MutableList<ContactDTO> = ArrayList()
                val contacts=contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null)
                if (contacts != null) {
                    while (contacts.moveToNext()){
                        val name=contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                        val number=contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                        val obj=ContactDTO()
                        obj.name=name
                        obj.number=number

                            sendSms(number,message)


                        val photo_uri=contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI))
                        if(photo_uri!=null){
                            obj.image=MediaStore.Images.Media.getBitmap(contentResolver, Uri.parse(photo_uri))
                        }
                        contactList.add(obj)
                    }
                    contacts.close()

                }

                contact_list.adapter=ContactAdapter(contactList,this)




        }

    }
    class ContactAdapter(items : List<ContactDTO>,ctx: Context) : RecyclerView.Adapter<ContactAdapter.ViewHolder>() {

        private var list = items
        private var context = ctx

        override fun getItemCount(): Int {
            return list.size
        }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                LayoutInflater.from(context).inflate(R.layout.contact_child, parent, false)
            )
        }


        class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
            val name = v.tv_name!!
            val number = v.tv_number!!
            val profile = v.iv_profile!!
        }

        override fun onBindViewHolder(holder: ContactAdapter.ViewHolder, position: Int) {
            holder?.name?.text = list[position].name
            holder?.number?.text = list[position].number
            if (list[position].image != null) {
                holder.profile.setImageBitmap(list[position].image)
            } else {
                holder.profile.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.mipmap.ic_launcher_round
                    )
                )
            }
        }
    }
        fun sendSms(number:String,message:String){

              var obj=SmsManager.getDefault()
              obj.sendTextMessage(number,null,message,null,null)
        }



}





