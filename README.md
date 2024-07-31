# bt-feed-service

1. Datasource
  Mysql Local Test용 DB입니다 추후에 RDS로 변경할 예정입니다.
  Test가 필요한 경우 무선 랜 IPv4 66으로 접속해주세요 !
  ```
        url: jdbc:mysql://...66:3306/feed_schema
        username: test
        password: test
  ```
  
2. api test

   | 메서드 | URL                                                    | 설명             |
   |-----|--------------------------------------------------------|----------------|
   | GET | /v1/api/feed                      | 전체 피드 정보를 조회   |
   | GET | /v1/api/feed?userId=               | 내 전체 피드를 조회   |
