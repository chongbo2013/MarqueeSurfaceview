package com.ferris.domo;

import android.os.Environment;

/**
 * 桌面的环境管理类
 * @author liwenxue
 *
 */
public final class FlyEnvironment {

		
		private final static String SDCARD = Environment.getExternalStorageDirectory().getPath();
		
		private final static String FILE_PATH = SDCARD + "/Test/";
		
		public final static String LOG = FILE_PATH + "log/";



		
	
}
