package com.example.prj3be.controller;

import com.example.prj3be.domain.Comment;
import com.example.prj3be.domain.Member;
import com.example.prj3be.dto.CommentFormDto;
import com.example.prj3be.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService commentService;

    @GetMapping("list")
    public Page<Comment> commentList(@RequestParam("id") Long boardId, Pageable pageable) {
        Page<Comment> commentList = commentService.commentListAll(boardId, pageable);
        return commentList;
    }


    @PostMapping("add/{boardId}")
    public void save(@PathVariable("boardId") String boardId, @RequestBody CommentFormDto content) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member memberByLogId = commentService.findMemberByLogId(authentication.getName());
        System.out.println("memberByLogId = " + memberByLogId.getLogId());
        System.out.println("authentication = " + authentication.getName());
        commentService.write(boardId, content, memberByLogId);
    }

    @PutMapping("update/{id}")
    public Comment update(@PathVariable Long id, @RequestBody Comment updateComment) {
        Comment editComment = commentService.update(id, updateComment);
        return editComment;
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable Long id) {
        commentService.delete(id);
    }
}
