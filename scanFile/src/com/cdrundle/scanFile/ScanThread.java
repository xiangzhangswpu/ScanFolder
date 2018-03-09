package com.cdrundle.scanFile;

import java.util.ArrayList;
import java.util.Date;

public class ScanThread extends Thread {
	/**
	 * �Ѿ��ϴ��ı�־
	 */
	private String HAS_UPLOAD;
	/**
	 * �Ѿ�ɨ��ı�־
	 */
	private String HAS_SCAN;
	/**
	 * ��ǰ�����ı���
	 */
	private String MACHINE_CODE;
	/**
	 * �����ɨ��ʱ��
	 */
	private Integer SCAN_TIME; 
	/**
	 * �ϴ��ɹ��ı�־
	 */
	private String UPLOAD_SUCCESS;
	/**
	 * �ļ�ɨ���Ŀ¼
	 */
	private String BASE_FILE_PATH;
	/**
	 * �ӿ������ַ
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
		           //����ɨ��ļ��ʱ��
		            sleep(SCAN_TIME*1000);
		            System.out.println("======ɨ��======"+new Date());
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
