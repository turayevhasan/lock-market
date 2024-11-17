package uz.pdp.lock_market.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.lock_market.entity.Comment;
import uz.pdp.lock_market.entity.Lock;
import uz.pdp.lock_market.enums.ErrorTypeEnum;
import uz.pdp.lock_market.exceptions.RestException;
import uz.pdp.lock_market.mapper.CommentMapper;
import uz.pdp.lock_market.payload.base.ResBaseMsg;
import uz.pdp.lock_market.payload.comment.req.CommentAddReq;
import uz.pdp.lock_market.payload.comment.req.CommentUpdateReq;
import uz.pdp.lock_market.payload.comment.res.CommentRes;
import uz.pdp.lock_market.repository.CommentRepository;
import uz.pdp.lock_market.repository.LockRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final LockRepository lockRepository;

    public ResBaseMsg add(CommentAddReq req) {
        Lock lock = lockRepository.findById(req.getLockId())
                .orElseThrow(RestException.thew(ErrorTypeEnum.LOCK_NOT_FOUND));

        Comment comment = CommentMapper.reqToEntity(req, lock);

        commentRepository.save(comment);
        return new ResBaseMsg("Message sent");
    }


    public ResBaseMsg update(long id, CommentUpdateReq req) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(RestException.thew(ErrorTypeEnum.COMMENT_NOT_FOUND));

        CommentMapper.updateComment(comment, req); //updating

        commentRepository.save(comment);

        return new ResBaseMsg("Comment updated");
    }

    public ResBaseMsg delete(long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(RestException.thew(ErrorTypeEnum.COMMENT_NOT_FOUND));

        commentRepository.delete(comment);
        return new ResBaseMsg("Comment successfully deleted");
    }

    public List<CommentRes> getComments(long lockId) {
        Lock lock = lockRepository.findById(lockId)
                .orElseThrow(RestException.thew(ErrorTypeEnum.LOCK_NOT_FOUND));

        return lock.getComments().stream()
                .map(CommentMapper::entityToDto)
                .toList();
    }
}
