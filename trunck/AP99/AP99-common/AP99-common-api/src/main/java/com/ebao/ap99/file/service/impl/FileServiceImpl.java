package com.ebao.ap99.file.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.ebao.ap99.file.entity.TDocument;
import com.ebao.ap99.file.entity.DocumentRule;
import com.ebao.ap99.file.service.CalService;
import com.ebao.ap99.file.service.DocumentService;
import com.ebao.ap99.file.service.FileService;
import com.ebao.ap99.parent.context.AppContext;
import com.ebao.unicorn.platform.foundation.context.impl.SpringContextManager;
import com.ebao.unicorn.platform.foundation.utils.Assert;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FileServiceImpl implements FileService {
	
	@Autowired
	private DocumentService documentService;
	
	ApplicationContext applicationContext = SpringContextManager.getApplicationContext();
	
	@Override
	public File writePhysicalFile(InputStream inputStream, String absoluteFileName) throws IOException {        
	    File file = makeFile(absoluteFileName);
	    FileOutputStream fileOutputStream = new FileOutputStream(file);
	    try {
	        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	        Streams.copy(inputStream, byteArrayOutputStream, true);
	        fileOutputStream.write(byteArrayOutputStream.toByteArray());
	        fileOutputStream.flush();
	    } finally {
	        fileOutputStream.close();
	    }
	    return file;
	}	

    @Override
	public void makeDir(String absolutePath) {
        // create folder
        File file = new File(absolutePath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

	@Override
	public File makeFile(String absoluteFileName) throws IOException {
		absoluteFileName = ensureAbsolute(absoluteFileName);
	    int positon = absoluteFileName.lastIndexOf(File.separator);     
	    String absolutePath = absoluteFileName.substring(0, positon + 1);
	    makeDir(absolutePath);
	    
	    File file = new File(absoluteFileName);
	    if (!file.exists()) {
	        file.createNewFile();
	    }
	    return file;
	}

	@Override
	public String generateFileName(TDocument document) {
	    if (document.getDocumentId() == 0)
	        documentService.saveDocument(document);
	
	    return document.getDocumentId() + "." + parseExtension(document.getFileName());
	}
	
    /**
     * [MiddlePath]/YYYYMM/BusiType/DocType/
     * 
     * @param businessType
     * @return absolute path
     */
    @Override
	public String generateRelativePath(String businessType, String docType, String middlePath) {
        Assert.isNotEmpty(middlePath, "Middle Path is necessary.");

        StringBuffer filePath = new StringBuffer(middlePath).append(File.separator)
                .append(new SimpleDateFormat("yyyyMM").format(AppContext.getSysDate()));

        if (StringUtils.isNotBlank(businessType))
            filePath.append(File.separator).append("biz-" + businessType);

        if (StringUtils.isNotBlank(docType))
            filePath.append(File.separator).append("doc-" + docType);

        return filePath.toString();
    }

    @Override
	public String parseExtension(String fileName) {
        if (StringUtils.isEmpty(fileName) || fileName.indexOf(".") < 0)
            return "";

        String[] temp = StringUtils.split(fileName, ".");
        return temp[temp.length - 1];
    }
    
    @Override
	public File readPhysicalFileContent(String pathname) throws FileNotFoundException, IOException {
        File f = new File(ensureAbsolute(pathname));

        // read file content
        FileInputStream ins = new FileInputStream(f);
        try {
            int countLen = ins.available();
            byte[] m_binArray = new byte[countLen];
            ins.read(m_binArray);
        } finally {
            ins.close();
        }
        return f;
    }
    
    private String ensureAbsolute(String pathname) {
		//if (pathname.startsWith(File.separator) || pathname.contains(":"))
			return pathname;
//		else
//			return FileService.FILE_ROOT_PATH + File.separator + pathname;
	}

	/**
     * Generate physical file
     * @param document
     * @return absolute file path
     * @throws Exception
     */
    @Override
	public <T> String generatePhysicalFile(TDocument document) throws Exception {      
        DocumentRule docConfig = documentService.findDocumentRule(document.getBusinessType());     
        CalService dataProvider = (CalService) applicationContext.getBean(docConfig.getParseService());
        String para = document.getPara();
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> paramMap = mapper.readValue(para, new TypeReference<Map<String, String>>() {});
        List<T> dataList = dataProvider.bizProcess(paramMap);
        String filePath = documentService.writeDocument(dataList, docConfig);
        return filePath;
    }

    
    @Override
	public String generateFileByURL(TDocument document) throws Exception {
        //generate physical file
        String relativePath = generateRelativePath(document.getBusinessType(), null,
                FileService.FILE_PATH_DOCUMENT_OUTPUT);
        String fileName = document.getDocumentId() + ".pdf";
        String relativeFileName = relativePath + File.separator + fileName;
        String fullPathName = FileService.FILE_ROOT_PATH + File.separator+relativeFileName;
        
        relativeFileName = relativeFileName.replace("\\", "/");
        
        downLoadFromUrl(document.getFileUrl(), fullPathName);
        
        return relativeFileName;
    }

    /**
     * downLoadFromUrl
     * 
     * @param urlStr
     * @param pathname
     * @throws Exception
     */
    @Override
	public void downLoadFromUrl(String urlStr, String pathname) throws Exception {
        InputStream inputStream = null;
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setConnectTimeout(3 * 1000);
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

            inputStream = conn.getInputStream();    
            
            writePhysicalFile(inputStream, pathname);           
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

	
}
