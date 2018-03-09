package com.cdrundle.scanFile;

import java.io.IOException;
import java.util.Properties;

/**
 * 扫描固定文件夹主进程
* @ClassName: ScanFirst 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author zhangxiang 
* @date 2018年2月27日 下午5:38:17 
*
 */
public class ScanFirst {
	private static final String prooertiesFilePath = "/params.properties";

	public static void main(String[] args) throws Exception {
		
		ScanFirst initScan = new ScanFirst();
		ScanThread initThread = initScan.loadPropertiesFile();
		if(initThread != null){
			initThread.start();
		}else{
			System.out.println("初始化任务线程失败，请检查！");
		}
	}
	
	public ScanThread loadPropertiesFile(){
		ScanThread LoadPropertiesThread = null;
		Properties properties = new Properties();
		try {
			properties.load(this.getClass().getResourceAsStream(prooertiesFilePath));
			LoadPropertiesThread = new ScanThread(properties.getProperty("HAS_UPLOAD")
					,properties.getProperty("HAS_SCAN")
					,properties.getProperty("MACHINE_CODE")
					,Integer.parseInt(properties.getProperty("SCAN_TIME"))
					,properties.getProperty("UPLOAD_SUCCESS")
					,properties.getProperty("BASE_FILE_PATH")
					,properties.getProperty("REQUST_URL"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return LoadPropertiesThread;
	}

}
