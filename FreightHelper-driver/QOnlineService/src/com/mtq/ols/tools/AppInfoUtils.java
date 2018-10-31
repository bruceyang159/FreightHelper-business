package com.mtq.ols.tools;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.util.DisplayMetrics;

public class AppInfoUtils {
	
	public static String getPackName(Context context) {
		return context.getPackageName();
	}
	
	
	/**
	 * @Title: getAppName
	 * @Description: ��ȡapp����
	 * @param context
	 */
	public static String getAppName(Context context) {
		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(
					context.getPackageName(), 0);
			int labelRes = packageInfo.applicationInfo.labelRes;
			return context.getResources().getString(labelRes);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * ͨ��pkgname��ȡ�汾��VerCode
	 * 
	 * @param context
	 * @param pkgname
	 */
	public static int getVerCode(Context context) {
		int verCode = 0;
		String pkgname = context.getPackageName();
		PackageManager pm = context.getPackageManager();
		PackageInfo packageInfo;
		try {
			packageInfo = pm.getPackageInfo(pkgname,
					PackageManager.GET_PERMISSIONS);
			if (packageInfo != null) {
				verCode = packageInfo.versionCode;
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return verCode;
	}

	/**
	 * ͨ��pkgname��ȡ�汾��VerName
	 * 
	 * @param context
	 * @param pkgname
	 */
	public static String getVerName(Context context) {
		String verName = "";
		String pkgname = context.getPackageName();
		PackageManager pm = context.getPackageManager();
		PackageInfo packageInfo;
		try {
			packageInfo = pm.getPackageInfo(pkgname,
					PackageManager.GET_PERMISSIONS);
			if (packageInfo != null) {
				verName = packageInfo.versionName;
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return verName;
	}

	/**
	 * @Title: getSystemVer
	 * @Description: ��ȡandroidϵͳ�汾��
	 */
	public static String getSystemVer() {
		String version_release = Build.VERSION.RELEASE; // ϵͳ�汾��
		return version_release;
	}

	/**
	 * @Title: getDensityWidth
	 * @Description: ��ȡDensityWidth
	 * @param context
	 */
	public static int getDensityWidth(Context context) {
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		return dm.widthPixels;
	}

	/**
	 * @Title: getDensityHeight
	 * @Description: ��ȡDensityHeight
	 * @param context
	 */
	public static int getDensityHeight(Context context) {
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		return dm.heightPixels;
	}

	/**
	 * @Title: getDensityDpi
	 * @Description: ��ȡDensityDpi
	 * @param context
	 */
	public static int getDensityDpi(Context context) {
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		return dm.densityDpi;
	}

	/**
	 * @Title: getDensity
	 * @Description: ��ȡDensity
	 * @param context
	 */
	public static float getDensity(Context context) {
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		return dm.density;
	}
}
