package com;



import com.entity.Comments;
import com.entity.Position;
import com.entity.Posts;
import com.entity.Users;
import com.repository.CommentsRepository;
import com.repository.PositionRepository;
import com.repository.PostsRepository;
import com.repository.UsersRepository;
import com.service.CommentService;
import com.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteObject
{


    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private PostsRepository postsRepository;
    @Autowired
    private CommentsRepository commentsRepository;

    public boolean delete(Comments comment)
    {
        if (commentsRepository.existsById(comment.getId()))
        {
            comment.setOwner(null);
            comment.setPost(null);
            comment.setParentComment(null);
            commentsRepository.delete(comment);
            return true;
        }
        return false;
    }
    public boolean deletePosition(Position position)
    {
        if (positionRepository.existsById(position.getId()))
        {
            positionRepository.delete(position);
            return true;
        }
        return false;
    }
    public boolean deletePost(Posts post)
    {
        if (postsRepository.existsById(post.getId()))
        {
            post.setOwner(null);
            postsRepository.delete(post);
            return true;
        }
        return false;
    }

    public boolean deleteUser(int  id)
    {
        if (usersRepository.existsById((long) id))
        {
            Users user= (Users) usersRepository.findById( (long)id ).get();
            user.getListPost().forEach(posts -> { posts.setOwner(null);postService.save(posts);});
            user.getListComment().forEach(comments -> { comments.setOwner(null);commentService.save(comments);});
            usersRepository.delete(user);
            return true;
        } else return  false;
    }
}
