package io.miinhho.recomran.api

import io.miinhho.recomran.api.dto.PlaceDocument

interface KakaoAPIService {
    /**
     * Kakao API 에서 `radius` 이내의 음식점을 조회합니다.
     *
     * @param x 중심 좌표의 경도
     * @param y 중심 좌표의 위도
     * @param radius 검색 반경(미터)
     * @param page 페이지 번호 (nullable)
     * @param size 페이지 크기 (nullable)
     * @return 음식점 목록
     * @throws io.miinhho.recomran.api.exception.KakaoAPIException - Kakao API 의 응답이 유효하지 않았을 때
     */
    fun getPlaces(x: Double, y: Double, radius: Int, page: Int?, size: Int?): List<PlaceDocument>
}