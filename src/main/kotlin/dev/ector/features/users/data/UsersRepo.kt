package dev.ector.features.users.data

import dev.ector.database.postgres.users.UsersTable
import dev.ector.features.users.data.convert.toUser
import dev.ector.features.users.data.convert.toUserDto
import dev.ector.features.users.domain.interfaces.IUsersRepo
import dev.ector.features.users.domain.models.User
import dev.ector.features.users.domain.models.UsersReq

class UsersRepo() : IUsersRepo {


    override fun create(user: User): User {
        UsersTable.insert(
            user.toUserDto()
        )
        return user
    }

    override fun fetch(req: UsersReq): List<User> {
        return UsersTable.fetch(req).map { it.toUser() }
    }

    override fun fetchById(id: Int): User? {
        return UsersTable.fetchById(id)?.toUser()

    }

    override fun fetchByPhone(phone: String): User? {
        return UsersTable.fetchByPhone(phone)?.toUser()
    }


    override fun delete(id: Int) {
        UsersTable.delete(id)

    }

}