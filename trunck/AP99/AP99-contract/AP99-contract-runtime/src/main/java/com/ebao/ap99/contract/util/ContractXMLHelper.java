package com.ebao.ap99.contract.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.SchemaFactory;

import com.ebao.ap99.contract.model.CurrencyVO;
import com.ebao.ap99.contract.model.BusinessModel.ContractBO;
import com.ebao.ap99.contract.model.BusinessModel.ContractBOList;

public class ContractXMLHelper {

    JAXBContext jaxbContext;

    //@Test
    public String marshallXml(ByteArrayOutputStream out, Object object, Class cls) throws Exception {

        String xmlString = null;
        jaxbContext = JAXBContext.newInstance(cls);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        // output pretty printed  
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        //jaxbMarshaller.marshal(contractBOList, System.out);
        jaxbMarshaller.marshal(object, out);
        xmlString = out.toString();
        return xmlString;
    }

    public void testUnmarshaller() throws Exception {

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        // output pretty printed  
        CurrencyVO vo = (CurrencyVO) jaxbUnmarshaller.unmarshal(xmlStream());
        System.out.println("CurrencyVo=" + vo.getCurrencyType());
        // assertTrue(true);
    }

    //@Test
    public void testXMLUnmarshaller() throws Exception {

        File file = new File("D:\\ContractBO.xml");
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        jaxbUnmarshaller.setSchema(
                SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(new File("D:\\MySchema.xsd")));
        // output pretty printed  
        ContractBOList vo = (ContractBOList) jaxbUnmarshaller.unmarshal(file);
        System.out.println("ContractBOList=" + vo.getBoList());
        for (ContractBO contractBO : (List<ContractBO>) vo.getBoList()) {
            System.out.println("contractBO.ContCompId:" + contractBO.getContCompId());
            System.out.println("contractBO.Broker:" + contractBO.getBroker());
        }
        //assertTrue(true);
    }

    private ContractBOList newInstance() {
        ContractBOList l = new ContractBOList();
        ContractBO vo = new ContractBO();
        //vo.setCedentCountry("China");
        //vo.setContractCategory("Unknown");
        vo.setBroker("ABC");
        vo.setBrokerRef("Broker 1234");
        l.addNewObject(vo);
        //l.addNewObject(vo);
        return l;
    }

    private InputStream xmlStream() {

        String s = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" + "<currencyVO>"
                + "    <currencyType>RMB</currencyType>" + "    <mainCurrency>True</mainCurrency>"
                + "    <mainCurrencyType>true</mainCurrencyType>" + "</currencyVO>";
        return new ByteArrayInputStream(s.getBytes());
    }

}
