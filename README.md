# HW5_2_1223223
> 안드로이드 스튜디오(자바 활용) 공부 - 스마트 모바일 프로그래밍 과제 5        
> 일기장 만들기      

### 과제 내용(간단한 일기장 만들기)
1. 날짜를 선택했을 때 그 날짜의 일기가 없으면 일기를 새로 쓸 수 있다.
2. 그 날짜의 일기가 있으면 일기 내용을 보여준다.

### 학습 목표
1) 데이터베이스의 기본 개념을 이해한다.
2) SQlite 사용법을 익힌다.
3) SQlite를 이용하여 앱을 개발한다.

### 실행 화면
<img width="402" alt="과제 5 일기장 실행 화면" src="https://user-images.githubusercontent.com/68562176/122410124-9d9d4e00-cfbe-11eb-82dc-1abc69964ab0.png">

---------
#### SQL 문 정리
* 테이블 생성
CREATE TABLE 테이블 이름 (
    속성_이름 데이터_타입 [NOT NULL] [DEFAULT 기본_값]             
    [PRIMARY KEY (속성_리스트)]        
    [UNIQUE (속성_리스트)]        
    [FOREIGN KEY (속성_리스트) REFERENCES 테이블_이름(속성_리스트)]         
    [ON DELETE 옵션][ON UPDATE 옵션]              
    [CONSTRAINT 이름] [CHECK(조건)]           
);
* 새로운 속성 추가
ALTER TABLE 테이블_이름 ADD 속성_이름 데이터_타입 [NOT NULL] [DEFAULT 기본_값];  
* 데이터 삽입
INSERT INTO 테이블_이름[(속성_리스트)] VALUES (속성값_리스트);
(속성 리스트를 쓰지 않을 경우 모든 속성의 값이 VALUES에 일대일로 대응되어야 함)
