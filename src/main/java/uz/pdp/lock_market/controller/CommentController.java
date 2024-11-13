package uz.pdp.lock_market.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lock_market.payload.base.ApiResult;
import uz.pdp.lock_market.payload.base.ResBaseMsg;
import uz.pdp.lock_market.payload.comment.CommentAddReq;
import uz.pdp.lock_market.payload.comment.CommentUpdateReq;
import uz.pdp.lock_market.payload.commentary.CommentRes;
import uz.pdp.lock_market.service.CommentService;
import uz.pdp.lock_market.util.BaseURI;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(BaseURI.API1 + BaseURI.COMMENT)
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/create")
    public ApiResult<ResBaseMsg> addComment(@RequestBody CommentAddReq req){
      return ApiResult.successResponse(commentService.add(req));
    }

    @GetMapping("/get/{commentaryId}")
    public ApiResult<List<CommentRes>> getComments(@PathVariable("commentaryId") long commentaryId) {
        return ApiResult.successResponse(commentService.getComments(commentaryId));
    }

    @PostMapping("/update/{id}")
    public ApiResult<ResBaseMsg> updateComment(@PathVariable("id") long id, @RequestBody CommentUpdateReq req){
        return ApiResult.successResponse(commentService.update(id, req));
    }

    @DeleteMapping("/delete/{id}")
    public ApiResult<ResBaseMsg> delete(@PathVariable("id") long id){
        return ApiResult.successResponse(commentService.delete(id));
    }


}
