package com.Backend.server.services;


import com.Backend.server.entities.Post;
import com.Backend.server.exceptions.NotFoundException;
import com.Backend.server.payloads.PostDTO;
import com.Backend.server.repositories.PostRepo;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class PostService {

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private PostRepo postRepo;


    // find all, sortable
    public Page<Post> getAllPosts(int page, int size, String orderBy) {
        if (size >= 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return postRepo.findAll(pageable);
    }


    // find by id
    public Post findById(long id) {
        return postRepo.findById(id).orElseThrow(() -> new NotFoundException(id));
    }


    // delete
    public void findByIdAndDelete(long id) {
        Post found = this.findById(id);
        postRepo.delete(found);
    }


    // get post likes

    public long getPostLikes(long id) {
        Post found = this.findById(id);
        return found.getLikes();
    }


    // add like

    public long addLikeToPost(long id) {
        Post found = this.findById(id);
        found.setLikes(found.getLikes() + 1);
        postRepo.save(found);
        return found.getLikes();
    }


    // remove like
    public long removeLikeToPost(long id) {
        Post found = this.findById(id);
        if (found.getLikes() > 0) {
            found.setLikes(found.getLikes() - 1);
            postRepo.save(found);
        }
        return found.getLikes();
    }


    // add post
    public Post saveNewPost(PostDTO body) {

        Post newPost = new Post();
        newPost.setDate(body.date());
        newPost.setTitle(body.title());
        newPost.setContent(body.content());
        return postRepo.save(newPost);

    }


    // upload post image
    public String uploadImage(MultipartFile file, long id) throws IOException {
        Post found = this.findById(id);
        String url = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        found.setImageUrl(url);
        postRepo.save(found);
        return url;
    }


    // modify post
    public Post updatePost(PostDTO body, long id) {

        Post found = this.findById(id);
        found.setDate(body.date());
        found.setTitle(body.title());
        found.setContent(body.content());
        return postRepo.save(found);
    }


}
