package com.byf.mywiki.controller;

import com.byf.mywiki.req.DocQueryReq;
import com.byf.mywiki.req.DocSaveReq;
import com.byf.mywiki.resp.DocQueryResp;
import com.byf.mywiki.resp.CommonResp;
import com.byf.mywiki.resp.PageResp;
import com.byf.mywiki.service.DocService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/doc")
public class DocController {

    @Resource
    private DocService docService;

//    @GetMapping("/all")
//    public CommonResp all() {
//        CommonResp<List<DocQueryResp>> resp = new CommonResp<>();
//        List<DocQueryResp> list = docService.all();
//        resp.setContent(list);
//        return resp;
//    }

    @GetMapping("/all/{ebookId}")
    public CommonResp all(@PathVariable Long ebookId) {
        CommonResp<List<DocQueryResp>> resp = new CommonResp<>();
        List<DocQueryResp> list = docService.all(ebookId);
        resp.setContent(list);
        return resp;
    }


    @GetMapping("/list")
    public CommonResp list(@Valid DocQueryReq req) {
        CommonResp<PageResp<DocQueryResp>> resp = new CommonResp<>();
        PageResp<DocQueryResp> list = docService.list(req);
        resp.setContent(list);
        return resp;
    }

    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody DocSaveReq req) {
        CommonResp resp = new CommonResp<>();
        docService.save(req);
        return resp;
    }

//    @DeleteMapping("/delete/{id}")
//    public CommonResp delete(@PathVariable Long id) {
//        CommonResp resp = new CommonResp<>();
//        docService.delete(id);
//        return resp;
//    }

    @DeleteMapping("/delete/{idsStr}")
    public CommonResp delete(@PathVariable String idsStr) {
        CommonResp resp = new CommonResp<>();
        List<String> list = Arrays.asList(idsStr.split(","));
        docService.delete(list);
        return resp;
    }

    @GetMapping("/find-content/{id}")
    public CommonResp findContent(@PathVariable Long id) {
        CommonResp<String> resp = new CommonResp<>();
        String content = docService.findContent(id);
        resp.setContent(content);
        return resp;
    }

    @GetMapping("/vote/{id}")
    public CommonResp vote(@PathVariable Long id) {
        CommonResp commonResp = new CommonResp();
        docService.vote(id);
        return commonResp;
    }




}
