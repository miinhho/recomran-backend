User Schema - JPA

- Id, username, password, image, saved_place, history_place
- String, String, String, String, Place[], Place[]

Place Schema - JPA
- Id, place_name, category_name, phone, address_name, road_address_name, url
- String, String, String, String, String, String, String

JWT 보안 설정

REST Client 설정
- 경도와 위도를 받아오는 작업 (프론트엔드)
- 받아온 경도와 위도, 유저 검색 옵션, 거리를 통해 Kakao API 요청
- Kakao API Key 받기
- CategoryGroupCode 를 통해 음식점 카테고리를 미리 입력

받아온 결과를 가공하는 서비스 생성
- 최대 15개의 문서 중 랜덤으로 1개만 고르기
- 결과 페이지 번호를 바꿔 요청해 유저가 여러 번 유효한 장소를 다시 고를 수 있도록