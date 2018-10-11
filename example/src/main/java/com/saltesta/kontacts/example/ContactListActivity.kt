package com.saltesta.kontacts.example

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.TextView

import kotlinx.android.synthetic.main.activity_contact_list.*

class ContactListActivity : AppCompatActivity() {

  lateinit var contactListViewAdapter: ContactListViewAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_contact_list)
    setSupportActionBar(toolbar)

    fab.setOnClickListener { view ->
      Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
          .setAction("Action", null)
          .show()
    }

    contactListViewAdapter = ContactListViewAdapter()
    findViewById<RecyclerView>(R.id.contact_list).apply {
      setHasFixedSize(true)
      layoutManager = LinearLayoutManager(this@ContactListActivity)
      adapter = contactListViewAdapter
    }

    // TODO set the contact list values
    // contactListViewAdapter.list = // something
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    // Inflate the menu; this adds items to the action bar if it is present.
    menuInflater.inflate(R.menu.menu_contact_list, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    return when (item.itemId) {
      R.id.action_settings -> true
      else -> super.onOptionsItemSelected(item)
    }
  }
}
