package uz.pdp.lock_market.mapper;

import uz.pdp.lock_market.entity.Comment;
import uz.pdp.lock_market.entity.Lock;
import uz.pdp.lock_market.payload.comment.CommentAddReq;
import uz.pdp.lock_market.payload.comment.CommentRes;
import uz.pdp.lock_market.payload.comment.CommentUpdateReq;

import static uz.pdp.lock_market.util.CoreUtils.getIfExists;

public interface CommentMapper {
    static CommentRes entityToDto(Comment comment) {
        return CommentRes.builder()
                .name(comment.getName())
                .email(comment.getEmail())
                .text(comment.getText())
                .stars(comment.getStars())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .build();

    }

    static void updateComment(Comment comment, CommentUpdateReq req){
        comment.setName(getIfExists(req.getName(), comment.getName()));
        comment.setEmail(getIfExists(req.getEmail(), comment.getEmail()));
        comment.setStars(getIfExists(req.getStars(), comment.getStars()));
        comment.setText(getIfExists(req.getText(), comment.getText()));
    }

    static Comment reqToEntity(CommentAddReq req, Lock lock) {
        return Comment.builder()
                .name(req.getName())
                .email(req.getEmail())
                .stars(req.getStars())
                .text(req.getText())
                .lock(lock)
                .build();
    }
}
