package com.sparrow.test.Service.response;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponseService {


    public <T> SingleResult<T> getSingleResult(T data) {
        SingleResult<T> result = new SingleResult<>();
        result.setData(data);
        setSuccessResult(result);
        return result;
    }

    public <T> ListResult<T> getListResult(List<T> list) {
        ListResult<T> result = new ListResult<>();
        result.setList(list);
        setSuccessResult(result);
        return result;
    }

    public CommonResult getSuccessResult() {
        CommonResult result = new CommonResult();
        setSuccessResult(result);
        return result;
    }

    public CommonResult getFailResult(String msg) {
        CommonResult result = new CommonResult();
        result.setSuccess(false);
        result.setMessage(msg);
        return result;
    }

    private void setSuccessResult(CommonResult result) {
        result.setSuccess(true);
        result.setMessage("성공했습니다.");
    }
}