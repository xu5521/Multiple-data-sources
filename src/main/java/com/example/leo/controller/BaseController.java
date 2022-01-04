package com.example.leo.controller;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * @author leo
 * @version 1.0
 * @desc
 * @date 2021/11/2 12:31
 */
public class BaseController {
    @Autowired
    HttpServletRequest req;
}
