package com.yidumen.cms.service.impl;

import com.yidumen.cms.GoodsStatus;
import com.yidumen.cms.model.Goods;
import com.yidumen.cms.service.GoodsService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cdm on 15/2/25.
 */
public final class GoodsServiceImpl implements GoodsService {
    private final Goods goods;

    public GoodsServiceImpl() {
        this.goods = new Goods();
    }

    @Override
    public List<Goods> findAll() {
        return goods.findAll();
    }

    @Override
    public List<Goods> findValidate() {
        final Map<String, Object[]> conding = new HashMap<>();
        final Integer[] between = {0, 1};
        conding.put("status", between);
        return goods.findBetween(conding);
    }

    @Override
    public List<Goods> findUnProcess() {
        return goods.findByCondition(new Goods().set("status", 1));
    }

    @Override
    public List<Goods> findTrash() {
        return goods.findByCondition(new Goods().set("status", 2));
    }

    @Override
    public void process(Long id) {
        goods.findById(id).set("status", GoodsStatus.SUCCESS.ordinal()).update();
    }

    @Override
    public void trash(Long id) {
        goods.findById(id).set("status", GoodsStatus.ERROR.ordinal()).update();
    }

    @Override
    public void recover(Long id) {
        goods.findById(id).set("status", GoodsStatus.WAIT.ordinal()).update();
    }

}
