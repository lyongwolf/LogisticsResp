package com.ly.service;

import com.ly.pojo.BasicData;

import java.util.List;

/**
 * 基础数据
 */
public interface IBasicService {

    /**
     * 根据条件查询基础数据信息
     * @param data
     * @return
     */
    List<BasicData> query(BasicData data) throws Exception;

    /**
     * 根据编号查询基础数据信息
     * @param id
     * @return
     * @throws Exception
     */
    BasicData queryById(Integer id) throws Exception;

    /**
     * 查询出所有的大类数据
     * @return
     * @throws Exception
     */
    List<BasicData> queryAllParentData() throws Exception;

    /**
     * 添加基础数据
     * @param data
     * @return
     */
    Integer addBasicData(BasicData data) throws Exception;

    /**
     * 更新基础数据
     * @param data
     * @return
     */
    Integer updateBasicData(BasicData data) throws Exception;

    /**
     * 根据编号删除基础数据
     * @param id
     * @return
     */
    Integer deleteBasicData(Integer id) throws Exception;

    /**
     * 根据 父类名称 获取对应的 子类信息
     * @param parentName
     * @return
     */
    List<BasicData> queryByParentName(String parentName) throws Exception;

}
