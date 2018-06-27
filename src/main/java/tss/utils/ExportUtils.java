package tss.utils;

import org.apache.poi.hssf.usermodel.*;
;

import org.apache.poi.hssf.util.HSSFRegionUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;

/**
 * @Author Zxzeng
 */

public class ExportUtils<T> {
    public ResponseEntity<byte[]> exportExcel(String[] headers, Collection<T> dataset, String filename) {
        // WorkBook declaration
        HSSFWorkbook wb = new HSSFWorkbook();
        // Generate a sheet
        HSSFSheet sheet = wb.createSheet(filename);

        sheet.setDefaultColumnWidth((short) 20);

        HSSFRow row = sheet.createRow(0);

        for (short i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);

        }
        try {
            Iterator<T> it = dataset.iterator();
            int index = 0;
            while (it.hasNext()) {
                index++;
                row = sheet.createRow(index);
                T t = (T) it.next();
                Field[] fields = t.getClass().getDeclaredFields();
                for (short i = 0; i < headers.length; i++) {
                    HSSFCell cell = row.createCell(i);
                    Field field = fields[i];
                    String fieldName = field.getName();
                    String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                    Class tCls = t.getClass();
                    Method getMethod = tCls.getMethod(getMethodName, new Class[]{});
                    Object value = getMethod.invoke(t, new Object[]{});
                    String textValue = null;
                    // 其它数据类型都当作字符串简单处理
                    if (value != null && value != "") {
                        textValue = value.toString();
                    }
                    if (textValue != null) {
                        HSSFRichTextString richString = new HSSFRichTextString(textValue);
                        cell.setCellValue(richString);
                    }
                }
            }
            HttpHeaders header = new HttpHeaders();
            header.setContentDispositionFormData("attachment", filename);
            header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
            wb.write(outByteStream);
            return new ResponseEntity<byte[]>(outByteStream.toByteArray(), header, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<byte[]>(null, null, null);
    }


}
