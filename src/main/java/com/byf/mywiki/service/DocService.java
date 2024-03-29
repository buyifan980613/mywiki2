package com.byf.mywiki.service;

import com.byf.mywiki.domain.Content;
import com.byf.mywiki.domain.Doc;
import com.byf.mywiki.domain.DocExample;
import com.byf.mywiki.exception.BusinessException;
import com.byf.mywiki.exception.BusinessExceptionCode;
import com.byf.mywiki.mapper.ContentMapper;
import com.byf.mywiki.mapper.DocMapper;
import com.byf.mywiki.mapper.DocMapperCust;
import com.byf.mywiki.req.DocQueryReq;
import com.byf.mywiki.req.DocSaveReq;
import com.byf.mywiki.resp.DocQueryResp;
import com.byf.mywiki.resp.PageResp;
import com.byf.mywiki.util.CopyUtil;
import com.byf.mywiki.util.RedisUtil;
import com.byf.mywiki.util.RequestContext;
import com.byf.mywiki.util.SnowFlake;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DocService {

    private static final Logger LOG = LoggerFactory.getLogger(DocService.class);

    @Resource
    public RedisUtil redisUtil;


    @Resource
    private ContentMapper contentMapper;

    @Resource
    private DocMapper docMapper;
    @Resource
    private DocMapperCust docMapperCust;

    @Resource
    private SnowFlake snowFlake;

//    public List<DocQueryResp> all() {
//        DocExample docExample = new DocExample();
//        docExample.setOrderByClause("sort asc");
//        List<Doc> docList = docMapper.selectByExample(docExample);
//
//        // 列表复制
//        List<DocQueryResp> list = CopyUtil.copyList(docList, DocQueryResp.class);
//
//        return list;
//    }
    public List<DocQueryResp> all(Long ebookId) {
        DocExample docExample = new DocExample();
        docExample.createCriteria().andEbookIdEqualTo(ebookId);
        docExample.setOrderByClause("sort asc");
        List<Doc> docList = docMapper.selectByExample(docExample);

        // 列表复制
        List<DocQueryResp> list = CopyUtil.copyList(docList, DocQueryResp.class);

    return list;
}



    public PageResp<DocQueryResp> list(DocQueryReq req) {
        DocExample docExample = new DocExample();
        docExample.setOrderByClause("sort asc");
        DocExample.Criteria criteria = docExample.createCriteria();
        PageHelper.startPage(req.getPage(), req.getSize());
        List<Doc> docList = docMapper.selectByExample(docExample);

        PageInfo<Doc> pageInfo = new PageInfo<>(docList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        // List<DocResp> respList = new ArrayList<>();
        // for (Doc doc : docList) {
        //     // DocResp docResp = new DocResp();
        //     // BeanUtils.copyProperties(doc, docResp);
        //     // 对象复制
        //     DocResp docResp = CopyUtil.copy(doc, DocResp.class);
        //
        //     respList.add(docResp);
        // }

        // 列表复制
        List<DocQueryResp> list = CopyUtil.copyList(docList, DocQueryResp.class);

        PageResp<DocQueryResp> pageResp = new PageResp();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);

        return pageResp;
    }

    /**
     * 保存
     */
    public void save(DocSaveReq req) {
        Doc doc = CopyUtil.copy(req, Doc.class);
        Content content = CopyUtil.copy(req, Content.class);
        if (ObjectUtils.isEmpty(req.getId())) {
            // 新增
            doc.setId(snowFlake.nextId());
            doc.setViewCount(0);
            doc.setVoteCount(0);
            docMapper.insert(doc);

            content.setId(doc.getId());
            contentMapper.insert(content);
        } else {
            // 更新
            docMapper.updateByPrimaryKey(doc);
            int count = contentMapper.updateByPrimaryKeyWithBLOBs(content);
            if (count == 0) {
                contentMapper.insert(content);
            }
        }
    }

//    public void delete(Long id) {
//        docMapper.deleteByPrimaryKey(id);
//    }

    public void delete(List<String> ids) {
        DocExample docExample = new DocExample();
        DocExample.Criteria criteria = docExample.createCriteria();
        criteria.andIdIn(ids);
        docMapper.deleteByExample(docExample);
    }

    public String findContent(Long id) {
        Content content = contentMapper.selectByPrimaryKey(id);
        // 文档阅读数+1
//        docMapperCust.increaseViewCount(id);
        if (ObjectUtils.isEmpty(content)) {
            return "";
        } else {
            return content.getContent();
        }
    }


    /**
     * 点赞
     */
    public void vote(Long id) {
         docMapperCust.increaseVoteCount(id);
//        // 远程IP+doc.id作为key，24小时内不能重复
        String ip = RequestContext.getRemoteAddr();
        if (redisUtil.validateRepeat("DOC_VOTE_" + id + "_" + ip, 5000)) {
            docMapperCust.increaseVoteCount(id);
        } else {
            throw new BusinessException(BusinessExceptionCode.VOTE_REPEAT);
        }

//        // 推送消息
//        Doc docDb = docMapper.selectByPrimaryKey(id);
//        String logId = MDC.get("LOG_ID");
//        wsService.sendInfo("【" + docDb.getName() + "】被点赞！", logId);
//        // rocketMQTemplate.convertAndSend("VOTE_TOPIC", "【" + docDb.getName() + "】被点赞！");
    }


    public void updateEbookInfo() {
        docMapperCust.updateEbookInfo();
    }

}
