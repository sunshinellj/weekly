package cn.caitc.weekly.service;

import java.util.List;

import cn.caitc.weekly.model.DeptList;

public interface DeptListService {

    /**
     * 查询部门
     *
     * @return 部门列表
     */
    List<DeptList> selectDeptList();

    /**
     * 根据部门id查询名字
     *
     * @return 部门名
     */
    String selectById(String groupId);

}
