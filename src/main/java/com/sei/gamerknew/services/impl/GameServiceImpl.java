package com.sei.gamerknew.services.impl;

import com.sei.gamerknew.entities.Game;
import com.sei.gamerknew.entities.Mark;
import com.sei.gamerknew.mapper.GameMapper;
import com.sei.gamerknew.mapper.InfoMapper;
import com.sei.gamerknew.mapper.MarkMapper;
import com.sei.gamerknew.mapper.TagMapper;
import com.sei.gamerknew.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameServiceImpl implements GameService {
    @Autowired
    private GameMapper gameMapper;
    @Autowired
    private MarkMapper markMapper;
    @Autowired
    private InfoMapper infoMapper;
    @Autowired
    private TagMapper tagMapper;


    @Override
    public Game findByGameId(String id) {
        Game game = gameMapper.findById(id);
        Double rating = 0.0;
        if(game != null){
            if(markMapper.getGameRating(game.getGameid()) != null){
                rating = markMapper.getGameRating(game.getGameid());
            }
            game.complete(infoMapper.findById(game.getInfoid()),tagMapper.findByID(game.getTagid()), rating);
        }
        return game;
    }

    @Override
    public List<Game> getGameByPpl() {
        List<Mark> marks = markMapper.OrderByPpl();
        List<Game> games = new ArrayList<>();
        for (Mark mark: marks) {
            Game game = findByGameId(mark.getGameid());
            games.add(game);
        }
        return games;
    }

    @Override
    public List<Game> getGameByReldate() {
        List<Game> games = gameMapper.OrderByReldate();
        for(Game game: games){
            Double rating = 0.0;
            if(markMapper.getGameRating(game.getGameid()) != null){
                rating = markMapper.getGameRating(game.getGameid());
            }
            game.complete(infoMapper.findById(game.getInfoid()),tagMapper.findByID(game.getTagid()), rating);
        }
        return games;
    }

    @Override
    public List<Game> getGameByPplNPlf(int platform) {
        List<Mark> marks = markMapper.OrderByPplNPlf(platform);
        List<Game> games = new ArrayList<>();
        for (Mark mark: marks) {
            Game game = gameMapper.findById(String.valueOf(mark.getGameid()));
            games.add(game);
        }
        return games;
    }

    @Override
    public List<Game> getGame() {
        return gameMapper.getGame();
    }

    @Override
    public void delGame(int gameId) {
        gameMapper.delete(String.valueOf(gameId));
    }

    @Override
    public void updateGame(Game game) {
        gameMapper.update(game.getGameid(), game.getName(), game.getReldate());
    }

    @Override
    public void addNewGame(Game game) {
        infoMapper.add(game.getInfoid(), game.getPlatform(), game.getProducer(), game.getIntro(),game.getMainstaff(), game.getImgsrc());
        tagMapper.add(game.getTagid(), game.getTag01(), game.getTag02(), game.getTag03());
        gameMapper.add(game.getGameid(), game.getName(), game.getReldate(), game.getInfoid(), game.getTagid());
    }

    @Override
    public Game findByGameName(String name) {
        return gameMapper.findByName(name);
    }
}
