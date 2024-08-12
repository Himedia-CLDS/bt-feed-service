package com.clds.bottletalk.feed.controller;

import com.clds.bottletalk.config.FileConfig;
import com.clds.bottletalk.feed.model.FeedDTO;
import com.clds.bottletalk.feed.model.FeedLike;
import com.clds.bottletalk.feed.service.FeedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("v1/feed")
@Slf4j
public class FeedController {

    private FeedService service;

    public FeedController(FeedService service) {
        this.service = service;
    }

    @GetMapping
    public List<FeedDTO> getList(@RequestParam(required = false) String userId){
        List<FeedDTO> feedList = service.findAllFeed(userId);
        String json = String.format(
          "{\"action\":\"FeedAllList\", \"user_id\": \"%s\"}", userId
        );
        log.info(json);
        return feedList;
    }

    @PostMapping("insert")
    public void insert(@RequestPart FeedDTO feedDTO, @RequestPart MultipartFile file) throws Exception {
        feedDTO.setCreatedAt(LocalDateTime.now());
        feedDTO.setReImgName(FileConfig.saveFile(feedDTO.getUserId(), file).getName());
        feedDTO.setOrgImgName(file.getOriginalFilename());
        service.insertFeed(feedDTO);
        String json = String.format("{\"action\":\"FeedInsert\", \"user_id\": \"%s\"}", feedDTO.getUserId());
        log.info(json);
    }

    @PutMapping("update")
    public FeedDTO update(@RequestPart FeedDTO feedDTO, @RequestPart MultipartFile file) throws Exception{
        FeedDTO feed = service.findFeed(feedDTO.getId(), feedDTO.getUserId());
        if(file != null){
            FileConfig.deleteFile(feed.getReImgName());
            feedDTO.setReImgName(FileConfig.saveFile(feed.getUserId(), file).getName());
            feedDTO.setOrgImgName(file.getOriginalFilename());
        }

        service.updateFeed(feedDTO);
        String json = String.format(
                "{\"action\":\"FeedUpdate\",\"feed_id\":\"%d\" \"user_id\": \"%s\"}",
                feedDTO.getId(), feedDTO.getUserId()
        );
        log.info(json);
        return null;
    }

    @PutMapping("delete")
    public void delete(@RequestBody FeedDTO feedDTO) throws Exception{
        String isDelete = "";
        FeedDTO deleteFeed = service.deleteFeed(feedDTO);
        if(deleteFeed.getReImgName() != null){
            isDelete = String.format("%b", FileConfig.deleteFile(deleteFeed.getReImgName()));
        } else {
            isDelete = "삭제할 파일 없음";
        }
        String json = String.format(
                "{\"action\":\"DeleteFeed\", \"feed_id\":\"%d\", \"user_id\": \"%s\", \"deleteFile\": \"%b\"}",
                feedDTO.getId(), feedDTO.getUserId(), isDelete
        );
        log.info(json);
    }

    @PutMapping("like")
    public FeedLike like(@RequestBody FeedLike feedLike){
        FeedLike likeDto = service.likeFeed(feedLike);
        String json = String.format(
                "{\"action\":\"%s\",\"feed_id\":\"%d\",\"user_id\":\"%s\"\"is_liked\":\"%b\"}",
                "FeedLike", feedLike.getFeedId(), feedLike.getUserId(), likeDto.isLiked()
        );
        log.info(json);
        return likeDto;
    }

    @PostMapping("like")
    public List<FeedDTO> getLike(@RequestParam("userId") String userId){
        List<FeedDTO> likeList = service.findLikedFeed(userId);
        String json = String.format(
                "{\"action\":\"%s\",\"userId\":\"%s\"}","LikeAllFeed", userId
        );
        log.info(json);
        return likeList;
    }
}