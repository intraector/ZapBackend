package dev.ector.features.auth.data

import dev.ector.database.postgres.auth.phone.PhoneCodeDto
import dev.ector.database.postgres.auth.phone.PhoneCodesTable
import dev.ector.features.auth.domain.interfaces.IAuthRepo

class AuthRepo() : IAuthRepo {
    override fun createPhoneCode(phone: String) {
        PhoneCodesTable.insert(
            PhoneCodeDto(
                phone = phone,
                code = "000000"
            ),
        )
    }

    override fun deletePhoneCode(phone: String) {
        PhoneCodesTable.delete(phone)
    }


}