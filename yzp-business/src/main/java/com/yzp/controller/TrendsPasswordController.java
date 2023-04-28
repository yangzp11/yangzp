package com.yzp.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.yzp.utils.AesUtil;
import com.yzp.utils.RedisUtil;
import com.yzp.vo.res.TrendsPasswordResVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * 动态密码 Controller
 *
 * @author YangZhiPeng
 * @date 2023/4/28 10:45
 */
@RestController
@RequestMapping("/trends")
public class TrendsPasswordController {

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 生成新密码
     */
    public TrendsPasswordResVO handleSavePass(TrendsPasswordResVO resVO, String redisKey) {
        // 随机6位密码
        String password = RandomUtil.randomNumbers(6);
        String decryptPassword = AesUtil.encrypt(password);
        resVO.setPassword(password);
        resVO.setCountdown(60L);
        redisUtil.set(redisKey, decryptPassword, 60, TimeUnit.SECONDS);
        return resVO;
    }

    /**
     * 生成动态密码
     */
    @GetMapping("/password/generate")
    public TrendsPasswordResVO getStoreTrendsPass(@RequestParam("storeId") Integer storeId) {
        TrendsPasswordResVO resVO = new TrendsPasswordResVO();
        String redisKey = "STORE_PASSWORD_KEY_" + storeId;
        Object smsCode = redisUtil.get(redisKey);
        if (ObjectUtil.isNotEmpty(smsCode)) {
            Long expireSecond = redisUtil.getExpire(redisKey);
            //0秒过期的话也重新生成，这个必须要，不然偶尔会返回0秒过期的密码
            if (expireSecond == 0) {
                return this.handleSavePass(resVO, redisKey);
            }
            resVO.setPassword(AesUtil.decrypt(String.valueOf(smsCode)));
            resVO.setCountdown(expireSecond);
            return resVO;
        }
        return this.handleSavePass(resVO, redisKey);
        // 前端VUE代码（静态页面，部分来自：https://blog.csdn.net/weixin_34409822/article/details/89063408）
/*
<template>
  <div class="circle">
    <div class="pie-right">
      <div class="right" :style="`--progress:${progress}deg`"></div>
    </div>
    <div class="pie-left">
      <div class="left" :style="`--progress:${progress2}deg`"></div>
    </div>
    <div class="mask">
      <span>{{ countdown - 1 }}s</span>
      <p>密码：{{ password }}</p>
    </div>
  </div>
</template>

<script>
  import { getStoreTrendsPass } from '@/api/sys/dept'
  export default {
    beforeRouteLeave(to, from, next) {
      clearInterval(this.timer)
      next()
    },
    data() {
      return {
        progress: 0,
        progress2: 0,
        countdown: 1,
        password: 0,
        timer: null, // 定时器
      }
    },
    created: function () {
      this.pageInit()
      this.timer = setInterval(() => {
        //60s一个圈  360/60
        let temporary = this.countdown * 6
        //小于180转第一个圆
        if (temporary <= 180) {
          this.progress = temporary
        }
        //第一个半圆转到180，开始转第二圆
        if (temporary >= 180) {
          this.progress2 = temporary - 180
        }
        //防止重新调接口后，progress为0
        if (!restFlag && temporary >= 180 && this.progress < 180) {
          this.progress = 180
        }
        //达到60秒，重置进度条，重新获取密码
        let restFlag = false
        if (this.countdown === 60) {
          this.countdown = 0
          this.progress = 0
          this.progress2 = 0
          restFlag = true
        }
        //重置，重新调取接口获取密码和时间
        if (restFlag) {
          this.pageInit()
        }
        //秒数自加1
        this.countdown = this.countdown + 1
        console.log('bbbbbbbbbb', this.progress)
        console.log('cccccccccc', this.progress2)
      }, 1000)

      // 离开当前页面时销毁定时器
      this.$once('hook:beforeDestroy', () => {
        clearInterval(this.timer)
        this.timer = null
      })
    },
    methods: {
      pageInit() {
        const params = new URLSearchParams()
        params.append('storeId', 1)
        getStoreTrendsPass(params).then((res) => {
          this.countdown = 60 - res.data.data.countdown + 1
          this.password = res.data.data.password
        })
      },
    },
  }
</script>

<style lang="scss" scoped>
  //css
  .circle {
    //这个元素可以提供进度条的颜色
    position: absolute;
    height: 200px;
    width: 200px;
    border-radius: 50%;
    background: red; //注意这是表示当前进度的颜色
  }
  .pie-right,
  .pie-left {
    //这俩元素主要是为了分别生成两个半圆的，所以起作用的地方在于clip裁掉另一半
    position: absolute;
    top: 0;
    left: 0;
    height: 200px;
    width: 200px;
    border-radius: 50%;
  }
  .right,
  .left {
    position: absolute;
    top: 0;
    left: 0;
    height: 200px;
    width: 200px;
    border-radius: 50%;
    background: #f0f0f0; //注意这个才不是当前进度的颜色
  }
  .pie-right,
  .right {
    //裁掉左边一半
    clip: rect(0, auto, auto, 100px);
    transform: rotate(var(--progress));
  }
  .pie-left,
  .left {
    //裁掉右边一半
    clip: rect(0, 100px, auto, 0);
    transform: rotate(var(--progress));
  }
  .mask {
    //我是遮罩 mask不用改 好欣慰
    position: absolute;
    top: 25px;
    left: 25px;
    height: 150px;
    width: 150px;
    background: #fff;
    border-radius: 50%;
    text-align: center;
  }
</style>
*/
    }

}
