package uz.pdp.lock_market.mapper;

import uz.pdp.lock_market.entity.Comment;
import uz.pdp.lock_market.payload.commentary.CommentRes;

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
}
