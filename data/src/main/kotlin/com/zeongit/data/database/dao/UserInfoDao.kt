package com.zeongit.data.database.dao

import com.zeongit.data.database.entity.UserInfo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserInfoDao : JpaRepository<UserInfo, Int>, JpaSpecificationExecutor<UserInfo> {
    fun findOneByUserId(userId: Int): Optional<UserInfo>

    fun findAllByNicknameLike(nickname: String): List<UserInfo>
}