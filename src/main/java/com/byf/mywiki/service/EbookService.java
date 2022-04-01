package com.byf.mywiki.service;

import com.byf.mywiki.domain.Ebook;
import com.byf.mywiki.domain.EbookExample;
import com.byf.mywiki.mapper.EbookMapper;
import com.byf.mywiki.req.EbookReq;
import com.byf.mywiki.resp.EbookResp;
import com.byf.mywiki.util.CopyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class EbookService {

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

    public List<EbookResp>  list(EbookReq req) {
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        // 动态sql 常用写法
        //
        if(!ObjectUtils.isEmpty(req.getName())){
            criteria.andNameLike("%" + req.getName() + "%");
        }
        List<Ebook> ebooks = ebookMapper.selectByExample(ebookExample);
//        List<EbookResp> respList = new ArrayList<>();
//        for(Ebook ebook:ebooks) {
////            EbookResp ebookResp = new EbookResp();
////            BeanUtils.copyProperties(ebook, ebookResp);
//            // 将ebook对象按对应属性复制到ebookresponse对象上
//            EbookResp ebookResp = CopyUtil.copy(ebook, EbookResp.class);
//            respList.add(ebookResp);
//        }
        List<EbookResp> respList = CopyUtil.copyList(ebooks, EbookResp.class);
        return respList;
    }


}
