package com.siziksu.services.ui.activity

import android.app.ListActivity
import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView.OnItemClickListener
import com.siziksu.services.R
import com.siziksu.services.app.Constants
import com.siziksu.services.ui.`object`.Index
import com.siziksu.services.ui.`object`.adapter.IndexAdapter

class MainActivity : ListActivity() {

    private val menu = ArrayList<Index>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        menu.addAll(MenuLoader.getMenu())
        listAdapter = IndexAdapter(menu)
        listView.onItemClickListener = OnItemClickListener { _, _, position, _ ->
            startActivity(
                    Intent(this, menu[position].clazz)
                            .putExtra(Constants.EXTRAS_TITLE, menu[position].title)
                            .putExtra(Constants.EXTRAS_SUMMARY, menu[position].summary)
            )
        }
    }
}
