package cn.medcn.common.service.impl;

import cn.medcn.common.pagination.MyPage;
import cn.medcn.common.pagination.Pageable;
import cn.medcn.common.service.BaseService;
import cn.medcn.common.utils.LocalUtils;
import cn.medcn.common.utils.SpringUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import java.util.List;

/**
 * Created by lixuan on 2017/1/4.
 */
public abstract class BaseServiceImpl<T> implements BaseService<T> {

    @Override
    public String local(String key) {
        return SpringUtils.getMessage(key);
    }

    @Override
    public String local(String key, Object[] params) {
        return SpringUtils.getMessage(key,params);
    }

    public boolean isAbroad(){
        return LocalUtils.isAbroad();
    }

    @Override
    public T selectOne(T t) {
        return getBaseMapper().selectOne(t);
    }

    @Override
    public List<T> select(T t) {
        return getBaseMapper().select(t);
    }

    @Override
    public int selectCount(T t) {
        return getBaseMapper().selectCount(t);
    }

    @Override
    public T selectByPrimaryKey(Object t) {
        return getBaseMapper().selectByPrimaryKey(t);
    }

    @Override
    public int insert(T t) {
        return getBaseMapper().insert(t);
    }

    @Override
    public int insertSelective(T t) {
        return getBaseMapper().insertSelective(t);
    }

    @Override
    public int delete(T t) {
        return getBaseMapper().delete(t);
    }

    @Override
    public int deleteByPrimaryKey(Object t) {
        return getBaseMapper().deleteByPrimaryKey(t);
    }

    @Override
    public int updateByPrimaryKey(T t) {
        return getBaseMapper().updateByPrimaryKey(t);
    }

    @Override
    public int updateByPrimaryKeySelective(T t) {
        return getBaseMapper().updateByPrimaryKeySelective(t);
    }

    @Override
    public MyPage<T> page(Pageable pageable) {
        startPage(pageable, Pageable.countPage);
        Page<T> page = (Page<T>) select((T) pageable.getParams());
        return MyPage.page2Mypage(page);
    }

    /**
     * 开启分页拦截器
     * @param pageable
     * @param useCount
     */
    protected void startPage(Pageable pageable, boolean useCount){
        PageHelper.startPage(pageable.getPageNum(), pageable.getPageSize(), useCount);
    }

    /**
     * 将page结果转换成MyPage对象
     * @param page
     * @return
     */
    protected <E> MyPage<E> toMyPage(Page<E> page){
        return MyPage.page2Mypage(page);
    }
}
