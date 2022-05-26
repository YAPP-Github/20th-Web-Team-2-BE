# Isoland (외딴썸)
[20th] Web 2팀 BackEnd

<br/>

## 전체 아키텍쳐
![스크린샷 2022-05-22 오후 10 49 15](https://user-images.githubusercontent.com/31160622/169699107-9ab4e347-6fd7-491c-8e02-3f5dc1482551.png)

### 간략한 설명
1. Api Gateway - 요청의 일원화, 인증, 로드밸런싱
2. Discovery Server - 각각의 서버의 정보를 관리. 새로운 컴포넌트 혹은 특정 컴포넌트를 추가할 때 유동적으로 라우팅.
3. Config Server - 각 서버의 필요한 셋팅 정보 관리. Configuration 정보가 수정되었을 때 유연하게 대처. 
4. User Server - 사용자 기능 담당 컴포넌트
5. Matching Server - 매칭 기능 담당 컴포넌트
6. Payment Server - 결제 기능 담당 컴포넌트

### 고려 사항
1. 위의 구조 및 설명들은 이후 진행되는 과정에 따라 바뀔 여지가 있습니다. ( ex. caching)
2. 간략한 전체적인 구조이며 내부적으로 추가적인 프로그램이 존재할 수 있습니다. ( ex. 배치 프로그램)

### Git Convention

**각 커밋 메시지는 다음과 같은 타입을 갖는다.**

- feat : 새로운 기능 추가
- fix : 버그 수정
- docs : 문서 수정 (주석, .md 등등)
- test : 테스트 코드 추가 및 수정
- refactor : 코드 리팩토링
- style : 코드 의미에 영향을 주지 않는 변경사항
- chore : 빌드 부분 혹은 패키지 매니저 수정
- rename : 파일, 폴더명 수정하거나 옮기는 경우

### Branch Strategy

**git flow 전략을 통해 개발한다.**

- main : 제품으로 출시될 수 있는 브랜치
- develop : 다음 출시 버전을 개발하는 브랜치
- feature : 기능을 개발하는 브랜치
- release : 이번 출시 버전을 준비하는 브랜치
- hotfix : 출시 버전에서 발생한 버그를 수정 하는 브랜치
