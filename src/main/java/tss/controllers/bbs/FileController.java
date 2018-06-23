package tss.controllers.bbs;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import tss.requests.information.bbs.TestFileRequest;
import tss.requests.information.bbs.TestStringRequest;
import tss.responses.information.bbs.TestStringResponse;

import java.util.ArrayList;

@Controller
@RequestMapping(path = "/test")
public class TestController {

    @PostMapping(path = "/userPicUpload")
    public void userPicUpload(TestFileRequest request) {
        ArrayList<Byte> datas = request.getData();
        System.out.print(datas.toString());
    }


    @PostMapping(path = "/string")
    public ResponseEntity<TestStringResponse> testString(@RequestBody TestStringRequest request) {
        String data = request.getData();
        System.out.print(data);
        return new ResponseEntity<>(new TestStringResponse(data), HttpStatus.OK);
    }
}
