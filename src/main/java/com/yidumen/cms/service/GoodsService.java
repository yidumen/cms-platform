package com.yidumen.cms.service;

import com.yidumen.cms.model.Goods;

import java.util.List;

/**
 * Created by cdm on 15/2/25.
 */
public interface GoodsService {
    List<Goods> findAll();

    List<Goods> findValidate();

    List<Goods> findUnProcess();

    List<Goods> findTrash();

    void process(Long id);

    void trash(Long id);

    void recover(Long id);
}
