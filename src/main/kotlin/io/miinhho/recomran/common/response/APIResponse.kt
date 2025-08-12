package io.miinhho.recomran.common.response

data class APIResponse(
    val success: Boolean?,
    val statusCode: APIStatusCode,
    val message: String?,
)

enum class APIStatusCode(val code: String) {
    API_SERVICE_DOWN("AD10")
}