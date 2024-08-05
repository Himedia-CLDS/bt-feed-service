# bt-feed-service

1. datasource.yml
  ```
        url: jdbc:mysql://.../feed_schema
        name: ...
        password: ...
  ```

2. bootrun

```
    git clone
    cd bt-feed-service/
    ./gradlew build
    ./gradlew bootrun
```
  
3. api method

   | 메서드 | URL                                                    | 설명             |
   |-----|--------------------------------------------------------|----------------|
   | GET | /v1/feed                      | 전체 피드 정보를 조회   |
   | GET | /v1/feed?userId=               | 내 전체 피드를 조회   |
