package com.cdrundle.scanFile;

import java.io.File;
import java.util.ArrayList;

/**
 * 文件扫描类
* @ClassName: FolderFileScanner 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author zhangxiang 
* @date 2018年2月28日 下午2:12:02 
*
 */
public class FolderFileScanner {
	
	private static final String EXCEL_XLS = ".xls";  
	private static final String EXCEL_XLSX = ".xlsx";
	
	/**
	 * 采用递归的方式对文件夹进行扫描，根据文件名前缀进行过滤
	* @Title: scanFilesWithRecursion 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param folderPath
	* @param @return
	* @param @throws Exception    设定文件 
	* @return ArrayList<Object>    返回类型 
	* @throws
	 */
	public static ArrayList<Object> scanFilesWithFileName(String folderPath,ArrayList<Object> resultList,String uploadFlag,String scanFlag) throws Exception{
		ArrayList<String> dirctorys = new ArrayList<String>();
        File directory = new File(folderPath);
        if(!directory.isDirectory()){
            throw new Exception('"' + folderPath + '"' + " 不是一个文件目录！");
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
	 * 采用递归的方式对文件夹进行扫描,根据List过滤
	* @Title: scanFilesWithRecursion 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param folderPath
	* @param @return
	* @param @throws Exception    设定文件 
	* @return ArrayList<Object>    返回类型 
	* @throws
	 */
	public static ArrayList<Object> scanFilesWithList(String folderPath,ArrayList<Object> resultList,String hasScanPath) throws Exception{
		ArrayList<String> dirctorys = new ArrayList<String>();
        File directory = new File(folderPath);
        if(!directory.isDirectory()){
            throw new Exception('"' + folderPath + '"' + " 不是一个文件目录！");
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
	 * 已经扫描过的文件增加特定前缀，后续不再进行扫描
	* @Title: reNameHasScanFile 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param oldFileName    设定文件 
	* @return void    返回类型 
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
