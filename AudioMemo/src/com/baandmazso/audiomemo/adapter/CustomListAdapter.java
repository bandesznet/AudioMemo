package com.baandmazso.audiomemo.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import com.baandmazso.audiomemo.R;
import com.baandmazso.audiomemo.R.id;
import com.baandmazso.audiomemo.R.layout;
import com.baandmazso.audiomemo.model.User;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomListAdapter extends BaseAdapter {
	Context mcontext;
	public List<User> list;

	public CustomListAdapter(Context mcontext, List<User> list) {
		this.mcontext = mcontext;
		this.list = list;
	}

	@Override
	public int getCount() {

		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater mInflater = (LayoutInflater) mcontext
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.list_adapter, parent,
					false);
		}

		TextView tv1 = (TextView) convertView.findViewById(R.id.textView1);
		TextView tv2 = (TextView) convertView.findViewById(R.id.textView2);
		
		User sd = list.get(position);
		tv1.setText(sd.getName());
		tv2.setText(String.valueOf(sd.getBirth_year()));

		return convertView;
	}

}