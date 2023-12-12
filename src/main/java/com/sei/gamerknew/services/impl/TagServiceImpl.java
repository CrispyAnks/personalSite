package com.sei.gamerknew.services.impl;

import com.sei.gamerknew.entities.Game;
import com.sei.gamerknew.mapper.TagMapper;
import com.sei.gamerknew.services.TagService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImpl implements TagService {
    @Mapper
    private TagMapper tagMapper;
    @Override
    public void updateTag(Game game) {
        tagMapper.updateTag(game.getTagid(), game.getTag01(), game.getTag02(), game.getTag03());
    }
}
