package me.wanpin.springboot.service.impl;

import me.wanpin.springboot.dto.CommentDto;
import me.wanpin.springboot.entity.Comment;
import me.wanpin.springboot.entity.Post;
import me.wanpin.springboot.entity.User;
import me.wanpin.springboot.mapper.CommentMapper;
import me.wanpin.springboot.repository.CommentRepository;
import me.wanpin.springboot.repository.PostRepository;
import me.wanpin.springboot.repository.UserRepository;
import me.wanpin.springboot.service.CommentService;
import me.wanpin.springboot.util.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private UserRepository userRepository;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository,UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void createComment(String postUrl, CommentDto commentDto) {

        Post post = postRepository.findByUrl(postUrl).get();
        Comment comment = CommentMapper.mapToComment(commentDto);
        comment.setPost(post);
        commentRepository.save(comment);

    }

    @Override
    public List<CommentDto> findAllComments() {
        List<Comment> comments = commentRepository.findAll();
        return comments.stream()
                .map(CommentMapper::mapToCommentDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    public List<CommentDto> findCommentsByPost() {
        String email = SecurityUtils.getCurrentUser().getUsername();
        User createdBy = userRepository.findByEmail(email);
        Long userId = createdBy.getId();
        List<Comment> comments = commentRepository.findCommentsByPost(userId);
        return comments.stream()
                .map((comment) -> CommentMapper.mapToCommentDto(comment))
                .collect(Collectors.toList());
    }
}
