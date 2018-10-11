package com.saltesta.kontacts.example

import android.graphics.drawable.Drawable
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.saltesta.kontacts.example.ContactListViewAdapter.ContactListRow
import com.saltesta.kontacts.example.R.layout

class ContactListViewAdapter : RecyclerView.Adapter<ContactListRow>() {
  class ContactListRow(
    val constraintLayout: ConstraintLayout
  ) : RecyclerView.ViewHolder(constraintLayout)

  class Contact(
    val image: Drawable?,
    val name: CharSequence
  )

  var list: List<Contact> = listOf()
    set(value) {
      field = value
      notifyDataSetChanged()
    }

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ) = ContactListRow(
      LayoutInflater.from(parent.context).inflate(
          layout.contact_list_row, parent, false
      ) as ConstraintLayout
  )

  override fun getItemCount(): Int = list.count()

  override fun onBindViewHolder(
    holder: ContactListRow,
    position: Int
  ) {
    val profileImage = holder.constraintLayout.getViewById(R.id.row_profile_image) as ImageView
    val profileName = holder.constraintLayout.getViewById(R.id.row_profile_name) as TextView

    list[position].apply {
      profileImage.setImageDrawable(image)
      profileName.text = name
    }
  }
}