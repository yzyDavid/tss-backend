package tss.controllers.bbs;

import jdk.nashorn.internal.objects.NativeUint8Array;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import tss.requests.information.bbs.FileUploadRequest;
import tss.requests.information.bbs.TestFileRequest;
import tss.requests.information.bbs.TestStringRequest;
import tss.responses.information.bbs.FileUploadResponse;
import tss.responses.information.bbs.TestStringResponse;

import javax.validation.constraints.Null;
import java.io.*;
import java.util.ArrayList;

@Controller
@RequestMapping(path = "/file")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @PostMapping(path = "/upload")
    public ResponseEntity<FileUploadResponse> fileUpload(@RequestBody FileUploadRequest request) {
        byte[] bytes = request.getBytes();
        String path = "D://upload";

        int stateInt = 1;
        if (bytes.length > 0) {
            try {
                File validateCodeFolder = new File(path);
                if (!validateCodeFolder.exists()) {
                    validateCodeFolder.mkdirs();
                }
                // 将字符串转换成二进制，用于显示图片
                // 将上面生成的图片格式字符串 imgStr，还原成图片显示
                InputStream in = new ByteArrayInputStream(bytes);
                File file = new File(path, request.getName());
                FileOutputStream fos = new FileOutputStream(file);
                byte[] b = new byte[1024];
                int nRead = 0;
                while ((nRead = in.read(b)) != -1) {
                    fos.write(b, 0, nRead);
                }
                fos.flush();
                fos.close();
                in.close();
            } catch (Exception e) {
                stateInt = 0;
                e.printStackTrace();
                return new ResponseEntity<>(new FileUploadResponse("upload failed!"), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(new FileUploadResponse("upload ok"), HttpStatus.OK);
    }
}
