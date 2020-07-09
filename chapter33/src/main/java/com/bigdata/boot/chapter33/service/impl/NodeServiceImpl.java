package com.bigdata.boot.chapter33.service.impl;

import com.bigdata.boot.chapter33.dao.NoteRepository;
import com.bigdata.boot.chapter33.model.Note;
import com.bigdata.boot.chapter33.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

/**
 * @ClassName NodeServiceImpl
 * @Description TODO
 * @Author HuHao（huhao1@cmcm.com）
 * @Date 2020/6/27 13:22
 * @Version 1.0
 **/

@Service
public class NodeServiceImpl implements NodeService {

    @Autowired
    private NoteRepository noteRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Note> findAll() {
        return noteRepository.findAll();
    }
}
