package uz.pdp.lock_market.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.lock_market.entity.Comment;
import uz.pdp.lock_market.entity.Commentary;
import uz.pdp.lock_market.enums.ErrorTypeEnum;
import uz.pdp.lock_market.exceptions.RestException;
import uz.pdp.lock_market.mapper.CommentMapper;
import uz.pdp.lock_market.payload.base.ResBaseMsg;
import uz.pdp.lock_market.payload.comment.CommentAddReq;
import uz.pdp.lock_market.payload.comment.CommentUpdateReq;
import uz.pdp.lock_market.payload.commentary.CommentRes;
import uz.pdp.lock_market.repository.CommentRepository;
import uz.pdp.lock_market.repository.CommentaryRepository;

import java.util.List;

import static uz.pdp.lock_market.util.CoreUtils.getIfExists;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentaryRepository commentaryRepository;
    private final CommentRepository commentRepository;

    public ResBaseMsg add(CommentAddReq req){
        Commentary commentary = commentaryRepository.findById(req.getCommentaryId())
                .orElseThrow(RestException.thew(ErrorTypeEnum.COMMENTARY_NOT_FOUND));

        Comment comment = Comment.builder()
                .name(req.getName())
                .email(req.getEmail())
                .stars(req.getStars())
                .text(req.getText())
                .commentary(commentary)
                .build();

        commentRepository.save(comment);
        return new ResBaseMsg("sent");
    }


    public ResBaseMsg update(long id, CommentUpdateReq req) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(RestException.thew(ErrorTypeEnum.COMMENT_NOT_FOUND));

        comment.setName(getIfExists(req.getName(), comment.getName()));
        comment.setEmail(getIfExists(req.getEmail(), comment.getEmail()));
        comment.setStars(getIfExists(req.getStars(), comment.getStars()));
        comment.setText(getIfExists(req.getText(), comment.getText()));
        commentRepository.save(comment);
        return new ResBaseMsg("updated");
    }

    public ResBaseMsg delete(long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(RestException.thew(ErrorTypeEnum.COMMENT_NOT_FOUND));

        commentRepository.delete(comment);
        return new ResBaseMsg("Comment successfully deleted");
    }

    public List<CommentRes> getComments(long commentaryId) {
        Commentary commentary = commentaryRepository.findById(commentaryId)
                .orElseThrow(RestException.thew(ErrorTypeEnum.COMMENTARY_NOT_FOUND));

        return commentary.getComments().stream()
                .map(CommentMapper::entityToDto)
                .toList();
    }
}
