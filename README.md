# Spring Security with Spring Boot 2.0で簡単なRest APIを実装する の写経
# https://qiita.com/rubytomato@github/items/6c6318c948398fa62275
# https://github.com/rubytomato/demo-security-jwt-spring2



Development environment

* Java 1.8.0
* Spring Boot 2.0.6
* H2
* Maven 3.5.4

## Build & Run

using an embedded database H2.

```text
mvn clean package
```

```text
java -jar .\target\demo.jar
```

## test user

|email                  |password         |admin  |
|:----------------------|:----------------|:------|
|kkamimura@example.com  |iWKw06pvj        |true   |
|rsakuma@example.com    |sk10ZIaiq        |false  |
|tyukinaga@example.com  |me02yFufL        |false  |
|zsawatari@example.com  |FjqU39aia        |false  |
|ehiyama@example.com    |ruFOep18r        |false  |

## API

### pre login API

ログイン時に必要なCSRFトークンを取得する

```text
curl -i -c cookie.txt "http://localhost:9000/app/prelogin"
```

example

```text
type cookie.txt

# Netscape HTTP Cookie File
# http://curl.haxx.se/docs/http-cookies.html
# This file was generated by libcurl! Edit at your own risk.

#HttpOnly_localhost	FALSE	/app	FALSE	0	XSRF-TOKEN	b6554c4e-810e-431b-8244-5e43270a5c30
#HttpOnly_localhost	FALSE	/app	FALSE	0	JSESSIONID	E56CF9C4BDD6638071D83A7E5B093991
```


### login API

プレログインAPIで取得したCSRFトークンを_csrfパラメータに指定する

```text
curl -i -b cookie.txt -c cookie.txt -X POST "http://localhost:9000/app/login" -d "email=kkamimura@example.com" -d "pass=iWKw06pvj" -d "_csrf={CSRF_TOKEN}"
```

### logout API

```text
curl -i -b cookie.txt -H "x-xsrf-token:{CSRF_TOKEN}" -X POST "http://localhost:9000/app/logout"
```

### No authentication required API

```text
curl -i -b cookie.txt "http://localhost:9000/app/hello"
```

```text
curl -i -b cookie.txt "http://localhost:9000/app/hello/{message}"
```

認証が不要でもPOST時はCSRFトークンが必要

```text
curl -i -b cookie.txt -X POST "http://localhost:9000/app/hello" -d "message=world" -d "_csrf={CSRF_TOKEN}"
```

### These APIs do not need roles

```text
curl -i -b cookie.txt "http://localhost:9000/app/memo/1"
```

```text
curl -i -b cookie.txt "http://localhost:9000/app/memo/list"
```

### These APIs requiring authentication and USER role

```text
curl -i -b cookie.txt "http://localhost:9000/app/user"
```

```text
curl -i -b cookie.txt "http://localhost:9000/app/user/echo/{message}"
```

```text
curl -i -b cookie.txt -H "Content-Type:application/json" -H "x-xsrf-token:{CSRF_TOKEN}" -X POST "http://localhost:9000/app/user/echo" -d "{\"message\": \"hello world\"}"
```

### These APIs requiring authentication and ADMIN role

```text
curl -i -b cookie.txt "http://localhost:9000/app/admin"
```

```text
curl -i -b cookie.txt "http://localhost:9000/app/admin/echo/{message}"
```

```text
curl -i -b cookie.txt -H "Content-Type:application/json" -H "x-xsrf-token:{CSRF_TOKEN}" -X POST "http://localhost:9000/app/admin/echo" -d "{\"message\": \"hello world\"}"
```
