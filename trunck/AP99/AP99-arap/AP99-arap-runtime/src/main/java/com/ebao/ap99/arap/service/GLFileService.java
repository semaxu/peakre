package com.ebao.ap99.arap.service;

import java.util.List;

import com.ebao.ap99.arap.entity.GLFileInfo;

public interface GLFileService {
	public static final String GL_FILE_FOLDER = "GL_file_folder";
	public static final String GL_FILE_PREFIX = "Peakre General Ledger Data_";
	public static final String GL_FILE_SPLIT = "|";
    public static final Integer GL_FILE_NUM_LEN = 3;
    public static final String FILE_NEW_LINE = System.getProperty("line.separator");
	
	/**
	 * Generate GL file to be posted
	 * @return generated file file list
	 * @throws Exception
	 */
	public List<GLFileInfo> generateGLFile() throws Exception;
	
	/**
	 * Handle returned GL file
	 * @throws Exception
	 */
	public void hanleReturnedGLFile() throws Exception;
}
