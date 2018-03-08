package com.cdrundle.scanFile;

import java.io.File;
import java.util.ArrayList;

/**
 * �ļ�ɨ����
* @ClassName: FolderFileScanner 
* @Description: TODO(������һ�仰��������������) 
* @author zhangxiang 
* @date 2018��2��28�� ����2:12:02 
*
 */
public class FolderFileScanner {
	
	private static final String EXCEL_XLS = ".xls";  
	private static final String EXCEL_XLSX = ".xlsx";
	
	/**
	 * ���õݹ�ķ�ʽ���ļ��н���ɨ�裬�����ļ���ǰ׺���й���
	* @Title: scanFilesWithRecursion 
	* @Description: TODO(������һ�仰�����������������) 
	* @param @param folderPath
	* @param @return
	* @param @throws Exception    �趨�ļ� 
	* @return ArrayList<Object>    �������� 
	* @throws
	 */
	public static ArrayList<Object> scanFilesWithFileName(String folderPath,ArrayList<Object> resultList,String uploadFlag,String scanFlag) throws Exception{
		ArrayList<String> dirctorys = new ArrayList<String>();
        File directory = new File(folderPath);
        if(!directory.isDirectory()){
            throw new Exception('"' + folderPath + '"' + " ����һ���ļ�Ŀ¼��");
        }
        if(directory.isDirectory()){
            File [] filelist = directory.listFiles();
            for(int i = 0; i < filelist.length; i ++){
                if(filelist[i].isDirectory()){
                    dirctorys.add(filelist[i].getAbsolutePath());
                    scanFilesWithFileName(filelist[i].getAbsolutePath(),resultList,uploadFlag,scanFlag);
                }else{
                	String nowFilePath = filelist[i].getAbsolutePath();
                	String nowFileName = filelist[i].getName();
                	if((nowFileName.endsWith(EXCEL_XLS) || nowFileName.endsWith(EXCEL_XLSX)) && !(nowFileName.startsWith("["+uploadFlag+"]") || nowFileName.startsWith("["+scanFlag+"]")) ){
                		resultList.add(nowFilePath);
                	}
                }
            }
        }
        return resultList;
    }
	
	/**
	 * ���õݹ�ķ�ʽ���ļ��н���ɨ��,����List����
	* @Title: scanFilesWithRecursion 
	* @Description: TODO(������һ�仰�����������������) 
	* @param @param folderPath
	* @param @return
	* @param @throws Exception    �趨�ļ� 
	* @return ArrayList<Object>    �������� 
	* @throws
	 */
	public static ArrayList<Object> scanFilesWithList(String folderPath,ArrayList<Object> resultList,String hasScanPath) throws Exception{
		ArrayList<String> dirctorys = new ArrayList<String>();
        File directory = new File(folderPath);
        if(!directory.isDirectory()){
            throw new Exception('"' + folderPath + '"' + " ����һ���ļ�Ŀ¼��");
        }
        if(directory.isDirectory()){
            File [] filelist = directory.listFiles();
            for(int i = 0; i < filelist.length; i ++){
                if(filelist[i].isDirectory()){
                    dirctorys.add(filelist[i].getAbsolutePath());
                    scanFilesWithList(filelist[i].getAbsolutePath(),resultList,hasScanPath);
                }else{
                	String nowFilePath = filelist[i].getAbsolutePath();
                	if(nowFilePath.endsWith(EXCEL_XLS) || nowFilePath.endsWith(EXCEL_XLSX)){
                		if(!(hasScanPath.indexOf("["+nowFilePath+"]")>-1)){
                			resultList.add(nowFilePath);
                    	}
                	}
                }
            }
        }
        return resultList;
    }
	
	/**
	 * �Ѿ�ɨ������ļ������ض�ǰ׺���������ٽ���ɨ��
	* @Title: reNameHasScanFile 
	* @Description: TODO(������һ�仰�����������������) 
	* @param @param oldFileName    �趨�ļ� 
	* @return void    �������� 
	* @throws
	 */
	public static void reNameHasScanFile(String oldFilePath,String fileFlag){
		 File toBeRenamed = new File(oldFilePath);
		 String parentPath = toBeRenamed.getParent();
		 String fileName = toBeRenamed.getName();
		 File newFile = new File(parentPath+File.separator+"["+fileFlag+"]"+fileName);
		 toBeRenamed.renameTo(newFile);
	}
	
}
