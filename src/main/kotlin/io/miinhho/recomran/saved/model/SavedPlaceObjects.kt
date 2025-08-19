package io.miinhho.recomran.saved.model

@JvmInline
value class SavedPlaceName(val value: String) {
    init {
        require(value.isNotBlank()) { "저장된 장소 이름은 비어있을 수 없습니다" }
        require(value.length <= 100) { "저장된 장소 이름은 100자를 초과할 수 없습니다" }
    }
}