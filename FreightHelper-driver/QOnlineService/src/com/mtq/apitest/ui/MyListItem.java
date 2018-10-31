/*
 * @Title MyListItem.java
 * @Copyright Copyright 2010-2014 Careland Software Co,.Ltd All Rights Reserved.
 * @Description 
 * @author Zhouls
 * @date 2015-1-6 9:03:58
 * @version 1.0
 */
package com.mtq.apitest.ui;

import android.view.View.OnClickListener;


/**
 * The Class MyListItem.
 *
 * @Description ��ť
 * @author  Zhouls
 * @date  2014-11-4 ����12:59:45
 */
public class MyListItem {
	
	/** The name. */
	private String name;
	
	/** The listener. */
	private OnClickListener listener;

	/**
	 * Instantiates a new my list item.
	 *
	 * @param name the name
	 * @param listener the listener
	 */
	public MyListItem(String name, OnClickListener listener) {
		this.name = name;
		this.listener = listener;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the listener.
	 *
	 * @return the listener
	 */
	public OnClickListener getListener() {
		return listener;
	}

}
