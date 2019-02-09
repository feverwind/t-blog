package com.xsh.blog.service.impl;

import com.xsh.blog.dao.OptionVoMapper;
import com.xsh.blog.model.Vo.OptionVo;
import com.xsh.blog.model.Vo.OptionVoExample;
import com.xsh.blog.service.IOptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.SQLSyntaxErrorException;
import java.util.List;
import java.util.Map;

/**
 * options表的service
 * Created by BlueT on 2017/3/7.
 */
@Service
@Slf4j
public class OptionServiceImpl implements IOptionService {

    @Resource
    private OptionVoMapper optionDao;

    @Override
    public void insertOption(OptionVo optionVo) {
        log.debug("Enter insertOption method:optionVo={}", optionVo);
        optionDao.insertSelective(optionVo);
        log.debug("Exit insertOption method.");
    }

    @Override
    @Transactional
    public void insertOption(String name, String value) {
        log.debug("Enter insertOption method:name={},value={}", name, value);
        OptionVo optionVo = new OptionVo();
        optionVo.setName(name);
        optionVo.setValue(value);
        if (optionDao.selectByPrimaryKey(name) == null) {
            optionDao.insertSelective(optionVo);
        } else {
            optionDao.updateByPrimaryKeySelective(optionVo);
        }
        log.debug("Exit insertOption method.");
    }

    @Override
    @Transactional
    public void saveOptions(Map<String, String> options) {
        if (null != options && !options.isEmpty()) {
            options.forEach(this::insertOption);
        }
    }

    @Override
    public OptionVo getOptionByName(String name) {
        OptionVo optionVo = null;

        try {
            optionVo = optionDao.selectByPrimaryKey(name);
        }catch (BadSqlGrammarException e){
            log.error("SQL执行错误" + e.getMessage());
        }

        return optionVo;
    }

    @Override
    public List<OptionVo> getOptions() {
        return optionDao.selectByExample(new OptionVoExample());
    }
}
