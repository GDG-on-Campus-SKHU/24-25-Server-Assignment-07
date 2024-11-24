package junwatson.mychat.service;

import junwatson.mychat.domain.Member;
import junwatson.mychat.domain.Post;
import junwatson.mychat.dto.PostDto;
import junwatson.mychat.repository.MemberRepository;
import junwatson.mychat.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public PostDto createPost(Long memberId, PostDto postDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

        Post post = Post.builder()
                .member(member)
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .imageUrl(postDto.getImageUrl())
                .build();

        Post savedPost = postRepository.save(post);
        return new PostDto(savedPost.getId(), savedPost.getTitle(), savedPost.getContent(), savedPost.getImageUrl());
    }

    public List<PostDto> getPostsByMember(Long memberId) {
        List<Post> posts = postRepository.findByMemberId(memberId);
        return posts.stream()
                .map(post -> new PostDto(post.getId(), post.getTitle(), post.getContent(), post.getImageUrl()))
                .collect(Collectors.toList());
    }

    public PostDto getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
        return new PostDto(post.getId(), post.getTitle(), post.getContent(), post.getImageUrl());
    }

    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
        postRepository.delete(post);
    }
}