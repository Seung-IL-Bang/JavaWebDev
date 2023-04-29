package com.webdev.servlet.service;

import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import com.webdev.servlet.dao.TodoDAO;
import com.webdev.servlet.domain.TodoVO;
import com.webdev.servlet.dto.TodoDTO;
import com.webdev.servlet.util.Mapper;

import java.rmi.StubNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public enum TodoService {

    INSTANCE;

    private TodoDAO dao;
    private ModelMapper mapper;

    TodoService() {
        dao = new TodoDAO();
        mapper = Mapper.INSTANCE.getMapper();
    }

    public void register(TodoDTO dto) throws Exception {
        TodoVO vo = mapper.map(dto, TodoVO.class);

        log.info(vo);

        dao.insert(vo);
    }

    public TodoDTO get(Long tno) throws Exception {
        log.info("tno: " + tno);
        TodoVO todoVO = dao.selectOne(tno);
        TodoDTO todoDTO = mapper.map(todoVO, TodoDTO.class);
        return todoDTO;
    }

    public List<TodoDTO> listAll() throws Exception {
        List<TodoVO> voList = dao.selectAll();

        log.info("voList ---------------------------");
        log.info(voList);

        List<TodoDTO> dtoList = voList.stream()
                .map(vo -> mapper.map(vo, TodoDTO.class)).collect(Collectors.toList());

        return dtoList;
    }

    public void remove(Long tno) throws Exception {

        log.info("[DELETE] tno: " + tno);
        dao.deleteOne(tno);
    }

    public void modify(TodoDTO todoDTO) throws Exception {
        log.info("[UPDATE] DTO: " + todoDTO);

        TodoVO vo = mapper.map(todoDTO, TodoVO.class);

        dao.updateOne(vo);
    }
}
