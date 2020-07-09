package com.bigdata.boot.chapter33.service;

import com.bigdata.boot.chapter33.model.Note;

import java.util.List;

/**
 * @ClassName NodeService
 * @Description TODO
 * @Author HuHao（huhao1@cmcm.com）
 * @Date 2020/6/27 13:21
 * @Version 1.0
 **/

public interface NodeService {
    List<Note> findAll();

}
