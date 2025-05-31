package com.vistagram.app.service.Interface;
import com.vistagram.app.domain.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface PostService {
    PostDto createPost(MultipartFile image, String caption, String poiName, String poiLocation, Long userId);
    Page<PostDto> getTimeline(int page, int size);
    PostDto getPostById(Long postId);
    Page<PostDto> getUserPosts(Long userId, int page, int size);
    Page<PostDto> searchPosts(String query, int page, int size);
    void deletePost(Long postId, Long userId);

}
