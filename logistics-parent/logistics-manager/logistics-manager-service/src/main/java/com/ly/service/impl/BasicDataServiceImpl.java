package com.ly.service.impl;

import com.ly.mapper.BasicDataMapper;
import com.ly.pojo.BasicData;
import com.ly.pojo.BasicDataExample;
import com.ly.service.IBasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 基础数据
 */
@Service
public class BasicDataServiceImpl implements IBasicService {

    @Autowired
    private BasicDataMapper basicDataMapper;

    @Override
    public List<BasicData> query(BasicData data) throws Exception {
        BasicDataExample example = new BasicDataExample();
        BasicDataExample.Criteria criteria = example.createCriteria();
        if (data != null) {
            if (data.getBaseName() != null && !"".equals(data.getBaseName())) {
                criteria.andBaseNameEqualTo(data.getBaseName());
            }
            if (data.getParentId() != null && data.getParentId() > 0) {
                criteria.andParentIdEqualTo(data.getParentId());
            }
        }
        return basicDataMapper.selectByExample(example);
    }

    @Override
    public BasicData queryById(Integer id) throws Exception {
        return basicDataMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<BasicData> queryAllParentData() throws Exception {
        BasicDataExample example = new BasicDataExample();
        example.createCriteria().andParentIdIsNull();
        return basicDataMapper.selectByExample(example);
    }

    @Override
    public Integer addBasicData(BasicData data) throws Exception {
        if (data.getParentId() != null && data.getParentId().equals(-1)) {
            // parentId == null 为大类
            data.setParentId(null);
        }
        return basicDataMapper.insertSelective(data);
    }

    @Override
    public Integer updateBasicData(BasicData data) throws Exception {
        if (data.getParentId() != null && data.getParentId().equals(-1)) {
            data.setParentId(null);
        }
        return basicDataMapper.updateByPrimaryKey(data);
    }

    @Override
    public Integer deleteBasicData(Integer id) throws Exception {
        return basicDataMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<BasicData> queryByParentName(String parentName) throws Exception {
        // 根据名称找到数据
        BasicData data = new BasicData();
        data.setBaseName(parentName);
        // 找到常用区间对应的基础数据
        List<BasicData> datas = this.query(data);
        if (datas != null && datas.size() > 0) {
            BasicData bd = datas.get(0);
            BasicData newBd = new BasicData();
            newBd.setParentId(bd.getBaseId());
            List<BasicData> list = this.query(newBd);
            return list;
        }
        return null;
    }
}
