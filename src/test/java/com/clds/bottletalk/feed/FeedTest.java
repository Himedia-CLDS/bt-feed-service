package com.clds.bottletalk.feed;

import com.clds.bottletalk.feed.model.FeedDTO;
import com.clds.bottletalk.feed.model.FeedLikeDTO;
import com.clds.bottletalk.feed.service.FeedService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class FeedTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FeedService feedService;

    @Test
    public void testGetList() throws Exception {
        FeedDTO feed1 = new FeedDTO();
        feed1.setId(1L);
        feed1.setUserId("user1");
        feed1.setContent("First Feed");
        feed1.setCreatedAt(LocalDateTime.now());

        FeedDTO feed2 = new FeedDTO();
        feed2.setId(2L);
        feed2.setUserId("user2");
        feed2.setContent("Second Feed");
        feed2.setCreatedAt(LocalDateTime.now());

        List<FeedDTO> feedList = Arrays.asList(feed1, feed2);

        Mockito.when(feedService.findAllFeed(null)).thenReturn(feedList);

        mockMvc.perform(get("/v1/feed"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.apiData", hasSize(2)))
                .andExpect(jsonPath("$.apiData[0].userId", is("user1")))
                .andExpect(jsonPath("$.apiData[1].userId", is("user2")));
    }

    @Test
    public void testInsert() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.png",
                MediaType.IMAGE_PNG_VALUE, "test content".getBytes());

        FeedDTO feedDTO = new FeedDTO();
        feedDTO.setUserId("user1");
        feedDTO.setContent("테스트 테에스트으으아아");
        feedDTO.setCreatedAt(LocalDateTime.now());

        mockMvc.perform(multipart("/v1/feed/insert")
                        .file(file)
                        .param("feedDTO", "")
                        .content("{\"userId\": \"user1\", \"content\": \"테스트 테에스트으으아아\"}")
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.apiData", is("피드작성 성공")));
    }

    @Test
    public void testUpdate() throws Exception {
        FeedDTO feedDTO = new FeedDTO();
        feedDTO.setId(1L);
        feedDTO.setUserId("user1");
        feedDTO.setContent("피드업뎃");

        Mockito.when(feedService.getUpdateFeed(Mockito.any(FeedDTO.class))).thenReturn(feedDTO);

        mockMvc.perform(put("/v1/feed/getfeed")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"userId\": \"user1\", \"content\": \"피드업뎃\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.apiData.content", is("피드업뎃")));
    }

    @Test
    public void testDelete() throws Exception {
        FeedDTO feedDTO = new FeedDTO();
        feedDTO.setId(1L);
        feedDTO.setUserId("user1");

        Mockito.when(feedService.deleteFeed(Mockito.any(FeedDTO.class))).thenReturn(feedDTO);

        mockMvc.perform(put("/v1/feed/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"userId\": \"user1\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.apiData", is("삭제완료")));
    }

    @Test
    public void testLike() throws Exception {
        FeedLikeDTO likeDTO = new FeedLikeDTO();
        likeDTO.setFeedId(1L);
        likeDTO.setUserId("user1");
        likeDTO.setLiked(true);

        Mockito.when(feedService.likeFeed(Mockito.any(FeedLikeDTO.class))).thenReturn(likeDTO);

        mockMvc.perform(put("/v1/feed/like")  // POST -> PUT으로 수정
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"feedId\": 1, \"userId\": \"user1\", \"isLiked\": true}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.apiData.liked", is(true)));
    }
}
