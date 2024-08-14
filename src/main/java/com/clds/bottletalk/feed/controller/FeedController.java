package com.clds.bottletalk.feed.controller;

import com.clds.bottletalk.common.JsonResult;
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
    private FileConfig fileConfig;

    public FeedController(FeedService service, FileConfig fileConfig) {
        this.service = service;
        this.fileConfig = fileConfig;
    }


    @GetMapping
    public JsonResult getList(@RequestParam(name="userId", required = false) String userId){
        List<FeedDTO> feedList = service.findAllFeed(userId);
        if(userId != null){
            String json = String.format(
                    "{\"action\":\"FeedMyList\", \"user_id\": \"%s\"}", userId
            );
            log.info(json);
        }
        if(feedList != null){
            return JsonResult.success(feedList);
        } else {
            return JsonResult.fail("불러오기 실패");
        }
    }

    @PostMapping("insert")
    public JsonResult insert(@RequestPart FeedDTO feedDTO, @RequestPart(required = false) MultipartFile file) throws Exception {
        feedDTO.setCreatedAt(LocalDateTime.now());
        if(file != null){
            feedDTO.setReImgName(fileConfig.saveFile(feedDTO.getUserId(), file).getName());
            feedDTO.setOrgImgName(file.getOriginalFilename());
        }

        service.insertFeed(feedDTO);
        String json = String.format("{\"action\":\"FeedInsert\", \"user_id\": \"%s\"}", feedDTO.getUserId());
        log.info(json);

        if(feedDTO.getCreatedAt() != null){
            return JsonResult.success("피드작성 성공");
        } else {
            return JsonResult.fail("피드작성 실패");
        }
    }

    @PutMapping("update")
    public JsonResult update(@RequestPart FeedDTO feedDTO, @RequestPart MultipartFile file) throws Exception{
        FeedDTO feed = service.findFeed(feedDTO.getId(), feedDTO.getUserId());
        if(file != null){
            fileConfig.deleteFile(feed.getReImgName());
            feedDTO.setReImgName(fileConfig.saveFile(feed.getUserId(), file).getName());
            feedDTO.setOrgImgName(file.getOriginalFilename());
        }

        LocalDateTime updataAt =  service.updateFeed(feedDTO);
        String json = String.format(
                "{\"action\":\"FeedUpdate\",\"feed_id\":\"%d\" \"user_id\": \"%s\"}",
                feedDTO.getId(), feedDTO.getUserId()
        );
        log.info(json);

        if(updataAt != null){
            return JsonResult.success(updataAt);
        } else {
            return JsonResult.fail("업데이트 실패");
        }
    }

    @PutMapping("delete")
    public JsonResult delete(@RequestBody FeedDTO feedDTO) throws Exception{
        String isDelete = "";
        FeedDTO deleteFeed = service.deleteFeed(feedDTO);
        if(deleteFeed.getReImgName() != null){
            isDelete = String.format("%b", fileConfig.deleteFile(deleteFeed.getReImgName()));
        } else {
            isDelete = "삭제할 파일 없음";
        }
        String json = String.format(
                "{\"action\":\"DeleteFeed\", \"feed_id\":\"%d\", \"user_id\": \"%s\", \"deleteFile\": \"%b\"}",
                feedDTO.getId(), feedDTO.getUserId(), isDelete
        );
        log.info(json);

        return JsonResult.success("삭제완료");
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

    @GetMapping("like")
    public JsonResult getLike(@RequestParam("userId") String userId){
        List<FeedDTO> likeList = service.findLikedFeed(userId);
        String json = String.format(
                "{\"action\":\"%s\",\"userId\":\"%s\"}","LikeAllFeed", userId
        );
        log.info(json);
        if(likeList != null){
            return JsonResult.success(likeList);
        } else {
            return JsonResult.fail("불러올 리스트 없음");
        }
    }
}