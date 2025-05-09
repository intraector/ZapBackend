package dev.ector.features.users.domain.controller

import dev.ector.database.postgres.PostgresDb
import dev.ector.features.users.domain.interfaces.IUsersController
import dev.ector.features.users.domain.interfaces.IUsersRepo
import dev.ector.features.users.domain.models.User
import dev.ector.features.users.domain.models.UsersReq
import dev.ector.features.users.domain.models.UsersResp
import org.jetbrains.exposed.sql.transactions.transaction

class UsersController(
    val postgres: PostgresDb,
    val repo: IUsersRepo,
) : IUsersController {


    override fun fetch(req: UsersReq): UsersResp {
        return transaction(postgres.db) {
            repo.fetch(req)
        }
    }

    override fun delete(id: Int) {
        transaction(postgres.db) {
            repo.delete(id)
        }
    }

    override fun fetchById(id: Int): User? {
        return transaction(postgres.db) {
            repo.fetchById(id)
        }
    }
}