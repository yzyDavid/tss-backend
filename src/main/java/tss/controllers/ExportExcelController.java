package tss.controllers;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tss.annotations.session.Authorization;
import tss.annotations.session.CurrentUser;
import tss.entities.ClassEntity;
import tss.entities.ClassRegistrationEntity;
import tss.entities.UserEntity;
import tss.repositories.ClassRepository;
import tss.repositories.CourseRepository;
import tss.requests.information.DownloadExcelRequest;
import tss.utils.ExportUtils;

import javax.xml.ws.Response;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path="excel")
public class ExportExcelController {
    private final CourseRepository course;
    private final ClassRepository classes;

    @Autowired ExportExcelController(CourseRepository course, ClassRepository classes)
    {
        this.course = course;
        this.classes = classes;
    }

    @GetMapping("/download/{courseId}")
    @ResponseBody
    public ResponseEntity<byte[]> downloadExcel(/*@CurrentUser UserEntity user,*/
                                                @PathVariable String courseId) throws Exception
    {


        ExportUtils<UserEntity> ee = new ExportUtils<>();
        String[] headerss = {"用户名", "电子邮件", "性别", ""};

        // TODO: Add current user
        List<ClassEntity> cls = classes.findByCourse_id(courseId);
        List<UserEntity> students = new ArrayList<>() ;
        for (ClassEntity cl : cls)
        {
            List<ClassRegistrationEntity>  crs = cl.getClassRegistrations();
            for (ClassRegistrationEntity cr : crs)
            {
                students.add(cr.getStudent());
            }
        }
//
//        Workbook wb = new HSSFWorkbook();
//        ResponseEntity<byte []> excelresponse;
//        Sheet sheet = wb.createSheet("1");
//
//        Row row = sheet.createRow(0);// 第0+1行
//        Cell cell = row.createCell(0);
//        cell.setCellValue("abc");
//
//        /* Set up Http headers */
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentDispositionFormData("attachment", "students.xls");
//        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//
//
//        /* Write to the buffer */
//        ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
//        wb.write(outByteStream);
//        return new ResponseEntity<byte[]>(outByteStream.toByteArray(), headers, HttpStatus.OK);
        return ee.exportExcel(headerss, students, "students.xls");


    }


}
