package dev.ector.features.users.domain.interfaces

import dev.ector.features.users.domain.models.User
import dev.ector.features.users.domain.models.UsersReq
import dev.ector.features.users.domain.models.UsersResp

interface IUsersController {
    fun fetch(req: UsersReq) : UsersResp
    fun delete(id: Int)
    fun fetchById(id: Int) : User?
}