package io.miinhho.recomran.auth.exception

class InvalidTokenException(override val message: String?) : RuntimeException(message) {
    constructor(): this(null)
}

class InvalidEmailException(override val message: String?) : RuntimeException(message) {
    constructor(): this(null)
}

class InvalidPasswordException(override val message: String?): RuntimeException(message) {
    constructor(): this(null)
}

class ConflictEmailException(override val message: String?): RuntimeException(message) {
    constructor(): this(null)
}