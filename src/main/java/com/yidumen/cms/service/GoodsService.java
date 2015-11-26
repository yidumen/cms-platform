package com.yidumen.cms.service;

import com.yidumen.cms.GoodsStatus;
import com.yidumen.cms.model.Goods;
import com.yidumen.cms.repository.GoodsHibernateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 蔡迪旻
 * 2015年11月26日
 */
@Service
public final class GoodsService {
    @Autowired
    private GoodsHibernateRepository goodsDao;

    public List<Goods> findAll() {
        return goodsDao.findAll();
    }

    public List<Goods> findValidate() {
        return goodsDao.findValidate();
    }

    public List<Goods> findUnProcess() {
        return goods.findByCondition(new Goods().set("status", 1));
    }

    public List<Goods> findTrash() {
        return goods.findByCondition(new Goods().set("status", 2));
    }

    public void process(Long id) {
        goods.findById(id).set("status", GoodsStatus.SUCCESS.ordinal()).update();
    }

    public void trash(Long id) {
        goods.findById(id).set("status", GoodsStatus.ERROR.ordinal()).update();
    }

    public void recover(Long id) {
        goods.findById(id).set("status", GoodsStatus.WAIT.ordinal()).update();
    }

}
