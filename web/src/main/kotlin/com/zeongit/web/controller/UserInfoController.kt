package com.zeongit.web.controller


import com.zeongit.data.database.entity.UserInfo
import com.zeongit.qiniu.core.component.QiniuConfig
import com.zeongit.qiniu.service.BucketService
import com.zeongit.share.annotations.Auth
import com.zeongit.share.annotations.CurrentUserId
import com.zeongit.share.annotations.CurrentUserInfoId
import com.zeongit.share.annotations.RestfulPack
import com.zeongit.share.exception.NotFoundException
import com.zeongit.share.exception.ProgramException
import com.zeongit.web.dto.UserInfoDto
import com.zeongit.web.service.UserInfoService
import com.zeongit.web.service.UserService
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("userInfo")
class UserInfoController(
        private val userService: UserService,
        private val userInfoService: UserInfoService,
        private val qiniuConfig: QiniuConfig,
        private val bucketService: BucketService) {


    /**
     * 创建及修改
     */
    @Auth(true)
    @PostMapping("save")
    @RestfulPack
    fun save(@CurrentUserId userId: Int, @RequestBody userInfoDto: UserInfoDto): UserInfo {
        val info = try {
            userInfoService.getByUserId(userId)
        } catch (e: NotFoundException) {
            val info = UserInfo()
            info.userId = userId
            info
        }
        info.gender = userInfoDto.gender ?: info.gender
        info.birthday = userInfoDto.birthday ?: info.birthday
        info.nickname = userInfoDto.nickname ?: info.nickname
        info.introduction = userInfoDto.introduction ?: info.introduction
        info.country = userInfoDto.country ?: info.country
        info.province = userInfoDto.province ?: info.province
        info.city = userInfoDto.city ?: info.city
        return userInfoService.save(info)
    }

    /**
     * 修改头像
     */
    @Auth(true)
    @PostMapping("modifiedAvatarUrl")
    @RestfulPack
    fun modifiedAvatarUrl(@CurrentUserId userId: Int, @RequestBody userInfoDto: UserInfoDto): UserInfo {
        val info = userInfoService.getByUserId(userId)
        userInfoDto.avatarUrl.isNullOrEmpty() && throw ProgramException("操作有误")
        bucketService.move(userInfoDto.avatarUrl!!, qiniuConfig.qiniuAvatarBucket, qiniuConfig.qiniuTemporaryBucket)
        info.avatarUrl?.let {
            bucketService.move(it, qiniuConfig.qiniuTemporaryBucket, qiniuConfig.qiniuAvatarBucket)
        }
        info.avatarUrl = userInfoDto.avatarUrl
        return userInfoService.save(info)
    }

    /**
     * 修改背景
     */
    @Auth(true)
    @PostMapping("modifiedBackground")
    @RestfulPack
    fun modifiedBackground(@CurrentUserId userId: Int, @RequestBody userInfoDto: UserInfoDto): UserInfo {
        val info = userInfoService.getByUserId(userId)
        userInfoDto.background.isNullOrEmpty() && throw ProgramException("操作有误")
        bucketService.move(userInfoDto.background!!, qiniuConfig.qiniuBackgroundBucket, qiniuConfig.qiniuTemporaryBucket)
        info.background?.let {
            bucketService.move(it, qiniuConfig.qiniuTemporaryBucket, qiniuConfig.qiniuBackgroundBucket)
        }
        info.background = userInfoDto.background
        return userInfoService.save(info)
    }

    /**
     * 获取用户信息
     */
    @Auth
    @GetMapping("get")
    @RestfulPack
    fun get(@CurrentUserInfoId id: Int): UserInfo {
        return userInfoService.get(id)
    }

    /**
     * 获取用户信息
     */
    @Auth
    @GetMapping("getModifiedPasswordDate")
    @RestfulPack
    fun getModifiedPasswordDate(@CurrentUserInfoId id: Int): Date {
        val info = userInfoService.get(id)
        return userService.get(info.userId).lastModifiedDate!!
    }
}