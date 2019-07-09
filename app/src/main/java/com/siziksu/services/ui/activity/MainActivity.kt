package com.siziksu.services.ui.activity

import android.app.ListActivity
import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView.OnItemClickListener
import com.siziksu.services.R
import com.siziksu.services.app.Constants
import com.siziksu.services.ui.menu.Item
import com.siziksu.services.ui.menu.MenuLoader
import com.siziksu.services.ui.menu.adapter.ItemAdapter

class MainActivity : ListActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val menu = ArrayList<Item>()
        menu.addAll(MenuLoader.getMenu())
        listAdapter = ItemAdapter(menu)
        listView.onItemClickListener = OnItemClickListener { _, _, position, _ ->
            startActivity(
                    Intent(this, menu[position].clazz)
                            .putExtra(Constants.EXTRAS_TITLE, menu[position].title)
                            .putExtra(Constants.EXTRAS_SUMMARY, menu[position].summary)
            )
        }
    }
}
