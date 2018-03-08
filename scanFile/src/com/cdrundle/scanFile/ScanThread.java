package com.cdrundle.scanFile;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

public class ScanThread extends Thread {
	private static final String prooertiesFilePath = "src/params.properties";
	public void run() {
        while(true){
				Properties properties = new Properties();
				FileInputStream in;
				int time = 60;
				try {  
		            in = new FileInputStream(prooertiesFilePath);  
		            properties.load(in);//
		            in.close();
		            //String hasScanList = properties.getProperty("HAS_SCAN_FILE_LIST");                                                                                                         
		            //System.out.println("===已经扫描过的文件列表==="+hasScanList);
		            time = Integer.parseInt(properties.getProperty("SCAN_TIME"));
		            String uploadFlag = properties.getProperty("HAS_UPLOAD");
		            String scanFlag = properties.getProperty("HAS_SCAN");
		           //设置扫描的间隔时间
		            sleep(time*1000);
		            System.out.println("======扫描======"+new Date());
		            ArrayList<Object> willScanList = new ArrayList<>();
					//ArrayList<Object> willScanList = FolderFileScanner.scanFilesWithList(properties.getProperty("BASE_FILE_PATH"),willScanList,hasScanList);
		            willScanList = FolderFileScanner.scanFilesWithFileName(properties.getProperty("BASE_FILE_PATH"),willScanList,uploadFlag,scanFlag);
					if(willScanList.size()>0){
						for(Object obj :  willScanList){
							String result = ExplainAndUpload.sendPostFun(String.valueOf(obj),properties.getProperty("MACHINE_CODE"),properties.getProperty("REQUST_URL"));
							if(result.indexOf(properties.getProperty("UPLOAD_SUCCESS"))>-1){
								FolderFileScanner.reNameHasScanFile(String.valueOf(obj), uploadFlag);
							}else{
								FolderFileScanner.reNameHasScanFile(String.valueOf(obj), scanFlag);
							}
							//hasScanList += "["+String.valueOf(obj)+"]";
						}
//						properties.setProperty("HAS_SCAN_FILE_LIST",hasScanList);
//			            FileOutputStream out = new FileOutputStream(prooertiesFilePath);
//			            properties.store(out, "");
//			            out.flush();
//			            out.close();
					}
		        } catch (Exception e) {
		            e.printStackTrace();  
		        }
        }
    }
}
