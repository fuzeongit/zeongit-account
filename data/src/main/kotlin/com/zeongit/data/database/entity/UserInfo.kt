package com.zeongit.data.database.entity

import com.zeongit.data.constant.Gender
import com.zeongit.data.constant.UserState
import com.zeongit.share.entity.BaseEntity
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.io.Serializable
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EntityListeners

/**
 * 用户
 *
 * @author fjj
 */
@Entity
@EntityListeners(AuditingEntityListener::class)
class UserInfo() : BaseEntity(), Serializable {
    //性别
    @Column(name = "user_id")
    var userId: Int = 0

    //性别
    @Column(name = "gender")
    var gender: Gender = Gender.MALE

    //生日
    @Column(name = "birthday")
    var birthday: Date = Date()

    //昵称
    @Column(name = "nickname")
    lateinit var nickname: String

    //简介
    @Column(name = "introduction")
    lateinit var introduction: String

    //国家
    @Column(name = "country", length = 32)
    var country: String? = null

    //省份
    @Column(name = "province", length = 32)
    var province: String? = null

    //城市
    @Column(name = "city", length = 32)
    var city: String? = null

    //头像
    @Column(name = "avatar_url")
    var avatarUrl: String? = null

    //背景图
    @Column(name = "background")
    var background: String? = null

    //用户状态（冗余字段）
    @Column(name = "state")
    var state: UserState = UserState.PASS

    constructor(gender: Gender = Gender.UNKNOWN, birthday: Date = Date(), nickname: String = "镜花水月", introduction: String = "镜花水月",
                country: String? = null, province: String? = null, city: String? = null, avatarUrl: String? = null, background: String? = null) : this() {
        this.gender = gender
        this.birthday = birthday
        this.nickname = nickname
        this.introduction = introduction
        this.country = country
        this.province = province
        this.city = city
        this.avatarUrl = avatarUrl
        this.background = background
    }
}
