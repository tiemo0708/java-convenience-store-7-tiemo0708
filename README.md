# java-convenience-store-precourse
# 전체 구조

```scss
com.convenience
  ├── controller (Presentation Layer)
  │    ├── ProductController.java
  │    ├── PaymentController.java
  │    ├── PromotionController.java
  │    └── MembershipController.java
  ├── service (Application Layer)
  │    ├── ProductService.java
  │    ├── PaymentService.java
  │    ├── PromotionService.java
  │    ├── MembershipService.java
  │    └── ReceiptService.java
  ├── domain (Business Layer)
  │    ├── Product.java
  │    ├── Promotion.java
  │    ├── Membership.java
  │    ├── Receipt.java
  │    └── enums
  │         ├── ProductType.java
  │         └── PromotionType.java
  ├── repository (Persistence Interface)
  │    ├── ProductRepository.java
  │    ├── PromotionRepository.java
  │    ├── MembershipRepository.java
  │    └── ReceiptRepository.java
  ├── infrastructure (Persistence Implementation)
  │    ├── FileProductRepository.java
  │    ├── DatabasePromotionRepository.java
  │    └── FileMembershipRepository.java
  ├── dto (Data Transfer Objects)
  │    ├── ProductPurchaseRequestDTO.java
  │    ├── ReceiptDTO.java
  │    ├── PurchasedProductDTO.java
  │    └── MembershipDiscountDTO.java
  ├── config (Configuration and Dependency Injection)
  │    └── ApplicationConfig.java
  ├── utils (Utility Classes)
  │    ├── InputValidator.java
  │    ├── DateUtils.java
  │    └── FileUtils.java
  └── view (UI Interaction)
       ├── InputView.java
       └── OutputView.java

```

```scss
com.convenience
  ├── controller (Presentation Layer)  // 사용자 요청을 처리하고 결과를 서비스 계층에 위임하는 계층입니다. 주로 REST API 엔드포인트를 제공하며, Presentation Layer 역할을 합니다.
  │    ├── ProductController.java       // 상품 관련 요청을 처리합니다.
  │    ├── PaymentController.java       // 결제 관련 요청을 처리합니다.
  │    ├── PromotionController.java     // 프로모션 관련 요청을 처리합니다.
  │    └── MembershipController.java    // 멤버십 관련 요청을 처리합니다.
  ├── service (Application Layer)       // 비즈니스 로직을 처리하는 계층입니다. 서비스 클래스는 컨트롤러의 요청을 받아 데이터 처리 및 비즈니스 규칙을 적용합니다.
  │    ├── ProductService.java          // 상품의 구매, 재고 관리 등 주요 비즈니스 로직을 처리합니다.
  │    ├── PaymentService.java          // 결제 처리 로직을 담당합니다.
  │    ├── PromotionService.java        // 프로모션 관련 비즈니스 로직을 처리합니다.
  │    ├── MembershipService.java       // 멤버십 관련 할인 적용 로직을 처리합니다.
  │    └── ReceiptService.java          // 구매 영수증 생성 및 계산 로직을 담당합니다.
  ├── domain (Business Layer)           // 도메인 모델을 포함하는 계층입니다. 비즈니스 규칙과 상태를 관리하며, 애플리케이션의 핵심 로직이 포함된 객체들입니다.
  │    ├── Product.java                 // 상품의 정보를 나타내는 도메인 객체입니다.
  │    ├── Promotion.java               // 프로모션 관련 정보를 나타내는 도메인 객체입니다.
  │    ├── Membership.java              // 멤버십 관련 정보를 나타내는 도메인 객체입니다.
  │    ├── Receipt.java                 // 영수증 정보를 담고 있는 도메인 객체입니다.
  │    └── enums                        // 비즈니스 관련 열거형(Enum)들을 정의합니다.
  │         ├── ProductType.java        // 상품의 유형을 정의하는 열거형입니다.
  │         └── PromotionType.java      // 프로모션 유형을 정의하는 열거형입니다.
  ├── repository (Persistence Interface) // 데이터 접근을 위한 추상화 계층입니다. 도메인 객체를 데이터 소스로부터 가져오고 저장하는 인터페이스를 정의합니다.
  │    ├── ProductRepository.java       // 상품 데이터를 관리하기 위한 인터페이스입니다.
  │    ├── PromotionRepository.java     // 프로모션 데이터를 관리하기 위한 인터페이스입니다.
  │    ├── MembershipRepository.java    // 멤버십 데이터를 관리하기 위한 인터페이스입니다.
  │    └── ReceiptRepository.java       // 영수증 데이터를 관리하기 위한 인터페이스입니다.
  ├── infrastructure (Persistence Implementation) // 데이터 접근 인터페이스의 실제 구현체를 포함하는 계층입니다. 파일, 데이터베이스 등과 같은 구체적인 데이터 접근 방법을 정의합니다.
  │    ├── FileProductRepository.java   // 파일을 이용해 상품 데이터를 관리하는 구현체입니다.
  │    ├── DatabasePromotionRepository.java // 데이터베이스를 이용해 프로모션 데이터를 관리하는 구현체입니다.
  │    └── FileMembershipRepository.java // 파일을 이용해 멤버십 데이터를 관리하는 구현체입니다.
  ├── dto (Data Transfer Objects)       // 계층 간 데이터 전송을 위해 사용되는 객체들을 포함합니다. Presentation Layer와 Service Layer 간 데이터 전송을 캡슐화합니다.
  │    ├── ProductPurchaseRequestDTO.java // 상품 구매 요청 데이터를 담은 DTO입니다.
  │    ├── ReceiptDTO.java              // 영수증 정보를 담아 사용자에게 반환하는 DTO입니다.
  │    ├── PurchasedProductDTO.java     // 구매한 상품의 정보를 포함하는 DTO입니다.
  │    └── MembershipDiscountDTO.java   // 멤버십 할인 정보를 포함하는 DTO입니다.
  ├── config (Configuration and Dependency Injection) // 애플리케이션의 의존성 주입 및 설정을 관리하는 계층입니다. 스프링 설정 클래스 등을 포함합니다.
  │    └── ApplicationConfig.java       // 각 빈(bean)을 정의하여 의존성을 설정합니다.
  ├── utils (Utility Classes)           // 유틸리티 기능을 제공하는 클래스들이 포함됩니다. 공통적으로 재사용 가능한 기능들을 제공하여 코드 중복을 줄입니다.
  │    ├── InputValidator.java          // 사용자 입력 값을 검증하는 유틸리티 클래스입니다.
  │    ├── DateUtils.java               // 날짜와 관련된 작업을 처리하는 유틸리티 클래스입니다.
  │    └── FileUtils.java               // 파일 입출력 작업을 처리하는 유틸리티 클래스입니다.
  └── view (UI Interaction)             // 사용자와의 상호작용을 담당하는 계층입니다. 입력을 받고 출력하는 기능을 제공합니다.
       ├── InputView.java               // 사용자로부터 입력을 받는 역할을 담당합니다.
       └── OutputView.java              // 결과를 사용자에게 출력하는 역할을 담당합니다.

```

- **controller (Presentation Layer)**:
    - 사용자 요청을 받아 **Service Layer**로 전달하고, 결과를 사용자에게 반환하는 역할을 합니다.
    - REST API 엔드포인트를 제공하며, 비즈니스 로직을 직접 수행하지 않고 **서비스 계층에 위임**합니다.
- **service (Application Layer)**:
    - 비즈니스 로직을 처리하는 계층으로, 컨트롤러와 **Repository** 사이의 중간 계층 역할을 합니다.
    - 요청된 비즈니스 기능을 실행하며, 필요한 경우 **Repository Interface**를 이용해 데이터를 관리합니다.
- **domain (Business Layer)**:
    - 애플리케이션의 핵심 도메인 객체와 비즈니스 규칙을 포함하는 계층입니다.
    - 상태와 행위(비즈니스 로직)를 관리하며, 도메인 객체의 일관성과 무결성을 보장합니다.
- **repository (Persistence Interface)**:
    - 데이터 접근을 위한 **추상화된 인터페이스**를 제공하며, 데이터를 저장하거나 검색하는 기능을 정의합니다.
    - 인터페이스를 사용하여 **서비스 계층이 구체적인 저장소 구현에 의존하지 않도록** 설계합니다.
- **infrastructure (Persistence Implementation)**:
    - **repository** 인터페이스의 구체적인 구현체를 포함하며, 파일, 데이터베이스 등 **데이터 저장소와의 구체적인 연결**을 담당합니다.
    - 이를 통해 데이터 저장소의 변경에 유연하게 대처할 수 있도록 합니다.
- **dto (Data Transfer Objects)**:
    - 계층 간 데이터를 안전하고 명확하게 전송하기 위한 객체들을 포함합니다.
    - 데이터의 캡슐화를 통해 도메인 객체가 직접 외부로 노출되지 않도록 합니다.
- **config (Configuration and Dependency Injection)**:
    - **의존성 주입**을 관리하고, 스프링 설정을 통해 애플리케이션 내 모든 빈을 생성하고 설정합니다.
    - 의존성 관리를 통해 **결합도를 낮추고 테스트 용이성을 높이는 역할**을 합니다.
- **utils (Utility Classes)**:
    - 공통적으로 사용되는 기능을 제공하여 코드 중복을 줄이고 **재사용성**을 높입니다.
    - 예: 사용자 입력 검증, 파일 입출력, 날짜 처리 등
- **view (UI Interaction)**:
    - 사용자와의 상호작용을 담당하며, **사용자 입력을 받고 결과를 출력**하는 기능을 제공합니다.
    - 이는 주로 콘솔 응용 프로그램에서 사용되며, 실제 사용자와의 인터페이스를 정의합니다.

```scss
Presentation Layer (controller)
    │
    ├── ProductController - 사용자 요청을 처리하고 ProductService 호출
    ├── PaymentController - 사용자 요청을 처리하고 PaymentService 호출
    ├── PromotionController - 프로모션 정보를 처리하고 PromotionService 호출
    └── MembershipController - 멤버십 정보 처리 및 MembershipService 호출
    │
    ▼
Application Layer (service)
    │
    ├── ProductService - 상품 구매, 재고 관리 등의 비즈니스 로직을 수행하며 ProductRepository, PromotionService를 사용
    ├── PaymentService - 결제 처리 및 영수증 관련 로직을 수행하며 필요한 도메인 객체들을 이용
    ├── PromotionService - 프로모션 적용 로직을 처리
    ├── MembershipService - 멤버십 할인 적용 로직을 수행하며 MembershipRepository를 사용
    └── ReceiptService - 구매 영수증 생성을 담당
    │
    ▼
Business Layer (domain)
    │
    ├── Product - 상품의 속성 및 비즈니스 로직을 포함
    ├── Promotion - 프로모션 정보와 관련된 비즈니스 규칙 포함
    ├── Membership - 멤버십 정보와 상태 관리
    ├── Receipt - 영수증 정보와 상태 관리
    └── enums - 도메인 내에서 사용되는 열거형
    │
    ▼
Persistence Layer (repository & infrastructure)
    │
    ├── ProductRepository (interface) - 상품 데이터를 관리하기 위한 인터페이스
    │       └── FileProductRepository (구현체) - 파일을 통해 상품 데이터를 관리
    ├── PromotionRepository (interface) - 프로모션 데이터를 관리하기 위한 인터페이스
    │       └── DatabasePromotionRepository (구현체) - 데이터베이스에서 프로모션 데이터를 관리
    ├── MembershipRepository (interface) - 멤버십 데이터를 관리하기 위한 인터페이스
    │       └── FileMembershipRepository (구현체) - 파일을 통해 멤버십 데이터를 관리
    └── ReceiptRepository (interface) - 영수증 데이터를 관리하기 위한 인터페이스

```

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/3d88502c-9fc6-4aff-8675-b13612bf4f2f/ef9e7ee1-902b-47e5-a118-db58fe531924/image.png)

### readme 작성

## **구현 기능 목록**

1. **환영 인사와 상품명, 가격, 프로모션 이름, 재고 안내**
    - [ ]  [products.md](http://products.md) 로부터 상품 목록을 불러온다
    - [ ]  [promotions.md](http://promotions.md) 로 부터 프로모션 목록을 불러온다
    - [ ]  불러온 파일 들로 양식에 맞게 상품이름, 가격, 프로모션,  재고를 안내한다
        - [ ]  재고가 없으면 “재고 없음” 출력

예시:

```
안녕하세요. W편의점입니다.
현재 보유하고 있는 상품입니다.

- 콜라 1,000원 10개 탄산2+1
- 콜라 1,000원 10개
- 사이다 1,000원 8개 탄산2+1
- 사이다 1,000원 7개
- 오렌지주스 1,800원 9개 MD추천상품
- 오렌지주스 1,800원 재고 없음
- 탄산수 1,200원 5개 탄산2+1
- 탄산수 1,200원 재고 없음
- 물 500원 10개
- 비타민워터 1,500원 6개
- 감자칩 1,500원 5개 반짝할인
- 감자칩 1,500원 5개
- 초코바 1,200원 5개 MD추천상품
- 초코바 1,200원 5개
- 에너지바 2,000원 5개
- 정식도시락 6,400원 8개
- 컵라면 1,700원 1개 MD추천상품
- 컵라면 1,700원 10개
```

1. **재고관리**
    - [ ]  재고를 고려 하여 결제 가능 여부 판단
    - [ ]  상품 구매시 결제된 수량 만큼 재고에서 차감
    - [ ]  프로모션 재고 부터 우선 차감
    - [ ]  재고 부족할 시 일반 재고 차감
2. **구매 기능 구현(사용자에게 구매 상품과 수량을 입력받아 처리)**
    - [ ]  사용자에게 구매 상품과 수량을 입력받는다
    - [ ]  **프로모션 할인**
        - [ ]  상품이 프로모션 기간 내인지 확인하고 적용(현재 날짜와 시간을 가져오려면 `camp.nextstep.edu.missionutils.DateTimes`의 `now()`를 활용}
        - [ ]  프로모션 적용 가능한 상품에 대해 고객이 해당수량 만큼 가져오지 않을 경우 혜택 안내 메시지 출력

      예시:

       ```
       현재 {상품명}은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)
       ```

        - [ ]  프로모션 재고 내에서 적용한다
            - [ ]  프로모션 상품에 대한 재고가 부족해 혜택이 없을 경우 정가로 결제할지 여부 메시지 출력

           ```
           현재 {상품명} {수량}개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)
           
           ```

        - [ ]  프로모션 에 따라 1+1, 1+2 적용
            - [ ]  탄산2+1의 경우 2+1 적용
            - [ ]  반짝할인, MD추천상품인 경우 1+1 적용

        - [ ]  **멤버십 할인**
            - [ ]  **멤버십 할인 적용 여부를 입력 받는다.’**

          예시:

           ```
           멤버십 할인을 받으시겠습니까? (Y/N)
           
           ```

            - [ ]  프로모션 **미적용 금액의 30%**를 할인
            - [ ]  멤버십 할인의 **최대 한도는 8,000원**

1. **영수증 출력 기능**
    - [ ]  고객의 구매 내역과 할인을 요약하여 출력
        - [ ]  구매 상품 내역: 구매한 상품명, 수량, 가격
        - [ ]  증정 상품 내역: 프로모션에 따라 무료로 제공된 증정 상품의 목록
        - [ ]  금액 정보
            - [ ]  총구매액: 구매한 상품의 총 수량과 총 금액
            - [ ]  행사할인: 프로모션에 의해 할인된 금액
            - [ ]  멤버십할인: 멤버십에 의해 추가로 할인된 금액
            - [ ]  내실돈: 최종 결제 금액

   예시:

    ```
    ==============W 편의점================
    상품명		수량	금액
    콜라		3 	3,000
    에너지바 		5 	10,000
    =============증	정===============
    콜라		1
    ====================================
    총구매액		8	13,000
    행사할인			-1,000
    멤버십할인			-3,000
    내실돈			 9,000
    
    ```

2. **추가 구매여부 확인**
    - [ ]  추가 구매 여부를 확인하기 위해 안내 문구를 출력한다.

   예시:

    ```
    감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)
    ```

    - [ ]  “Y” 입력 시 환영 인사와 상품명, 가격, 프로모션 이름, 재고 안내 로 이동
    - [ ]  “N” 입력시 종료

## 예외처리

**구매 입력 부분**

- [ ]  **잘못된 상품명 입력 시 예외 처리**: 사용자가 입력한 상품명이 존재하지 않을 경우, **[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요**를 출력한다.
- [ ]  **잘못된 형식의 입력 예외 처리**: 입력 형식이 `[상품명-수량]` 형태가 아닐 경우, **[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요**를 출력한다
- [ ]  **구매 수량 초과 예외 처리**: 재고 수량을 초과하는 수량을 구매하려 할 경우, **[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요**를 출력한다.

**멤버십& 프로모션 부분**

- [ ]  **멤버십 할인 적용 여부 입력 오류 처리**: 사용자가 멤버십 할인 적용 여부 입력 시 잘못된 값을 입력한 경우, **[ERROR] 잘못된 입력입니다. Y 또는 N을 입력해 주세요**를 출력한다.
- [ ]  **프로모션 적용 확인 오류 처리**: 프로모션 적용 가능 여부를 묻는 질문에서 잘못된 값을 입력할 경우, **[ERROR] 잘못된 입력입니다. Y 또는 N을 입력해 주세요**를 출력한다.
- [ ]  **정가 결제 여부 입력 오류 처리**: 프로모션 재고가 부족하여 일부 상품을 정가로 결제할지 여부를 묻는 질문에 잘못된 값을 입력한 경우, **[ERROR] 잘못된 입력입니다. Y 또는 N을 입력해 주세요**를 출력한다.