package dev.ector.features.users.domain.interfaces

import dev.ector.features.users.domain.models.UsersReq

interface IUsersController {
    fun fetch(req: UsersReq)
    fun delete(id: Int)
    fun fetchById(id: Int)
}