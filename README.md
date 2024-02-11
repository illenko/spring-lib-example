## Spring boot 3 library and microservice uses it to reduce boilerplate code.

---
#### Stack:
* Kotlin
* Spring Boot 3.2.2
* Spring Webflux.
* Native images support.

---
#### `lib` project includes:
* Optional routes config (see `com.illenko.payment.lib.api.Routes`).
* Internal API model (`com.illenko.payment.lib.api.request/response` packages).
* Handler with optional dependencies on services (`com.illenko.payment.lib.api.handler.Handler`).
* Services interfaces (`com.illenko.payment.lib.api.service`)
* Enums, etc.

---
#### `paypal-integration` project includes:
* Defines `lib` as dependency, see `build.gradle.kts` (`implementation("com.illenko.payment:lib:$paymentLibVersion")`)
* Implement only one of 2 services, defined in `lib` (`com.illenko.payment.paypalintegration.service.CreditServiceImpl`), it makes only `/credit` endpoint enabled, because of `@ConditionalOnBean` annotation used in `lib.Routes`.


---
Please note, if you want to add any new endpoints with new request/response model and build your service in native image, additional info must be added to handler level, to add required info to native executable, see:
```kotlin
@Component
@RegisterReflectionForBinding(
    InternalCreditRequest::class,
    InternalDebitRequest::class,
    InternalCreditResponse::class,
    InternalDebitResponse::class
)
class Handler {
    //
}
```
This problem may just be specific for router-handler approach, but for `@RestController`'s it must work without extra hints.

---
Native image info:
* Size: 128.55 MB
* RAM on start: 33.04MiB
* RAM after benchmark: 101MiB
---

Primitive `/credit` endpoint benchmark:
```shell
ab -n 10000 -c 100 -p credit_request.json -T application/json http://localhost:8080/credit
```
```text
Server Hostname:        localhost
Server Port:            8080

Document Path:          /credit
Document Length:        71 bytes

Concurrency Level:      100
Time taken for tests:   8.518 seconds
Complete requests:      10000
Failed requests:        0
Total transferred:      1420000 bytes
Total body sent:        2430000
HTML transferred:       710000 bytes
Requests per second:    1174.05 [#/sec] (mean)
Time per request:       85.175 [ms] (mean)
Time per request:       0.852 [ms] (mean, across all concurrent requests)
Transfer rate:          162.81 [Kbytes/sec] received
                        278.61 kb/s sent
                        441.42 kb/s total

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    0   0.9      0      58
Processing:     2   84 345.0     29    3417
Waiting:        2   84 345.0     29    3417
Total:          2   85 345.0     29    3417

Percentage of the requests served within a certain time (ms)
  50%     29
  66%     36
  75%     41
  80%     45
  90%     59
  95%    319
  98%    638
  99%   3345
 100%   3417 (longest request)

```