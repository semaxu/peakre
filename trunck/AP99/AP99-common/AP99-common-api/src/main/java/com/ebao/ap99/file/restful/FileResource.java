package com.ebao.ap99.file.restful;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.lang3.StringUtils;
import org.restlet.data.Disposition;
import org.restlet.ext.fileupload.RestletFileUpload;
import org.restlet.representation.FileRepresentation;
import org.restlet.representation.Representation;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.file.entity.DocumentRule;
import com.ebao.ap99.file.entity.TDocument;
import com.ebao.ap99.file.service.DocumentService;
import com.ebao.ap99.file.service.FileService;
import com.ebao.ap99.file.service.PrintService;
import com.ebao.unicorn.platform.foundation.restful.annotation.Module;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

@Module(Module.PUBLIC)
@Path("/file")
public class FileResource {

    @Autowired
	private DocumentService documentService;
    
    @Autowired
	private FileService fileService;
    
    @Autowired
    private PrintService printService;
    
    public FileResource() {

    }

    private static Logger logger = LoggerFactory.getLogger();

    /**
     * Upload selected file to server only
     * 
     * @param entity
     * @param businessId
     * @param businessType
     * @return Document ID
     * @throws Exception
     */
    @POST
    @Path("/upload/{businessId}/{businessType}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Map upload(Representation entity, @PathParam("businessId") String businessId,
            @PathParam("businessType") String businessType) throws Exception {
        logger.debug("upload start, businessKey is {} ", businessId);
        if (businessType == null || businessType.equalsIgnoreCase("undefined")) {
        	throw new Exception("Business type missed, please retry. - " + businessType);
        }
        
        TDocument document = documentService.createNewDocument(businessType, null);
        document.setStatus("0");
        if (businessId != null && !businessId.equalsIgnoreCase("undefined")) {
            document.setBusinessId(Long.valueOf(businessId));
        }

        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(1000240);
        RestletFileUpload upload = new RestletFileUpload(factory);
        FileItemIterator fileIterator = upload.getItemIterator(entity);
        while (fileIterator.hasNext()) {
            FileItemStream fi = fileIterator.next();
            String fileName = fi.getName();  
            
            if (fileName == null || "null".equals(fileName)) {
                break;
            }
          
            document.setFileName(fileName);
            String relativePath = fileService.generateRelativePath(businessType, null, FileService.FILE_PATH_ATTACHMENT);
            String realFileName = fileService.generateFileName(document);
            String relativeFileName = relativePath + File.separator + realFileName;
            String absoluteFileName = FileService.FILE_ROOT_PATH + File.separator + relativeFileName;

            fileService.writePhysicalFile(fi.openStream(), absoluteFileName);
            
            document.setFilePath(absoluteFileName);
            documentService.saveDocument(document);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("documentId", document.getDocumentId());
        return map;
    }

    @GET
    @Path("/download/{documentId}")
    public Representation download(@PathParam("documentId") Long documentId) throws Exception {
        String absouteFileName = null;

        TDocument document = documentService.load(documentId);
        if (StringUtils.isNotBlank(document.getFilePath())) {
            absouteFileName = FileService.FILE_ROOT_PATH + File.separator + document.getFilePath();
        } else {
            absouteFileName = fileService.generatePhysicalFile(document);
        }
        
        File localFile = fileService.readPhysicalFileContent(absouteFileName);
        Representation representation = new FileRepresentation(localFile, org.restlet.data.MediaType.APPLICATION_ALL);
        if(document.getFileName()!=null) {
        	representation.getDisposition().setFilename(document.getFileName());
        }        
        representation.getDisposition().setType(Disposition.TYPE_ATTACHMENT);

        return representation;
    }

    @GET
    @Path("/export/{documentId}")
    @Deprecated
    public Representation export(@PathParam("documentId") Long documentId) throws Exception {
        Representation representation = null;
        TDocument document = documentService.load(documentId);
        String filePath = fileService.generatePhysicalFile(document);

        File f = fileService.readPhysicalFileContent(filePath);
        representation = new FileRepresentation(f, org.restlet.data.MediaType.APPLICATION_MSOFFICE_XLSX);

        return representation;
    }
    
    @POST
    @Path(value = "/printURL/{businessType}")
    public Map<String, String> printURL(@PathParam("businessType") String businessType, Map paramMap)
            throws Exception {      
        TDocument document = documentService.createNewDocument(businessType, paramMap);
        
        String fileUrl = printService.generatePrintURL(document);
        
        document.setFileUrl(fileUrl);
        documentService.saveDocument(document);
        
        Map<String, String> returnMap = new HashMap<String, String>();
        returnMap.put("documentId", String.valueOf(document.getDocumentId()));
        returnMap.put("fileUrl", document.getFileUrl());
        return returnMap;
    }

    @GET
    @Path(value = "/queryPrintURL/{documentId}")
    public Map<String, String> queryPrintURL(@PathParam("documentId") Long documentId) throws Exception {
        TDocument document = documentService.load(documentId);
        if (document == null)
            throw new Exception("Document NOT FOUND:" + documentId);        
        
        if (StringUtils.isBlank(document.getFileUrl())) {
            document.setFileUrl(printService.generatePrintURL(document));
            documentId = documentService.saveDocument(document);
        }
        
        Map<String, String> returnMap = new HashMap<String, String>();
        returnMap.put("documentId", String.valueOf(document.getDocumentId()));
        returnMap.put("fileUrl", document.getFileUrl());
        return returnMap;
    }

    @GET
    @Path(value = "/queryPrintPath/{documentId}")
    public Map<String, String> queryPrintPath(@PathParam("documentId") Long documentId) throws Exception {
        TDocument document = documentService.load(documentId);
        if (document == null)
            throw new Exception("Document NOT FOUND:" + documentId);
        
        if (StringUtils.isBlank(document.getFileUrl())) {
            document.setFileUrl(printService.generatePrintURL(document));
            documentService.saveDocument(document);
        }
                    
        if (StringUtils.isBlank(document.getFilePath())) {
            String relativeFileName = fileService.generateFileByURL(document);          
            document.setFilePath(relativeFileName);
            documentService.saveDocument(document);
        }
        
        Map<String, String> map = new HashMap<String, String>();
        map.put("documentId", String.valueOf(document.getDocumentId()));
        map.put("filePath", document.getFilePath());
        return map;
    }

    @POST
    @Path(value = "/printPath/{businessType}")
    public Map<String, String> printPath(@PathParam("businessType") String businessType, Map paramMap)
            throws Exception {
        String filePath = printService.printPath(businessType,paramMap);
        Map<String, String> map = new HashMap<String, String>();
        map.put("filePath", filePath);
        return map;
    }

    @GET
    @Path(value = "/getPrintDataResource")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_XML)
    public String getPrintDataResource(@QueryParam("documentId") Long documentId) throws Exception {
        return printService.getPrintDataResource(documentId);
    }
    

}
