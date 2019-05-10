package cn.caitc.weekly.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.caitc.weekly.dao.DeptListDao;
import cn.caitc.weekly.model.DeptList;
import cn.caitc.weekly.service.DeptListService;

@Service
public class DeptListServiceImpl implements DeptListService {


    @Autowired
    private DeptListDao deptListDao;

    /**
     * 查询部门
     *
     * @return 部门列表
     */
    public List<DeptList> selectDeptList() {
        return deptListDao.selectDeptList();
    }

    /**
     * 根据部门id查询名字
     *
     * @return 部门名
     */
    public String selectById(String groupId) {
        return deptListDao.selectById(groupId);
    }
}
