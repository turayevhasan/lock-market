package uz.pdp.lock_market.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lock_market.payload.comment.res.RatingRes;
import uz.pdp.lock_market.payload.base.ApiResult;
import uz.pdp.lock_market.payload.base.ResBaseMsg;
import uz.pdp.lock_market.payload.comment.req.CommentAddReq;
import uz.pdp.lock_market.payload.comment.req.CommentUpdateReq;
import uz.pdp.lock_market.payload.comment.res.CommentRes;
import uz.pdp.lock_market.service.CommentService;
import uz.pdp.lock_market.util.BaseURI;

import java.util.List;
import java.util.Locale;

@RestController
@RequiredArgsConstructor
@RequestMapping(BaseURI.API1 + BaseURI.COMMENT)
public class CommentController {
    private final CommentService commentService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/add")
    public ApiResult<ResBaseMsg> addComment(
            @RequestHeader(value = "Accept-Language", required = false) String lang,
            @RequestBody @Valid CommentAddReq req) {
        return ApiResult.successResponse(commentService.add(lang, req));
    }

    @PutMapping("/update/{id}")
    public ApiResult<ResBaseMsg> updateComment(
            @RequestHeader(value = "Accept-Language", required = false) String lang,
            @PathVariable("id") long id,
            @RequestBody @Valid CommentUpdateReq req) {
        return ApiResult.successResponse(commentService.update(lang, id, req));
    }

    @GetMapping("/get-rating/{lockId}")
    public ApiResult<RatingRes> getRating(@PathVariable long lockId) {
        return ApiResult.successResponse(commentService.getRating(lockId));
    }

    @GetMapping("/get-all-by-lock/{lockId}")
    public ApiResult<List<CommentRes>> getComments(@PathVariable("lockId") long lockId) {
        return ApiResult.successResponse(commentService.getComments(lockId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ApiResult<ResBaseMsg> delete(
            @RequestHeader(value = "Accept-Language", required = false) String lang,
            @PathVariable("id") long id) {
        return ApiResult.successResponse(commentService.delete(lang, id));
    }

}
