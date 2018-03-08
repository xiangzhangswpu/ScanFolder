package com.cdrundle.scanFile;


/**
 * 扫描固定文件夹主进程
* @ClassName: ScanFirst 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author zhangxiang 
* @date 2018年2月27日 下午5:38:17 
*
 */
public class ScanFirst {

	public static void main(String[] args) throws Exception {
		
		ScanThread testThread = new ScanThread();
        testThread.start();
	}

}
