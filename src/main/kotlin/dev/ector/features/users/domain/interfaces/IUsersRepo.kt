package dev.ector.features.users.domain.interfaces

import dev.ector.features.users.domain.models.User
import dev.ector.features.users.domain.models.UsersReq

interface IUsersRepo {
    fun create(user: User) : User
    fun fetch(req: UsersReq): List<User>
    fun fetchById(id: Int): User?
    fun fetchByPhone(phone: String): User?
    fun delete(id: Int)

}