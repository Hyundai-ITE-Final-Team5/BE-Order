# Order Project

## API 정리



### 🧾ORDER

`GET` **/order/paymethodList** : 결제방법 리스트 가져오기

`GET` **/order/orderlist** : 로그인된 mid로 주문목록 가져오기

`POST` **/order/directorder/{psid}** : 바로주문(1개상품 : psid) 주문하기

`POST` **/order/carttoorder** : 장바구니에서 주문하기 (data : {order: {}, orderItems: []})

`PUT` **/order/cancleorder/{oid}** : oid 주문 취소하기 (update 주문상태 변경)

-----------------------------------------

### 👨‍👩‍👧USER

`GET` **/member/info** : 주문자 정보 가져오기
```
memberInfo : {
  mid : mid,
  ...
}
```
