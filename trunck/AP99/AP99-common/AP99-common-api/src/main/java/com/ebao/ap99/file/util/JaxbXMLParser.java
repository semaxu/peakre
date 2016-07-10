package com.ebao.ap99.file.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.commons.collections.CollectionUtils;

import com.ebao.ap99.file.model.ItemVO;
import com.ebao.ap99.file.service.XMLParseInterface;
import com.ebao.unicorn.platform.foundation.cfg.Env;

/**
 * Date: Feb 25, 2016 8:36:20 PM
 * 
 * @author chuan.ye
 */
public class JaxbXMLParser {

    /**
     * parse XML to Java object
     * 
     * @param inputStreaml
     * @param className
     * @return list
     * @throws Exception
     */
    public static List<ItemVO<Object>> unmarshaller(InputStream inputStream, String className) throws Exception {

        Class cls = Class.forName(className);
        JAXBContext jaxbContext = JAXBContext.newInstance(cls);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        Schema validateschema = getSchemaValidator(jaxbContext, className);
        jaxbUnmarshaller.setSchema(validateschema);
        XMLParseInterface xMLParseInterface = (XMLParseInterface) jaxbUnmarshaller.unmarshal(inputStream);
        List<ItemVO<Object>> list = new ArrayList<ItemVO<Object>>();
        if (CollectionUtils.isNotEmpty(xMLParseInterface.getBoList())) {
            for (Object obj : xMLParseInterface.getBoList()) {
                ItemVO<Object> itemVO = new ItemVO<Object>();
                itemVO.setBizVO(obj);
                list.add(itemVO);
            }
        }
        return list;
    }

    public static String marshallXml(ByteArrayOutputStream out, Object object, Class cls) throws Exception {

        String xmlString = null;
        JAXBContext jaxbContext = JAXBContext.newInstance(cls);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        // output pretty printed  
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        //jaxbMarshaller.marshal(contractBOList, System.out);
        jaxbMarshaller.marshal(object, out);
        xmlString = out.toString();
        return xmlString;
    }

    private static Map<String, Schema> validateSchemaMap = new HashMap<String, Schema>();

    /**
     * get XSD Valiadtor
     * 
     * @param jaxbContext
     * @param className
     * @return schema
     * @throws Exception
     */
    public static Schema getSchemaValidator(JAXBContext jaxbContext, String className) throws Exception {

        String envPath = Env.getParameter(Constants.ENV.FILE_PATH) +File.separator+ "Document" + File.separator + "Input";
        Schema validateschema = validateSchemaMap.get(className);
        if (validateschema == null) {
            //Path
            MySchemaOutputResolver resolver = new MySchemaOutputResolver(envPath, className + ".xsd");
            jaxbContext.generateSchema(resolver);
            File file = resolver.getF();
            validateschema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(file);
            validateSchemaMap.put(className, validateschema);
        }

        validateschema = validateSchemaMap.get(className);
        return validateschema;

    }

}

class MySchemaOutputResolver extends SchemaOutputResolver {

    private File f;

    public File getF() {
        return f;
    }

    public void setF(File f) {
        this.f = f;
    }

    public MySchemaOutputResolver(String dir, String fileName) throws IOException {
        //f = new File(dir, fileName);
        f = makeFile(dir+File.separator+fileName);
    }

    @Override
    public Result createOutput(String namespaceUri, String suggestedFileName) throws IOException {
        return new StreamResult(f);
    }
    
   
    public File makeFile(String absoluteFileName) throws IOException {
        int positon = absoluteFileName.lastIndexOf(File.separator);     
        String absolutePath = absoluteFileName.substring(0, positon + 1);
        makeDir(absolutePath);
        
        File file = new File(absoluteFileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }
    
    public void makeDir(String absolutePath) {
        // create folder
        File file = new File(absolutePath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

}
