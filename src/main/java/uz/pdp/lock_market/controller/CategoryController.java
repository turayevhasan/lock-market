package uz.pdp.lock_market.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lock_market.payload.category.req.CategoryUpdateReq;
import uz.pdp.lock_market.payload.category.req.CategoryAddReq;
import uz.pdp.lock_market.payload.category.res.CategoryRes;
import uz.pdp.lock_market.payload.base.ApiResult;
import uz.pdp.lock_market.service.CategoryService;
import uz.pdp.lock_market.util.BaseURI;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(BaseURI.API1 + BaseURI.CATEGORY)
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/add")
    public ApiResult<CategoryRes> addCategory(@RequestBody @Valid CategoryAddReq req) {
        return ApiResult.successResponse(categoryService.add(req));
    }

    @PutMapping("/update/{id}")
    public ApiResult<CategoryRes> updateCategory(@PathVariable("id") long id, @RequestBody CategoryUpdateReq req){
        return ApiResult.successResponse(categoryService.update(id, req));
    }

    @GetMapping("/get/{id}")
    public ApiResult<CategoryRes> getCategory(@PathVariable("id") long id){
        return ApiResult.successResponse(categoryService.getOne(id));
    }

    @GetMapping("/get-all")
    public ApiResult<List<CategoryRes>> getAllCategory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String name){
        return ApiResult.successResponse(categoryService.getAll(page, size, name));
    }


}
