## Leonardo Cruz - IDnow project

* Email: leonardlsc@gmail.com
* Phone: number: +49 1577 5770286
* Github: [https://github.com/leonardoluiz](https://github.com/leonardoluiz)
* Linkedin: [https://www.linkedin.com/in/leonardo-cruz-9a86b45/](https://www.linkedin.com/in/leonardo-cruz-9a86b45/)

## add Company

```
curl -X POST \
  http://localhost:9000/api/v1/addCompany \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: 37fa7bc3-de6f-b936-32ad-cceba753b290' \
  -d '{"id": 1, "name": "Test Bank", "sla_time": 60, "sla_percentage": 0.9, "current_sla_percentage": 0.7}"'

```

## add identifications

```
curl -X POST \
  http://localhost:9000/api/v1/startIdentification \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: 97e87964-5097-25a9-58e4-824ee8af6755' \
  -d '{"i
  
```

## start Identification

```
curl -X GET \
  http://localhost:9000/api/v1/identifications \
  -H 'cache-control: no-cache' \
  -H 'postman-token: 609506e5-ab32-4393-586a-bc1245cd3b25'
```
