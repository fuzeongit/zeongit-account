package com.zeongit.web.dto

import com.zeongit.share.enum.VerificationCodeOperation
import com.zeongit.share.exception.ProgramException

class SendCodeDto {
    var phone: String = ""
        get() {
//            if (!RegexUtil.checkMobile(field)) {
//                throw ProgramException("请输入正确的手机号码")
//            }
            if (field.isEmpty()) {
                throw ProgramException("请输入正确的手机号码")
            }
            return field
        }

    lateinit var verificationCodeOperation: VerificationCodeOperation
}