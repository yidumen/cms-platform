package com.yidumen.cms.service;

import com.yidumen.cms.GoodsStatus;
import com.yidumen.cms.entity.Goods;
import com.yidumen.cms.repository.GoodsHibernateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @SuppressWarnings("unchecked")
    public List<Goods> findUnProcess() {
        final Goods model = new Goods();
        model.setStatus(GoodsStatus.WAIT);
        return goodsDao.find(model);
    }

    @SuppressWarnings("unchecked")
    public List<Goods> findTrash() {
        final Goods model = new Goods();
        model.setStatus(GoodsStatus.ERROR);
        return goodsDao.find(model);
    }

    public void process(Long id) {
        final Goods entity = goodsDao.find(id);
        entity.setStatus(GoodsStatus.SUCCESS);
        goodsDao.edit(entity);
    }

    public void trash(Long id) {
        final Goods entity = goodsDao.find(id);
        entity.setStatus(GoodsStatus.ERROR);
        goodsDao.edit(entity);
    }

    public void recover(Long id) {
        final Goods entity = goodsDao.find(id);
        entity.setStatus(GoodsStatus.WAIT);
        goodsDao.edit(entity);
    }

}
