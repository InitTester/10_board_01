package com.pf.www.forum.notice.service;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pf.www.forum.notice.dao.BoardDao;
import com.pf.www.forum.notice.dto.BoardDto;

@Service
public class BoardService {
	private final static Logger log = LoggerFactory.getLogger(BoardService.class);
	
	@Autowired
	private BoardDao boardDao;

	/* 게시판 리스트 */
	public List<BoardDto> getList(HashMap<String, String> params, Integer page, Integer size){
		return boardDao.getList(params, page, size);
	}

	/* 게시판 디테일 */
	public BoardDto getBoardDetail(Integer board_seq) {
		return boardDao.getBoardDetail(board_seq);
	}
	
	/* 페이징 처리를 위한 전체 페이지*/
	public HashMap<String, Integer> getTotalListPage(Integer size, Integer page){
		
		HashMap<String, Integer> pageHandler = new HashMap<String, Integer>();
		
		/*pageHandler에 필요한 변수 선언
		 * totalPage 전체 게시물 개수
		 * page 현재 페이지 번호
		 * size 페이지당 게시물 수
		 * 
		 * begin 시작페이지
		 * end 끝 페이지
		 * 
		 * prev 이전 화살표 
		 * next 다음 화살표
		 * */
		
		int totalPage = boardDao.getTotalPage(); 
		int totalPageSize = totalPage / size;
		
		int begin = page == 0 ? 1 : ((page-1)/size)*size + 1;
		int end = Math.min(begin+size-1,  totalPageSize);
		
		int prev = begin;
		int next = end != totalPage ? end : totalPage;
		
		pageHandler.put("totalPage",totalPage);
		pageHandler.put("totalPageSize",totalPageSize);
		pageHandler.put("begin",begin);
		pageHandler.put("end",end);
		pageHandler.put("size",size);
		pageHandler.put("prev",prev);
		pageHandler.put("next",next);
		
		return pageHandler;
	}
}
