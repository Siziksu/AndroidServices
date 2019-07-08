package com.siziksu.services.ui.activity

import android.app.ListActivity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import com.siziksu.services.R
import com.siziksu.services.app.Constants
import com.siziksu.services.ui.`object`.Index
import com.siziksu.services.ui.`object`.adapter.IndexAdapter

class MainActivity : ListActivity(), AdapterView.OnItemClickListener {

    private val menu = ArrayList<Index>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        menu.addAll(MenuLoader.getMenu())
        val adapter = IndexAdapter(menu)
        listAdapter = adapter
        listView.onItemClickListener = this
    }

    override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        startActivity(
                Intent(this, menu[position].clazz)
                        .putExtra(Constants.EXTRAS_TITLE, menu[position].title)
                        .putExtra(Constants.EXTRAS_SUMMARY, menu[position].summary)
        )
    }
}
