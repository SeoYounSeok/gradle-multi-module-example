# gradle-multi-module-example

 - java 11
 - gradle v7.6.1
 - spring-boot v2.7.10
 - spring-management v1.0.15.RELEASE


--
#### 프로젝트 컨셉
 - 각 모듈을 계층, 기능, 도메인로 구성
 - 최종 Compound 계층을 통해 필요한 모듈 구성
 - 멀티 모듈을 이용하여 유연한 서비스 배포


~~~

multimodule-example/       <- 루트 프로젝트
│
├── build.gradle     <- 루트 프로젝트 빌드 스크립트
├── settings.gradle  <- 루트 프로젝트 설정 스크립트
│
├── modules/          <- 각 모듈을 포함하는 디렉터리
│   │
│   ├── database/
│   │   ├── database-common
│   │   ├── database-interface
│   │   └── database-user
│   │
│   ├── persistence/
│   │   ├── persistence-common
│   │   ├── persistence-user-interface
│   │   ├── persistence-user-r2dbc
│   │   ├── persistence-user-jpa
│   │   └── persistence-user
│   │
│   ├── database-user-interface/
│   │   ├── build.gradle
│   │   └── src/
│   │
│   ├── database-user/
│   │   ├── build.gradle
│   │   └── src/
│   │
│   ├── ...
│   │
│   ├── user-api/
│   │   ├── build.gradle
│   │   └── src/
│   │
│   ├── event-api/
│   │   ├── build.gradle
│   │   └── src/
│   │
│   ├── ...
│
└── ...
~~~