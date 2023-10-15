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



<img width="391" alt="Screenshot 2023-10-12 at 21 18 54" src="https://github.com/letitgobaby/gradle-multi-module-example/assets/48303144/a983de10-906b-4866-904e-713d7484f4cc">


--
#### 실행 커맨드
 - ./gradlew cleanTest test
 - ./gradlew :modules:compound:compound-user:cleanTest test --rerun-tasks
 