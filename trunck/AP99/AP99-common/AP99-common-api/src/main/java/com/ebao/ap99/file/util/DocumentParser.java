package com.ebao.ap99.file.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;

import com.ebao.ap99.file.entity.DocumentField;
import com.ebao.ap99.file.entity.DocumentRule;
import com.ebao.ap99.file.model.ItemVO;
import com.ebao.unicorn.platform.foundation.utils.LoggerFactory;

public class DocumentParser {
    private static Logger           logger          = LoggerFactory.getLogger();
    private FastDateFormat          DATETIME_FORMAT = FastDateFormat.getInstance("dd/MM/yyyy");
    private static SimpleDateFormat formatter       = new SimpleDateFormat("dd/MM/yyyy");

    public DocumentParser() {

    }

    public static List<ItemVO<Object>> documentXmlParser(File file, DocumentRule documentRule)
            throws FileNotFoundException, IOException, Exception {
        String className = documentRule.getParseClass();
        List<ItemVO<Object>> list = FileParser.getXmlObjectData(file, className);
        return list;
    }

    public static List creatModelList(File file, DocumentRule documentRule)
            throws FileNotFoundException, IOException, ParseException, InvalidFormatException {
        String className = documentRule.getParseClass();
        List<DocumentField> fields = documentRule.getTDocumentFields();
        String[][] result = FileParser.getData(file, 1);
        List list = new ArrayList();
        int rowLength = result.length;
        for (int i = 0; i < rowLength; i++) {
            Object model = createModel(className);
            ItemVO itemVO = new ItemVO();
            String msg = "";
            for (int j = 0; j < fields.size(); j++) {
                DocumentField field = fields.get(j);
                int rowLen = result[i].length;
                if(j<rowLen){
                    msg = msg + setModelValue(model, field, result[i][j]);
                    itemVO.setBizVO(model);
                }
            }
            itemVO.setRowNo(String.valueOf(i+1));
            if(msg.length()>0){
                itemVO.setResultFlag(false);
                itemVO.setMsg(msg);
                
            }else{
                itemVO.setResultFlag(true);
                itemVO.setMsg("success!");
            }
            list.add(itemVO);
        }
        return list;
    }

    public static String setModelValue(Object model, DocumentField field, String fileValue) {
        String fieldName = field.getFieldName();
        String fieldType = field.getFieldType();
        String fieldNullable = field.getFieldNullable();
        Integer fieldMax = field.getFieldMaxlength();
        StringBuffer msg = new StringBuffer();
        if (fieldNullable.equals("1")) {
            if (StringUtils.isBlank(fileValue)) {
                msg.append("property :" + fieldName + " can not be empty!");
                return msg.toString();
            }
        }
        if (fieldType.equals("1")) {
            if (!isValidDate(fileValue)) {
                msg.append("property :" + fieldName + " invalid date format!");
                return msg.toString();
            }
        }
        if (fieldMax > 0) {
            if (fileValue.length() > fieldMax) {
                msg.append("property :" + fieldName + "value too large");
                return msg.toString();
            }
        }
        try {
            String name = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1); 
            Method m = model.getClass().getMethod("get" + name);
            String type = model.getClass().getDeclaredField(fieldName).getGenericType().toString();
            if (type.equals("class java.lang.String")) {
                    m = model.getClass().getMethod("set" + name, String.class);
                 //   fileValue = parseValue(fileValue);
                    m.invoke(model, fileValue);

            }
            if (type.equals("class java.lang.Integer")) {
                    m = model.getClass().getMethod("set" + name, Integer.class);
                    BigDecimal bd = new BigDecimal(fileValue);
                    m.invoke(model, bd.intValue());
            }
            if (type.equals("class java.lang.Boolean")) {
                    m = model.getClass().getMethod("set" + name, Boolean.class);
                    m.invoke(model, false);

            }
            if (type.equals("class java.util.Date")) {
                    m = model.getClass().getMethod("set" + name, Date.class);
                    m.invoke(model, formatter.parse(fileValue));
            }

            if (type.equals("class java.math.BigDecimal")) {
                m = model.getClass().getMethod("set" + name, BigDecimal.class);
                if(fileValue.equals("")){
                    m.invoke(model,new BigDecimal(-1));
                }else {
                    m.invoke(model, new BigDecimal(fileValue)); 
                }
        }

        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return msg.toString();
    }

    public static Object createModel(String className) {
        Class modelClass = null;
        try {
            modelClass = Class.forName(className);
            Object model = modelClass.newInstance();
            Field[] field = model.getClass().getDeclaredFields();

            for (int j = 0; j < field.length; j++) {
                String name = field[j].getName();
                name = name.substring(0, 1).toUpperCase() + name.substring(1);

                String type = field[j].getGenericType().toString();
                if (type.equals("class java.lang.String")) {
                    Method m = model.getClass().getMethod("get" + name);
                    String value = (String) m.invoke(model);
                    if (value == null) {
                        m = model.getClass().getMethod("set" + name, String.class);
                        m.invoke(model, "");
                    }
                }
                if (type.equals("class java.lang.Integer")) {
                    Method m = model.getClass().getMethod("get" + name);
                    Integer value = (Integer) m.invoke(model);
                    if (value == null) {
                        m = model.getClass().getMethod("set" + name, Integer.class);
                        m.invoke(model, 0);
                    }
                }
                if (type.equals("class java.lang.Boolean")) {
                    Method m = model.getClass().getMethod("get" + name);
                    Boolean value = (Boolean) m.invoke(model);
                    if (value == null) {
                        m = model.getClass().getMethod("set" + name, Boolean.class);
                        m.invoke(model, false);
                    }
                }
                if (type.equals("class java.util.Date")) {
                    Method m = model.getClass().getMethod("get" + name);
                    Date value = (Date) m.invoke(model);
                    if (value == null) {
                        m = model.getClass().getMethod("set" + name, Date.class);
                        m.invoke(model, new Date());
                    }
                }

            }
            return model;

        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isValidDate(String s) {
        try {
            //formatter.parse(s);
            DateUtils.parseDateStrictly(s,"dd/MM/yyyy");
            return true;
        } catch (Exception e) {
            // throw java.text.ParseException or NullPointerException，

            return false;  
        }  
    } 
    
   public static String parseValue(String s) {
    String [] ss = s.split("\\.");
    return ss[0];
}

}
