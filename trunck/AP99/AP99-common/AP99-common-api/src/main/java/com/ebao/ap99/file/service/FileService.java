package com.ebao.ap99.file.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.ebao.ap99.file.entity.TDocument;
import com.ebao.unicorn.platform.foundation.cfg.Env;

public interface FileService {

	String FILE_ROOT_PATH = Env.getParameter("FILE_PATH");
	String FILE_PATH_DOCUMENT_OUTPUT = "Document" + File.separator + "Output";
	String FILE_PATH_DOCUMENT_INPUT = "Document" + File.separator + "Input";
	String FILE_PATH_ATTACHMENT = "Attachment";	
    String FILE_PATH_TEMPLATE = "Template" + File.separator + "Excel";
	public void downLoadFromUrl(String urlStr, String pathname) throws Exception;

	public String generateFileByURL(TDocument document) throws Exception;

	public <T> String generatePhysicalFile(TDocument document) throws Exception;

	public File readPhysicalFileContent(String pathname) throws FileNotFoundException, IOException;

	public String parseExtension(String fileName);

	public String generateRelativePath(String businessType, String docType, String middlePath);

	public String generateFileName(TDocument document);

	public File makeFile(String absoluteFileName) throws IOException;

	public void makeDir(String absolutePath);

	public File writePhysicalFile(InputStream inputStream, String absoluteFileName) throws IOException;
	
}
