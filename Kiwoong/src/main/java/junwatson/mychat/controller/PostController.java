package junwatson.mychat.controller;

import junwatson.mychat.dto.PostDto;
import junwatson.mychat.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestParam Long memberId, @RequestBody PostDto postDto) {
        PostDto createdPost = postService.createPost(memberId, postDto);
        return ResponseEntity.ok(createdPost);
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<PostDto>> getPostsByMember(@PathVariable Long memberId) {
        List<PostDto> posts = postService.getPostsByMember(memberId);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPost(@PathVariable Long postId) {
        PostDto post = postService.getPost(postId);
        return ResponseEntity.ok(post);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }
}