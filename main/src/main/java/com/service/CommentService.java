package com.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

import com.repository.*;
import com.entity.*;

@Service
public class CommentService
{
    @Autowired
    private CommentsRepository commentsRepository;
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;
    @Autowired
    private TransactionTemplate transactionTemplate;
     public void save(Comments comment)
     {
         commentsRepository.save(comment);
     }
    public List<Comments> getAll()
    {
        return (List<Comments>) commentsRepository.findAll();
    }
    public void create(Comments new_comment,Long  parent_id, String login, int post_id)
    {
        transactionTemplate.execute(status ->
                    {
                        Users user=userService.findByLogin(login);
                        Posts post= postService.get(post_id);

                        post.getListComments().add(new_comment);

                        user.getListComment().add(new_comment);

                        new_comment.setOwner(user);
                        new_comment.setPost(post);
                        if((parent_id!=null)&&(commentsRepository.existsById(parent_id)))
                        {
                            Comments parentComment=commentsRepository.findById(parent_id).get();
                            new_comment.setParentComment(parentComment);
                        }

                        commentsRepository.save(new_comment);

                        return null;
                    });

    }

    public boolean update(Comments post, int id)
    {
       return (boolean) transactionTemplate.execute(new TransactionCallback() {
           public Object doInTransaction(TransactionStatus status) {
               if (commentsRepository.existsById((long) id)) {
                   Comments currentcomment = commentsRepository.findById((long) id).get();
                   if (post.getOwner() == null)
                       post.setOwner(currentcomment.getOwner());
                   if (post.getPost() == null)
                       post.setPost(currentcomment.getPost());
                   if (post.getChildComment() == null)
                       post.setChildComment(currentcomment.getChildComment());
                   if (post.getTitle() == null)
                       post.setTitle(currentcomment.getTitle());
                   if (post.getDateCreate() == null)
                       post.setDateCreate(currentcomment.getDateCreate());
                   if (post.getContent() == null)
                       post.setContent(currentcomment.getContent());
                   if (post.getParentComment() == null)
                       post.setParentComment(currentcomment.getParentComment());

                   post.setId((long) id);
                   commentsRepository.save(post);
                   return Boolean.TRUE;
               } else return Boolean.FALSE;
           }
       });



    }
    public boolean delete(Comments comment)
    {
        return (boolean) transactionTemplate.execute(new TransactionCallback() {
            public Object doInTransaction(TransactionStatus status) {
                if (commentsRepository.existsById(comment.getId())) {
                    comment.setOwner(null);
                    comment.setPost(null);
                    comment.setParentComment(null);
                    commentsRepository.delete(comment);
                    return true;
                }
                return false;
            }
        });
    }
    public  Comments get(int id)
    {
        return  commentsRepository.findById((long) id).get();
    }
}