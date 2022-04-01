package com.byf.mywiki.controller;


import com.byf.mywiki.domain.Ebook;
import com.byf.mywiki.req.EbookReq;
import com.byf.mywiki.resp.CommonResp;
import com.byf.mywiki.resp.EbookResp;
import com.byf.mywiki.service.EbookService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/ebook")
public class EbookController {

    @Resource
    private EbookService ebookService;

//    @GetMapping("/list")
//    public List<Ebook> list() {
//        return ebookService.list();
//    }

//    无参查询
//    @GetMapping("/list")
//    public CommonResp list() {
//        CommonResp<List<Ebook>> resp = new CommonResp<>();
//        List<Ebook> list = ebookService.list();
//        resp.setContent(list);
//        return resp;
//    }

//        @GetMapping("/list")
//    public CommonResp list(String name) {
//        CommonResp<List<Ebook>> resp = new CommonResp<>();
//        List<Ebook> list = ebookService.list(name);
//        resp.setContent(list);
//        return resp;
//    }
//
//    @GetMapping("/list")
//    public CommonResp list(EbookReq req) {
//        CommonResp<List<Ebook>> resp = new CommonResp<>();
//        List<Ebook> list = ebookService.list(req);
//        resp.setContent(list);
//        return resp;
//    }

    @GetMapping("/list")
    public CommonResp list(EbookReq req) {
        CommonResp<List<EbookResp>> resp = new CommonResp<>();
        List<EbookResp> list = ebookService.list(req);
        resp.setContent(list);
        return resp;
    }




}
