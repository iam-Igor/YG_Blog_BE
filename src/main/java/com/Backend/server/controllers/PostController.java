package com.Backend.server.controllers;


import com.Backend.server.entities.Post;
import com.Backend.server.payloads.PostDTO;
import com.Backend.server.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/posts")
public class PostController {


    @Autowired
    private PostService postService;

    @GetMapping("/{id}")
    public Post findById(@PathVariable long id) {

        return postService.findById(id);

    }


    @GetMapping("/all")
    public Page<Post> findAll(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size,
                              @RequestParam(defaultValue = "id") String orderBy) {
        return postService.getAllPosts(page, size, orderBy);
    }

    @DeleteMapping
    public void deletePost(@RequestParam long id) {
        postService.findByIdAndDelete(id);
    }

    @PatchMapping("/like/add/{id}")
    public Long addLike(@PathVariable long id) {
        return postService.addLikeToPost(id);
    }

    @PatchMapping("/like/remove/{id}")
    public Long removeLike(@PathVariable long id) {
        return postService.removeLikeToPost(id);
    }


    @PostMapping("/new")
    public Post saveNewPost(@RequestBody PostDTO body) {
        return postService.saveNewPost(body);
    }

    @PatchMapping("/image/upload/{id}")
    public String uploadPost(@RequestParam("image") MultipartFile file, @PathVariable long id) throws IOException {
        return postService.uploadImage(file, id);
    }

    @PutMapping("/update/{id}")
    public Post updatePost(@RequestBody PostDTO body, @PathVariable long id) {
        return postService.updatePost(body, id);
    }

}
