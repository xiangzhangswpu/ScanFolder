package com.cdrundle.scanFile;

import java.util.ArrayList;
import java.util.Date;

public class ScanThread extends Thread {
	/**
	 * 已经上传的标志
	 */
	private String HAS_UPLOAD;
	/**
	 * 已经扫描的标志
	 */
	private String HAS_SCAN;
	/**
	 * 当前机器的编码
	 */
	private String MACHINE_CODE;
	/**
	 * 间隔的扫描时间
	 */
	private Integer SCAN_TIME; 
	/**
	 * 上传成功的标志
	 */
	private String UPLOAD_SUCCESS;
	/**
	 * 文件扫描根目录
	 */
	private String BASE_FILE_PATH;
	/**
	 * 接口请求地址
	 */
	private String REQUST_URL;
	
	public ScanThread(String hasUpload,String hasScan,String machineCode,Integer scanTime,String uploadSuccess,String baseFilePath,String requstUrl){
		this.HAS_UPLOAD = hasUpload;
		this.HAS_SCAN = hasScan;
		this.MACHINE_CODE = machineCode;
		this.SCAN_TIME = scanTime;
		this.UPLOAD_SUCCESS = uploadSuccess;
		this.BASE_FILE_PATH = baseFilePath;
		this.REQUST_URL = requstUrl;
	}
	
	
	public void run() {
        while(true){
				try {  
		           //设置扫描的间隔时间
		            sleep(SCAN_TIME*1000);
		            System.out.println("======扫描======"+new Date());
		            ArrayList<Object> willScanList = new ArrayList<>();
		            willScanList = FolderFileScanner.scanFilesWithFileName(BASE_FILE_PATH,willScanList,HAS_UPLOAD,HAS_SCAN);
					if(willScanList.size()>0){
						for(Object obj :  willScanList){
							String result = ExplainAndUpload.sendPostFun(String.valueOf(obj),MACHINE_CODE,REQUST_URL);
							if(result.indexOf(UPLOAD_SUCCESS)>-1){
								FolderFileScanner.reNameHasScanFile(String.valueOf(obj), HAS_UPLOAD);
							}else{
								FolderFileScanner.reNameHasScanFile(String.valueOf(obj), HAS_SCAN);
							}
						}
					}
		        } catch (Exception e) {
		            e.printStackTrace();  
		        }
        }
    }
}
