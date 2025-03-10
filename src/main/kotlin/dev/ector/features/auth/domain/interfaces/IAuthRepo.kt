package dev.ector.features.auth.domain.interfaces

interface IAuthRepo {
     fun createPhoneCode(phone: String);
     fun deletePhoneCode(phone: String);
}