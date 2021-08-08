package com.project.happyhouse.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.happyhouse.dto.QnA;
import com.project.happyhouse.service.QnAService;

import io.swagger.annotations.ApiOperation;

//http://localhost:9999/happyhouse/swagger-ui.html
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/api/qna")
public class QnAController {

	private static final Logger logger = LoggerFactory.getLogger(QnAController.class);
	// Vue에서 등록 제대로 됫는지 안 됫는지 체크해주는 변수
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private QnAService qnaService;

    @ApiOperation(value = "모든 질문게시판의 정보를 반환한다.", response = List.class)
	@GetMapping // url안 쓰면 위의 RequestMapping의 주소를 자동으로 불러온다.
	public ResponseEntity<List<QnA>> retrieveQnA() throws Exception {
		logger.debug("retrieveQnA - 호출");
		return new ResponseEntity<List<QnA>>(qnaService.retrieveQnA(), HttpStatus.OK);
	}
    
    
    @ApiOperation(value = "글번호에 해당하는 게시글의 정보를 반환한다.", response = QnA.class)    
	@GetMapping("{qnaNo}")
	public ResponseEntity<QnA> detailQnA(@PathVariable int qnaNo) {
    	System.out.println("read: "+qnaNo);
		logger.debug("detailQnA - 호출");
		System.out.println("들어옴");
		return new ResponseEntity<QnA>(qnaService.detailQnA(qnaNo), HttpStatus.OK);
	}

    @ApiOperation(value = "새로운 게시글 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping
	public ResponseEntity<String> writeQnA(@RequestBody QnA qna) {
		logger.debug("writeQnA - 호출");
		System.out.println("create !!!");
		if (qnaService.writeQnA(qna)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

    @ApiOperation(value = "글번호에 해당하는 게시글의 정보를 수정한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping("{qnaNo}")
	public ResponseEntity<String> updateQnA(@RequestBody QnA qnaNo) {
		logger.debug("updateQnA - 호출");
		logger.debug("" + qnaNo);
		
		if (qnaService.updateQnA(qnaNo)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

    @ApiOperation(value = "글번호에 해당하는 게시글의 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("{qnaNo}")
	public ResponseEntity<String> deleteQnA(@PathVariable int qnaNo) {
		logger.debug("deleteQnA - 호출");
		if (qnaService.deleteQnA(qnaNo)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
}