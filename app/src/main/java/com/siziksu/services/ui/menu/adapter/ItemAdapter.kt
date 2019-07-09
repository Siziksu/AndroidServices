package com.siziksu.services.ui.menu.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.siziksu.services.R
import com.siziksu.services.ui.menu.Item
import kotlinx.android.synthetic.main.index_rows.view.activityTitle
import kotlinx.android.synthetic.main.index_rows.view.summary

class ItemAdapter(private val items: List<Item>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        val view: View? = convertView ?: LayoutInflater.from(parent.context).inflate(R.layout.index_rows, parent, false)
        view?.apply {
            activityTitle.text = items[position].title
            summary.text = items[position].summary
        }
        return view
    }

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): Any {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}