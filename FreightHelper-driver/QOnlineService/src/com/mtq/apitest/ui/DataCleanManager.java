/*
 * @Title DataCleanManager.java
 * @Copyright Copyright 2010-2014 Careland Software Co,.Ltd All Rights Reserved.
 * @Description 
 * @author Zhouls
 * @date 2015-1-6 9:03:58
 * @version 1.0
 */
package com.mtq.apitest.ui;

import java.io.File;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;


/**
 * * ��Ӧ���������������.
 */
public class DataCleanManager {
	
	/**
	 * * �����Ӧ���ڲ�����(/data/data/com.xxx.xxx/cache) * * @param context
	 *
	 * @param context the context
	 */
	public static void cleanInternalCache(Context context) {
		deleteFilesByDirectory(context.getCacheDir());
	}

	/**
	 * * �����Ӧ���������ݿ�(/data/data/com.xxx.xxx/databases) * * @param context
	 *
	 * @param context the context
	 */
	public static void cleanDatabases(Context context) {
		deleteFilesByDirectory(new File("/data/data/"
				+ context.getPackageName() + "/databases"));
	}

	/**
	 * * �����Ӧ��SharedPreference(/data/data/com.xxx.xxx/shared_prefs) * * @param
	 * context
	 *
	 * @param context the context
	 */
	@SuppressLint("SdCardPath")
	public static void cleanSharedPreference(Context context) {
		deleteFilesByDirectory(new File("/data/data/"
				+ context.getPackageName() + "/shared_prefs"));
	}

	/**
	 * * �����������Ӧ�����ݿ� * * @param context * @param dbName.
	 *
	 * @param context the context
	 * @param dbName the db name
	 */
	public static void cleanDatabaseByName(Context context, String dbName) {
		context.deleteDatabase(dbName);
	}

	/**
	 * * ���/data/data/com.xxx.xxx/files�µ����� * * @param context
	 *
	 * @param context the context
	 */
	public static void cleanFiles(Context context) {
		deleteFilesByDirectory(context.getFilesDir());
	}

	/**
	 * * ����ⲿcache�µ�����(/mnt/sdcard/android/data/com.xxx.xxx/cache) * * @param
	 * context
	 *
	 * @param context the context
	 */
	public static void cleanExternalCache(Context context) {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			deleteFilesByDirectory(context.getExternalCacheDir());
		}
	}

	/**
	 * * ����Զ���·���µ��ļ���ʹ����С�ģ��벻Ҫ��ɾ������ֻ֧��Ŀ¼�µ��ļ�ɾ�� * * @param filePath.
	 *
	 * @param filePath the file path
	 */
	public static void cleanCustomCache(String filePath) {
		deleteFilesByDirectory(new File(filePath));
	}

	/**
	 * * �����Ӧ�����е����� * * @param context * @param filepath.
	 *
	 * @param context the context
	 * @param filepath the filepath
	 */
	public static void cleanApplicationData(Context context, String... filepath) {
		cleanInternalCache(context);
		cleanExternalCache(context);
		cleanDatabases(context);
		cleanSharedPreference(context);
		cleanFiles(context);
		for (String filePath : filepath) {
			cleanCustomCache(filePath);
		}
	}

	/**
	 * * ɾ������ ����ֻ��ɾ��ĳ���ļ����µ��ļ�����������directory�Ǹ��ļ������������� * * @param directory.
	 *
	 * @param directory the directory
	 */
	private static void deleteFilesByDirectory(File directory) {
		if (directory != null && directory.exists() && directory.isDirectory()) {
			for (File item : directory.listFiles()) {
				item.delete();
			}
		}
	}
}
