package uz.pdp.lock_market.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lock_market.payload.category.req.CategoryUpdateReq;
import uz.pdp.lock_market.payload.category.req.CategoryAddReq;
import uz.pdp.lock_market.payload.category.res.CategoryRes;
import uz.pdp.lock_market.payload.base.ApiResult;
import uz.pdp.lock_market.service.CategoryService;
import uz.pdp.lock_market.util.BaseURI;

import java.util.List;
import java.util.Locale;

@RestController
@RequiredArgsConstructor
@RequestMapping(BaseURI.API1 + BaseURI.CATEGORY)
public class CategoryController {
    private final CategoryService categoryService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ApiResult<CategoryRes> addCategory(
            @RequestHeader(value = "Accept-Language", required = false) String lang,
            @RequestBody @Valid CategoryAddReq req) {
        return ApiResult.successResponse(categoryService.add(lang, req));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ApiResult<CategoryRes> updateCategory(
            @RequestHeader(value = "Accept-Language", required = false) String lang,
            @PathVariable("id") long id,
            @RequestBody CategoryUpdateReq req) {
        return ApiResult.successResponse(categoryService.update(lang, id, req));
    }

    @GetMapping("/get/{id}")
    public ApiResult<CategoryRes> getCategory(
            @RequestHeader(value = "Accept-Language", required = false) String lang,
            @PathVariable("id") long id) {
        return ApiResult.successResponse(categoryService.getOne(lang, id));
    }

    @GetMapping("/get-all")
    public ApiResult<List<CategoryRes>> getAllCategory(
            @RequestHeader(value = "Accept-Language", required = false) String lang,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String name) {
        return ApiResult.successResponse(categoryService.getAll(lang, page, size, name));
    }


}
