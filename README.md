##회원가입 API
~~~
curl -X POST \
  http://localhost:9000/user/signup \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: 4fe1d5dd-604f-d92d-7208-22eab00baae2' \
  -d '{
	"userId": "test3",
	"userNm": "Hello3",
	"userPwd": "test123123"	
}'
~~~

##로그인 API
~~~
curl -X POST \
  http://localhost:9000/user/signin \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: 50f5fc74-2bbd-194f-4d1b-62ced0c461e9' \
  -d '{
	"userNm": "Hello3",
	"userPwd": "test123123"	
}'
~~~