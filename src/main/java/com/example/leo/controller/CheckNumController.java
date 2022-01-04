package com.example.leo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.leo.common.dto.CheckRocerdDto;
import com.example.leo.common.dto.SignDto;
import com.example.leo.common.lang.CheckNumParameter;
import com.example.leo.common.lang.Result;
import com.example.leo.entity.CheckNum;
import com.example.leo.entity.CheckRocerd;
import com.example.leo.handler.WalletHandler;
import com.example.leo.service.CheckNumService;
import com.example.leo.service.CheckRocerdService;
import com.example.leo.sign.Sign2PrizeService;
import com.example.leo.sign.SignPrizeService;
import com.example.leo.util.DateUtilZone;
import com.example.leo.util.PageHelper;
import com.example.leo.util.SpringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.Map;

/**
 * <p>
 * 用户签到次数表 前端控制器
 * </p>
 *
 * @author Author：leo
 * @since 2021-12-20
 */
@RestController
@RequestMapping("/check-num")
public class CheckNumController {

    @Autowired
    private CheckNumService checkNumService;

    @Autowired
    private CheckRocerdService checkRocerdService;

    @Autowired
    private SignPrizeService signPrizeService;

    @Autowired
    private Sign2PrizeService sign2PrizeService;

    @Autowired
    private WalletHandler walletHandler;


    @GetMapping("/sign")
    public Result sign(Long userId) {
        if (userId == null) {
            return Result.fail("userId不能为空或0");
        }
        //生成 签到日期入库
        SignDto signDto = new SignDto();
        CheckRocerd checkRocerd = new CheckRocerd().setUserId(userId).setCreateTime(new Date());
        checkRocerdService.save(checkRocerd);
        CheckNum one = checkNumService.getOne(Wrappers.<CheckNum>lambdaQuery().eq(CheckNum::getUserId, userId));
        if (one == null) {
            //第一次打卡
            CheckNum checkNum = new CheckNum().setUserId(userId).setStamp(Boolean.FALSE).setNum(1);
            checkNumService.save(checkNum);
            signDto.setNum(checkNum.getNum());
            signPrizeService = (SignPrizeService) SpringUtils.getBean(checkNum.getNum().intValue() + "SignPrizeService");
            Long amount = signPrizeService.amount();
            walletHandler.change(userId, amount);
            signDto.setAmount(amount);
        } else {
            //多次签到
            String yesterdays = DateUtilZone.yesterdays();
            CheckRocerd rocerd = checkRocerdService.selectYesterdayClock(userId, yesterdays);
            if (rocerd == null) {
                //表示昨天没签到
                one.setNum(1);
                checkNumService.updateById(one);
                signDto.setNum(1);
                signPrizeService = (SignPrizeService) SpringUtils.getBean(1 + "SignPrizeService");
                Long amount = signPrizeService.amount();
                walletHandler.change(userId, amount);
                signDto.setAmount(amount);
            } else {
                Integer num = one.getNum();
                if (num == 7) {
                    num = 0;
                }
                one.setNum(num + 1);
                one.setStamp(Boolean.FALSE);
                checkNumService.updateById(one);
                signDto.setNum(num + 1);
                signPrizeService = (SignPrizeService) SpringUtils.getBean(num + 1 + "SignPrizeService");
                Long amount = signPrizeService.amount();
                walletHandler.change(userId, amount);
                signDto.setAmount(amount);
            }
        }
        return Result.success(signDto);
    }


    @GetMapping("/sign2")
    public Result sign2(Long userId) {
        if (userId == null) {
            return Result.fail("userId不能为空或0");
        }
        //生成 签到日期入库
        SignDto signDto = new SignDto();
        CheckRocerd checkRocerd = new CheckRocerd().setUserId(userId).setCreateTime(new Date());
        checkRocerdService.save(checkRocerd);
        CheckNum one = checkNumService.getOne(Wrappers.<CheckNum>lambdaQuery().eq(CheckNum::getUserId, userId));
        if (one == null) {
            //第一次打卡
            CheckNum checkNum = new CheckNum().setUserId(userId).setStamp(Boolean.FALSE).setNum(1);
            checkNumService.save(checkNum);
            signDto.setNum(checkNum.getNum());
            Long amount = sign2PrizeService.amount(checkNum.getNum());
            walletHandler.change(userId, amount);
            signDto.setAmount(amount);
        } else {
            //多次签到
            String yesterdays = DateUtilZone.yesterdays();
            CheckRocerd rocerd = checkRocerdService.selectYesterdayClock(userId, yesterdays);
            if (rocerd == null) {
                //表示昨天没签到
                one.setNum(1);
                checkNumService.updateById(one);
                signDto.setNum(1);
                Long amount = sign2PrizeService.amount(1);
                walletHandler.change(userId, amount);
                signDto.setAmount(amount);
            } else {
                Integer num = one.getNum();
                if (num == 7) {
                    num = 0;
                }
                one.setNum(num + 1);
                one.setStamp(Boolean.FALSE);
                checkNumService.updateById(one);
                signDto.setNum(num + 1);
                Long amount = sign2PrizeService.amount(num + 1);
                walletHandler.change(userId, amount);
                signDto.setAmount(amount);
            }
        }
        return Result.success(signDto);
    }

    @GetMapping("clock")
    public Result clock(Long userId) {
        if (userId == null) {
            return Result.fail("userId不能为空或0");
        }
        //查询用户今天是否打卡
        String today = DateUtilZone.format(new Date());
        CheckRocerd checkRocerd = checkRocerdService.selectYesterdayClock(userId, today);
        if (checkRocerd == null) {
            return Result.success("今天未签到");
        }
        return Result.success("今天已签到");
    }


    @PostMapping("/getPage")
    public Result getPage(@RequestBody @Valid PageHelper<CheckNumParameter> pageHelper) {
        if (ObjectUtils.isNotEmpty(pageHelper.getSearchParam().getCreateTime())
                && pageHelper.getSearchParam().getCreateTime().length == 2) {
            pageHelper.getSearchParam().setStartTime(pageHelper.getSearchParam().getCreateTime()[0]);
            pageHelper.getSearchParam().setEndTime(pageHelper.getSearchParam().getCreateTime()[1]);
        }
        IPage<CheckRocerdDto> checkRocerdIPage = checkRocerdService.findByPage(pageHelper.buildPage(), pageHelper.getSearchParam());
        return Result.success(checkRocerdIPage);
    }


}
