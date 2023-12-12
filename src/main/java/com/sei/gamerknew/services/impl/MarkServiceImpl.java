package com.sei.gamerknew.services.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sei.gamerknew.entities.Mark;
import com.sei.gamerknew.entities.PageBean;
import com.sei.gamerknew.mapper.MarkMapper;
import com.sei.gamerknew.services.MarkService;
import com.sei.gamerknew.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MarkServiceImpl implements MarkService {
    @Autowired
    MarkMapper markMapper;


    @Override
    public PageBean<Mark> findByUserId(int pageNum, int pageSize) {
        PageBean<Mark> pb = new PageBean<>();
        PageHelper.startPage(pageNum, pageSize);
        try{
            Map<String, Object> userMap = ThreadLocalUtil.get();
            Page<Mark> p = (Page<Mark>)markMapper.findMarkByUserId((String) userMap.get("id"));
            pb.setTotal(p.getTotal());
            pb.setItems(p.getResult());
            return pb;
        }catch (Exception e){
            throw new RuntimeException("please login!");
        }

    }

    @Override
    public PageBean<Mark> findByGameId(String gameId, int pageNum, int pageSize) {
        return null;
    }

    @Override
    public void markUp(Mark mark) {
        markMapper.add(mark.getMarkid(), mark.getStatus(), mark.getRating(), mark.getComment(), mark.getUserid(), mark.getGameid());
    }

    @Override
    public void updateMark(Mark mark) {
        markMapper.update(mark.getMarkid(), mark.getStatus(), mark.getRating(),mark.getComment());
    }

    @Override
    public void delMark(String markId) {
        markMapper.delMark(markId);
    }
}
