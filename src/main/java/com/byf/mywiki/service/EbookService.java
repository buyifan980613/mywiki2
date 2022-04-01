package com.byf.mywiki.service;

import com.byf.mywiki.domain.Ebook;
import com.byf.mywiki.domain.EbookExample;
import com.byf.mywiki.mapper.EbookMapper;
import com.byf.mywiki.req.EbookQueryReq;
import com.byf.mywiki.req.EbookReq;
import com.byf.mywiki.req.EbookSaveReq;
import com.byf.mywiki.resp.EbookQueryResp;
import com.byf.mywiki.resp.EbookResp;
import com.byf.mywiki.resp.PageResp;
import com.byf.mywiki.util.CopyUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class EbookService {

    private static final Logger LOG = LoggerFactory.getLogger(EbookService.class);

    @Autowired
    private EbookMapper ebookMapper;
//    public List<Ebook>  list() {
//        return ebookMapper.selectByExample(null);
//    }
//    public List<Ebook>  list(String name) {
//        EbookExample ebookExample = new EbookExample();
//        EbookExample.Criteria criteria = ebookExample.createCriteria();
//        criteria.andNameLike("%" + name + "%");
//
//        return ebookMapper.selectByExample(ebookExample);
//    }
//
//    public List<Ebook>  list(EbookReq req) {
//        EbookExample ebookExample = new EbookExample();
//        EbookExample.Criteria criteria = ebookExample.createCriteria();
//        criteria.andNameLike("%" + req.getName() + "%");
//
//        return ebookMapper.selectByExample(ebookExample);
//    }

//    public List<EbookResp>  list(EbookReq req) {
//
//        EbookExample ebookExample = new EbookExample();
//        EbookExample.Criteria criteria = ebookExample.createCriteria();
//        // 动态sql 常用写法
//        //
//        if(!ObjectUtils.isEmpty(req.getName())){
//            criteria.andNameLike("%" + req.getName() + "%");
//        }
//        PageHelper.startPage(1,3);
//        List<Ebook> ebooks = ebookMapper.selectByExample(ebookExample);
////        List<EbookResp> respList = new ArrayList<>();
////        for(Ebook ebook:ebooks) {
//////            EbookResp ebookResp = new EbookResp();
//////            BeanUtils.copyProperties(ebook, ebookResp);
////            // 将ebook对象按对应属性复制到ebookresponse对象上
////            EbookResp ebookResp = CopyUtil.copy(ebook, EbookResp.class);
////            respList.add(ebookResp);
////        }
//        List<EbookResp> respList = CopyUtil.copyList(ebooks, EbookResp.class);
//        return respList;
//    }



    public PageResp<EbookQueryResp> list(EbookQueryReq req) {
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getName())) {
            criteria.andNameLike("%" + req.getName() + "%");
        }
        if (!ObjectUtils.isEmpty(req.getCategoryId2())) {
            criteria.andCategory2IdEqualTo(req.getCategoryId2());
        }
        PageHelper.startPage(req.getPage(), req.getSize());
        List<Ebook> ebookList = ebookMapper.selectByExample(ebookExample);

        PageInfo<Ebook> pageInfo = new PageInfo<>(ebookList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        // List<EbookResp> respList = new ArrayList<>();
        // for (Ebook ebook : ebookList) {
        //     // EbookResp ebookResp = new EbookResp();
        //     // BeanUtils.copyProperties(ebook, ebookResp);
        //     // 对象复制
        //     EbookResp ebookResp = CopyUtil.copy(ebook, EbookResp.class);
        //
        //     respList.add(ebookResp);
        // }

        // 列表复制
        List<EbookQueryResp> list = CopyUtil.copyList(ebookList, EbookQueryResp.class);

        PageResp<EbookQueryResp> pageResp = new PageResp();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);

        return pageResp;
    }

    public void save(EbookSaveReq req) {
        Ebook ebook = CopyUtil.copy(req, Ebook.class);
        if (ObjectUtils.isEmpty(req.getId())) {
            // 新增
//            ebook.setId(snowFlake.nextId());
            ebookMapper.insert(ebook);
        } else {
            // 更新
            ebookMapper.updateByPrimaryKey(ebook);
        }
    }

    public void delete(Long id) {
        ebookMapper.deleteByPrimaryKey(id);
    }



}
