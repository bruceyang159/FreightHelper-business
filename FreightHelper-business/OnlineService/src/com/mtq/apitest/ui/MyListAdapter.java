/*
 * @Title MyListAdapter.java
 * @Copyright Copyright 2010-2014 Careland Software Co,.Ltd All Rights Reserved.
 * @Description 
 * @author Zhouls
 * @date 2015-1-6 9:03:58
 * @version 1.0
 */
package com.mtq.apitest.ui;

import java.util.List;

import com.mtq.ols.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;


/**
 * The Class MyListAdapter.
 *
 * @Description ������
 * @author  Zhouls
 * @date  2014-11-4 ����12:48:27
 */
public class MyListAdapter extends BaseAdapter {
	
	/**
	 * The Class ViewHolder.
	 */
	public class ViewHolder {
		
		/** The btn item. */
		public Button btnItem;
	}

	/** The list items. */
	List<MyListItem> listItems;

	/** The list container. */
	private LayoutInflater listContainer;

	/**
	 * Instantiates a new my list adapter.
	 *
	 * @param context the context
	 * @param listItems the list items
	 */
	public MyListAdapter(Context context, List<MyListItem> listItems) {
		listContainer = LayoutInflater.from(context); // ������ͼ����������������
		this.listItems = listItems;
	}

	/**
	 * Gets the count.
	 *
	 * @return the count
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listItems.size();
	}

	/**
	 * Gets the item.
	 *
	 * @param position the position
	 * @return the item
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Gets the item id.
	 *
	 * @param position the position
	 * @return the item id
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Gets the view.
	 *
	 * @param position the position
	 * @param convertView the convert view
	 * @param parent the parent
	 * @return the view
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// �Զ�����ͼ
		ViewHolder viewHolder = null;
		if (convertView == null) {
			// ��ȡlist_item�����ļ�����ͼ
			convertView = listContainer.inflate(R.layout.list_item, null);
			viewHolder = new ViewHolder();
			// ��ȡ�ؼ�����
			viewHolder.btnItem = (Button) convertView.findViewById(R.id.btn);
			// ���ÿؼ�����convertView
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.btnItem.setText(listItems.get(position).getName());
		viewHolder.btnItem.setOnClickListener(listItems.get(position)
				.getListener());
		return convertView;
	}
}
