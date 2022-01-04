package com.example.leo.handler;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.leo.common.exception.CaptchaException;
import com.example.leo.entity.SysWallet;
import com.example.leo.service.SysWalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import java.util.concurrent.TimeUnit;

/**
 * @author leo
 * @version 1.0
 * @desc
 * @date 2021/12/20 17:27
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class WalletHandler {

    @Autowired
    private SysWalletService sysWalletService;

    @Autowired
    private RedissonClient redissonClient;

    public SysWallet getBalance(Long userId) {
        SysWallet sysWallet = sysWalletService.getOne(Wrappers.<SysWallet>lambdaQuery().eq(SysWallet::getUserId, userId));
        if (sysWallet != null) {
            return sysWallet;
        }
        SysWallet wallet = new SysWallet().setUserId(userId).setMoney(0L);
        sysWalletService.save(wallet);
        return wallet;
    }

    @Transactional
    public void change(Long userId, Long money) {
        if (money == 0) {
            return;
        }
        //redis锁
        RLock rLock = redissonClient.getLock("lock.WalletHandler.change." + userId);
        try {
            //等待5秒尝试上锁，强制释放时间10秒
            if (!rLock.tryLock(5L, 10L, TimeUnit.SECONDS)) {
                throw new CaptchaException("app.walletBusy");
            }
        } catch (Exception e) {
            throw new CaptchaException("app.walletBusy");
        }
        //注册事物提交后事件，解锁
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCommit() {
                super.afterCommit();
                rLock.unlock();
            }
        });
        SysWallet sysWallet = getBalance(userId);
        Long reward = sysWallet.getMoney() + money;
        sysWallet.setMoney(reward);
        sysWalletService.updateById(sysWallet);
    }
}
