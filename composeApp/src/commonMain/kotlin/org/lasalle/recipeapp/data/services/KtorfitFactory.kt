package org.lasalle.recipeapp.data.services
import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.client.request.accept
import io.ktor.client.request.request
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


object KtorfitFactory {
    val base = "https://recipes.pjasoft.com/api/"

    // CLIENTES  HTTP, ARIOS, FETCH
    // NO TENEMOS LA CETEXA DE QUE LA API REPSONDA SIEMPRE IGUAL
    // QUE PASA SI LA API NO RESPONDE CON EL STATUS CODE 409
    // API TARDA EN RESPONDER -> 105
    private val httpClient = HttpClient{
        expectSuccess = false

        install(HttpTimeout){
            requestTimeoutMillis = 40000
            connectTimeoutMillis = 40000
            socketTimeoutMillis = 40000
        }

        install(ContentNegotiation){
            json(
                Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                }
            )
        }

        defaultRequest{
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)

        }

    }

    private val ktorfit = Ktorfit
        .Builder()
        .httpClient(httpClient)
        .baseUrl(base)
        .build()

    // Ejecutar
    fun getAuthService() : AuthService{
        return ktorfit.createAuthService()
    }

    fun getRecipeService() : RecipeService{
        return ktorfit.createRecipeService()
    }


}