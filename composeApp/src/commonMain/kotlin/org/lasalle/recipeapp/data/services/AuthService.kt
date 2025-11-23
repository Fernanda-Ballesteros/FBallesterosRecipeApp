package org.lasalle.recipeapp.data.services

import org.lasalle.recipeapp.models.AuthResponse
import org.lasalle.recipeapp.models.RegisterBody
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.Path
import org.lasalle.recipeapp.models.LoginBody

interface AuthService {
    @POST("auth/register")
    suspend fun register(@Body request: RegisterBody) : AuthResponse

    @POST("auth/login")
    suspend fun login(@Body resquest: LoginBody) : AuthResponse

}