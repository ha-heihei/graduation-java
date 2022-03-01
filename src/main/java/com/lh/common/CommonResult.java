package com.lh.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author LiHao
 * @create 20220115 17:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult {

    private Boolean success=true;

    private String msg="";

    private Object result;

    public static CommonResult success(String msg,Object result){
        return new CommonResult(true,msg,result);
    }

    public static CommonResult success(String msg){
        return new CommonResult(true,msg,null);
    }

    public static CommonResult success(Object result){
        return new CommonResult(true,"",result);
    }

    public static CommonResult fail(String msg){
        return new CommonResult(false,msg,null);
    }

}
