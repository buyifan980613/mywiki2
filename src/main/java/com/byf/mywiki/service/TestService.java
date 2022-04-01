package com.byf.mywiki.service;

import com.byf.mywiki.domain.Test;
import com.byf.mywiki.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {
    @Autowired
    private TestMapper testMapper;

    public List<Test>  list() {
        return testMapper.list();
    }
}
