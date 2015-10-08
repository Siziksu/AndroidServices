package com.siziksu.services.ui.object.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.siziksu.services.R;
import com.siziksu.services.ui.object.Index;

import java.util.List;

public class IndexAdapter extends BaseAdapter {

  private List<Index> index;
  private LayoutInflater inflater;

  public IndexAdapter(Context context, List<Index> index) {
    inflater = LayoutInflater.from(context);
    this.index = index;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    View row;
    if (convertView == null) {
      row = inflater.inflate(R.layout.index_rows, parent, false);
    } else {
      row = convertView;
    }
    if (row != null) {
      TextView text;
      text = (TextView) row.findViewById(R.id.activityTitle);
      text.setText(index.get(position).title);
      text = (TextView) row.findViewById(R.id.summary);
      text.setText(index.get(position).summary);
    }
    return row;
  }

  @Override
  public int getCount() {
    return index.size();
  }

  @Override
  public Object getItem(int position) {
    return index.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }
}