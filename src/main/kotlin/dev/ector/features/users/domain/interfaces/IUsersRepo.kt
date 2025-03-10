package dev.ector.features.users.domain.interfaces

import dev.ector.features.users.domain.models.User
import dev.ector.features.users.domain.models.UsersReq
import dev.ector.features.users.domain.models.UsersResp

interface IUsersRepo {
    fun create(user: User) : User
    fun fetch(req: UsersReq): UsersResp
    fun fetchById(id: Int): User?
    fun fetchByPhone(phone: String): User?
    fun delete(id: Int)

}