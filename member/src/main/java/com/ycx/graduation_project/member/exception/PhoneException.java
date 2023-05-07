package com.ycx.graduation_project.member.exception;

/**
 * @Description:
 * @Created: with IntelliJ IDEA.
 * @author: 杨晨曦
 * 2023-5: 2023-05-08 16:04
 **/
public class PhoneException extends RuntimeException {

    public PhoneException() {
        super("存在相同的手机号");
    }
}
