package com.siziksu.services.ui.`object`.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.siziksu.services.R
import com.siziksu.services.ui.`object`.Index
import kotlinx.android.synthetic.main.index_rows.view.activityTitle
import kotlinx.android.synthetic.main.index_rows.view.summary

class IndexAdapter(private val index: List<Index>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        val view: View? = convertView ?: LayoutInflater.from(parent.context).inflate(R.layout.index_rows, parent, false)
        view?.apply {
            activityTitle.text = index[position].title
            summary.text = index[position].summary
        }
        return view
    }

    override fun getCount(): Int {
        return index.size
    }

    override fun getItem(position: Int): Any {
        return index[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}