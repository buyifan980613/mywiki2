package com.byf.mywiki.mapper;

//import com.jiawa.wiki.resp.StatisticResp;

import com.byf.mywiki.resp.StatisticResp;

import java.util.List;

public interface EbookSnapshotMapperCust {

    public void genSnapshot();

    List<StatisticResp> getStatistic();
//
//    List<StatisticResp> get30Statistic();
}
