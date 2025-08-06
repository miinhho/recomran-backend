
## 기능

사용자의 현재 위치를 기반으로 최대 10km 이내 음식점 중 하나를 추천해준다.
Kakao API 의 질의 쿼리를 사용해 사용자가 검색할 수 있도록 해준다.

위치 기반 음식점 검색 (Kakao API)
검색된 결과 중 랜덤 음식점 1개 추천
검색 옵션 (ex. 한식, 중식, 돈까스, 마라탕...)

유저가 음식점을 등록 (선택)
유저가 추천받은 음식점을 기록 및 북마크 (선택)

---
## Tech Stack

### Backend

**Spring Boot 3**
- Kotlin
    - 개발이 편리하다.
- JPA
    - 추상화된 레이어로 SQL 쿼리를 짜는 시간을 줄여준다.
- Spring Security + JWT
    - 세션 방식의 stateful 인증보다 JWT 의 Stateless 인증이 간단하고 빠른 인증이 필요한 도메인에 유리하다고 판단하였다.
- Kakao API
    - 위치 기반 API 중 문서가 잘 되어있으며 사용 제한도 넉넉하다.
- REST Client
    - 외부 API 와 통신하기 위해 필요하다.

React
- TypeScript
- Next.js
    - SSR 을 통해 유저가 가볍고 빠르게 접속할 수 있도록 하고 싶었고, Client 의 상태를 담아야 하는 로직보다는 정보를 표시하는 로직이 주를 이뤄 CSR 보다 SSR 이 유리하다.
- React Query
    - SWR 보다 SSR 에서 유리하다. 또한 캐싱 제어가 명확하고 에러 처리가 안정적이다. 유동적인 모바일 환경의 네트워크에서도 오프라인 요청 보류로 유연한 대처를 할 수 있다.
- Zustand
    - Boilerplate 가 적고 Redux 보다 사용 경험이 많다. Next.js 의 SSR 과도 호환성이 좋다.
- TailwindCSS
    - Emotion 보다 더 작은 번들 크기를 가지고 있으며, 빠르고 간편한 개발이 가능하다. 또한 현대 CSS 모범 수칙을 모두 따르고 있으며 사용 경험이 많아 학습 곡선의 문제가 없다. Shadcn 과 Next.js 라는 검증된 솔루션 사용에도 적합하며 SSR 환경에서 정적 CSS 사용으로 초기 렌더링 속도가 빠르다. 번들링 크기가 크다는 단점이 있지만 optimizing 옵션을 통해 최소한으로 줄일 수 있다. 실제로 Netflix 도 TailwindCSS 를 optimizing 해서 6.5kb 사이즈로 사용하는 사례도 있다.
- Shadcn
    - 빠르게 UI 를 구축할 수 있으며 TailwindCSS 를 사용하여 CSS 스타일링 시스템과 통합된다. 컴포넌트를 모두 커스텀 할 수 있어 자유도가 높다. 디자인 수칙과 색상 수칙을 기본적으로 제공하기 때문에 디자인 시간 투자를 적게 할 수 있다.
