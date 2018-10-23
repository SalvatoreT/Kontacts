package com.saltesta.kontacts.example

import android.Manifest
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.ContactsContract.Contacts
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.app.LoaderManager
import android.support.v4.content.CursorLoader
import android.support.v4.content.Loader
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import com.saltesta.kontacts.example.ContactListViewAdapter.Contact
import kotlinx.android.synthetic.main.activity_contact_list.fab
import kotlinx.android.synthetic.main.activity_contact_list.toolbar

class ContactListActivity : AppCompatActivity() {

  lateinit var contactListViewAdapter: ContactListViewAdapter

  private var contacts: List<Contact> = listOf()

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

    if (ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_CONTACTS
        )
        != PackageManager.PERMISSION_GRANTED
    ) {
      ActivityCompat.requestPermissions(
          this, arrayOf(Manifest.permission.READ_CONTACTS), 123
      )
    } else {
      val loaderManager = LoaderManager.getInstance<AppCompatActivity>(this)
      loaderManager.initLoader(0, null, object : LoaderManager.LoaderCallbacks<Cursor> {
        override fun onCreateLoader(
          id: Int,
          args: Bundle?
        ): Loader<Cursor> {
          // Define the columns to retrieve
          val projectionFields = arrayOf(
              ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME,
              ContactsContract.Contacts.PHOTO_URI
          )
          // Construct the loader
          // the selection criteria
          // Return the loader for use
          return CursorLoader(
              this@ContactListActivity,
              Contacts.CONTENT_URI, // URI
              projectionFields, // the selection args
              null,
              null,
              ContactsContract.Contacts.DISPLAY_NAME
          )
        }

        override fun onLoadFinished(
          loader: Loader<Cursor>,
          cursor: Cursor?
        ) {
          if (cursor?.isClosed == true) return

          contacts = cursor?.use {
            (0 until it.count).map { row ->
              it.moveToPosition(row)
              ContactListViewAdapter.Contact(
                  image = Uri.parse(
                      it.getString(it.getColumnIndex(ContactsContract.Contacts.PHOTO_URI)) ?: ""
                  ),
                  name = it.getString(it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
              )
            }
          } ?: listOf()
          contactListViewAdapter.list = contacts
        }

        override fun onLoaderReset(loader: Loader<Cursor>) {
        }
      })
    }
  }

  override fun onSaveInstanceState(
    outState: Bundle?
  ) {
    outState?.putParcelableArray(CONTACTS, contacts.toTypedArray())
    super.onSaveInstanceState(outState)
  }

  override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
    super.onRestoreInstanceState(savedInstanceState)
    contacts = savedInstanceState?.getParcelableArray(CONTACTS)?.toList() as List<Contact>? ?:
        listOf()
    contactListViewAdapter.list = contacts
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

  companion object {
    const val CONTACTS = "CONTACTS"
  }
}
