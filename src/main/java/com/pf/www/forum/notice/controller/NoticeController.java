package com.pf.www.forum.notice.controller;

import java.util.Calendar;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.pf.www.forum.message.MessageEnum;
import com.pf.www.forum.notice.service.BoardService;

@Controller
public class NoticeController {
	private final static Logger log = LoggerFactory.getLogger(NoticeController.class);
	
	@Autowired
	BoardService boardService;

	/* 게시판 리스트 */
	@RequestMapping("/forum//notice/listPage.do")
	public ModelAndView listPage(@RequestParam HashMap<String,String> params, 
			@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer size) {
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("key", Calendar.getInstance().getTimeInMillis());
		mv.setViewName("forum/notice/list");

		/* page, size의 값이 null일 수 있으니까 null 처리해주자 
		 * containsKey() 메서드로 해당 값이 있ㄴ,ㄴ지 확인*/
//		if(!params.containsKey("page")){
//			params.put("page","1");
//		}
//		if(!params.containsKey("size")){
//			params.put("size","10");
//		}
		
//	  	int page = Integer.parseInt(params.get("page"));
//	  	int size = Integer.parseInt(params.get("size"));
		
	  	
		/* 잘못된 페이지 접근 처리 */
	  	if(page <0 || page > (int)boardService.getTotalListPage(size, page).get("totalPageSize")) {
			mv.addObject("code",MessageEnum.PAGEING_ERROR.getCode());
			mv.addObject("msg",MessageEnum.PAGEING_ERROR.getDescription());	
	  	}
	  	
	  	mv.addObject("list", boardService.getList(params,page,size));
		log.info("page : " + page + ", size : " + size);
	  	mv.addObject("ph", boardService.getTotalListPage(size, page));
	  	
		return mv;
	}
	
	/* 게시글 작성 */
	@RequestMapping("/forum/notice/writePage.do")
	public ModelAndView writePage(@RequestParam HashMap<String, String> params,
			@RequestParam(defaultValue = "1") Integer boardSeq,
			@RequestParam(defaultValue = "1") Integer boardTypeSeq) {

		ModelAndView mv = new ModelAndView();
		mv.addObject("key", Calendar.getInstance().getTimeInMillis());
		mv.setViewName("forum/notice/write");
		
		return mv;				
	}

	/* 게시판 디테일 */
	@RequestMapping("/forum/notice/readPage.do")
	public ModelAndView readPage(@RequestParam HashMap<String, String> params,
			@RequestParam(defaultValue = "1") Integer boardSeq,
			@RequestParam(defaultValue = "1") Integer boardTypeSeq) {
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("key", Calendar.getInstance().getTimeInMillis());
		mv.setViewName("forum/notice/read");
		
//		if(!params.containsKey("boardSeq")) {
//			params.put("boardSeq","1");
//		}
		
		mv.addObject("detail", boardService.getBoardDetail(boardSeq));
		return mv;
	}
}
