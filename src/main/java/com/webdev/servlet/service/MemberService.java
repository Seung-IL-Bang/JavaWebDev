package com.webdev.servlet.service;

import com.webdev.servlet.dao.MemberDAO;
import com.webdev.servlet.domain.MemberVO;
import com.webdev.servlet.dto.MemberDTO;
import com.webdev.servlet.util.Mapper;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;

@Log4j2
public enum MemberService {

    INSTANCE;

    private MemberDAO dao;
    private ModelMapper mapper;

    MemberService() {
        dao = new MemberDAO();
        mapper = Mapper.INSTANCE.getMapper();
    }

    public MemberDTO login(String mid, String mpw) throws Exception {
        MemberVO vo = dao.getWithPassword(mid, mpw);
        MemberDTO memberDTO = mapper.map(vo, MemberDTO.class);

        return memberDTO;
    }
}
