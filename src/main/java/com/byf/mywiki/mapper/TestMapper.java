package com.byf.mywiki.mapper;

import com.byf.mywiki.domain.Test;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TestMapper {
    public List<Test>  list();
}
