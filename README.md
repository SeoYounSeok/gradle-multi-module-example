# gradle-multi-module-example

--
#### 1. 프로젝트 컨셉
 - **Layerd Architecture with Multi-Module** 형태로 프로젝트 구성.
 - **Spring IOC** 기능을 적극 활용한 프로젝트입니다. 
 - 모든 모듈들은 **Spring Bean**으로 등록되어야 합니다.
 - 모든 모듈들은 상위 또는 하위 모듈의 구현체에 구애받지 않고 jar파일로 실행 가능하며, 단위 테스트 가능한 형테가 되어야 합니다.
 - 각 레이어의 연결부는 Interface로 만들어진 Adapter를 참조하여 연결되며, 레이어 안에 속한 모듈들 또한 Interface로 만들어진 Adapter( **Type** )를 참조하여 구현됩니다.
 - 특정 레이어 안의 모듈들을 변경하여도 이상없이 동작하여야 합니다. (Ex/ persistence::jpa -> persistence::mybatis)
 - 프로젝트는 영역 별 하나 이상의 모듈들을 **Compound** 모듈에 주입하여 구동합니다. 
 - **Compound**는 resources 파일 주입과 SpringApplication을 구동 역할만 수행합니다.
 - Reactive와 MVC은 구동방식이 완전 다르기 때문에 각 방식에 맞는 모듈로만 구성해야 합니다.


#### 2. Layer 
 - **Database Layer**
    - 데이터베이스 드라이버와 Connection Pool 설정 등을 담당하는 영역입니다.
    - 실제 DB와 Connection을 맺는 설정을 Bean으로 등록합니다. 드라이버 관련 properties 값들은 외부(@Value)에서 주입받거나 모듈 내에 설정값을 직접 작성합니다.

 - **Persistence Layer**
   - 영속성 영역을 담당하는 영역입니다. 이 영역에 서비스 관련 로직은 넣지 않습니다.
   - JPA, QueryDSL, Mybatis 등 다양한 형태로 구현할 수 있고, 도메인 구조와 CRUD를 구현합니다.
   - 영속성에 관련된 Util만 추가하여 사용합니다.

 - **API Layer**
   - 서비스 로직을 담당하는 영역입니다.
   - 서비스관련 로직을 구현하고, Presentation 영역을 구현합니다.

 - **Compound Layer**
   - 모듈들을 합쳐 실제 구동시키는 영역입니다.
   - 서비스에 필요한 모듈들을 모아 프로젝트를 실행시킵니다.



--
#### 실행 커맨드
 - ./gradlew cleanTest test
 - ./gradlew :modules:compound:compound-user:cleanTest test --rerun-tasks
 