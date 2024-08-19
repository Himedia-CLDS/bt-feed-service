# bt-feed-service

1. bootrun

```
    git clone
    cd bt-feed-service/
    ./gradlew build
    ./gradlew bootrun
```
  
2. api method

   | 메서드 | URL                                                    | 설명             |
   |-----|--------------------------------------------------------|----------------|
   | GET | /v1/feed                      | 전체 피드 정보를 조회   |
   | GET | /v1/feed?userId=               | 내 전체 피드를 조회   |
   | POST |/v1/feed/insert                |피드 작성|
   | PUT |/v1/feed/update  | 피드수정|
   | PUT| /v1/feed/delete                 | 피드 삭제     |
   | PUT | /v1/feed/like                  | 피드 좋아요기능   |
    | POST| /v1/feed/like                 |좋아요한 피드목록 조회|

3. api 작동
   ```
     GET /v1/feed
     GET /v1/feed?userId=
     POST /v1/feed/insert
         form-data : application/json {"userId": "", "content": ""}, "file" : ""
     PUT /v1/feed/update
         form-data : application/json {"userId": "","id": "", "content": ""}, "file" : ""
     PUT /v1/feed/delete
         JSON : {"id":"", "userId":""}
     GET /v1/feed/like?userId=
     PUT /v1/feed/like
         JSON : {"feedId":"", "userId":""}
   ```

4. 스웨거
   - http://localhost:8080/swagger-ui/index.html
   - Json: http://localhost:8080/v3/api-docs
