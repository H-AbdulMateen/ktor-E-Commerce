package com.piashcse.routing

import com.piashcse.controller.CategoryController
import com.piashcse.models.PagingData
import com.piashcse.models.category.AddCategory
import com.piashcse.models.category.DeleteCategory
import com.piashcse.models.category.UpdateCategory
import com.piashcse.models.user.body.JwtTokenBody
import com.piashcse.plugins.RoleManagement
import com.piashcse.utils.ApiResponse
import com.piashcse.utils.Response
import com.piashcse.utils.authenticateWithJwt
import com.papsign.ktor.openapigen.route.path.auth.delete
import com.papsign.ktor.openapigen.route.path.auth.get
import com.papsign.ktor.openapigen.route.path.auth.post
import com.papsign.ktor.openapigen.route.path.auth.put
import com.papsign.ktor.openapigen.route.path.normal.*
import com.papsign.ktor.openapigen.route.response.respond
import com.papsign.ktor.openapigen.route.route
import io.ktor.http.*

fun NormalOpenAPIRoute.categoryRoute(categoryController: CategoryController) {
    route("category") {
        authenticateWithJwt(RoleManagement.SELLER.role) {
            post<Unit, Response, AddCategory, JwtTokenBody>(
                exampleRequest = AddCategory(
                    categoryName = "Mens Cloth"
                )
            ) { _, requestBody ->
                requestBody.validation()
                respond(ApiResponse.success(categoryController.createCategory(requestBody), HttpStatusCode.OK))
            }
            get<PagingData, Response, JwtTokenBody> { params ->
                params.validation()
                respond(ApiResponse.success(categoryController.getCategory(params), HttpStatusCode.OK))
            }
            put<UpdateCategory, Response, Unit, JwtTokenBody> { params, _ ->
                params.validation()
                respond(ApiResponse.success(categoryController.updateCategory(params), HttpStatusCode.OK))
            }
            delete<DeleteCategory, Response, JwtTokenBody> { params ->
                params.validation()
                respond(
                    ApiResponse.success(
                        categoryController.deleteCategory(params), HttpStatusCode.OK
                    )
                )
            }
        }
    }
}