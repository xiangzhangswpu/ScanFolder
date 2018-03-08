package com.cdrundle.scanFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class ExplainAndUpload {
	
	private static final String paramsMacine = "machineCode";
	private static final String paramsfileByteStr = "fileByteInfo";
	private static final String paramsFileName = "fileName";
	private static final String success = "success";
	private static final String failure = "failure";
	
	public static String sendPostFun(String filePath,String machineCode,String requestURL) {
		try{
			String Result = success;
			CloseableHttpClient httpclient = HttpClients.createDefault();
    	    List<NameValuePair> formparams = new ArrayList<NameValuePair>();
    	    formparams.add(new BasicNameValuePair(paramsMacine, machineCode));
    	    
    	    /***************************根据File获取byte[]**************************/
            byte[] buffer = null;
            try{
                File file = new File(filePath);
                formparams.add(new BasicNameValuePair(paramsFileName, file.getName()));
                FileInputStream fis = new FileInputStream(file);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] b = new byte[1024];
                int n;
                while ((n = fis.read(b)) != -1)
                {
                    bos.write(b, 0, n);
                }
                fis.close();
                bos.close();
                buffer = bos.toByteArray();
            }catch (FileNotFoundException e){
            	Result = failure;
                e.printStackTrace();
            }catch (IOException e){
            	Result = failure;
                e.printStackTrace();
            }
            try {
    			formparams.add(new BasicNameValuePair(paramsfileByteStr, new String(buffer,"ISO-8859-1")));
    		} catch (UnsupportedEncodingException e1) {
    			Result = failure;
    			e1.printStackTrace();
    		}
            /***************************根据File获取byte[]**************************/
    	    
    	    UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
    	    HttpPost httppost = new HttpPost(requestURL);
    	    httppost.setEntity(entity);
    	    CloseableHttpResponse response = null;
    	    try {
    	        response = httpclient.execute(httppost);
    	    } catch (IOException e) {
    	    	Result = failure;
    	        e.printStackTrace();
    	    }
    	    HttpEntity entity1 = response.getEntity();
    	    try {
    	    	Result = EntityUtils.toString(entity1);
    	    } catch (ParseException | IOException e) {
    	    	Result = failure;
    	        e.printStackTrace();
    	    }
    	    System.out.println("=========="+Result);
    	    return Result;
		}catch(Exception e){
			return failure;
		}
	}
	
}
