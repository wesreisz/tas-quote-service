### Notes:

Deploy:
` cf push CFDemoApp -p target/demo-0.0.1-SNAPSHOT.jar -b https://github.com/cloudfoundry/java-buildpack.git`

```2020-12-13T13:45:53.50-0500 [APP/PROC/WEB/0] ERR Exception in thread "main" java.lang.UnsupportedClassVersionError: com/wesleyreisz/helloworld/demo/DemoApplication has been compiled by a more recent version of the Java Runtime (class file version 55.0), this version of the Java Runtime only recognizes class file versions up to 52.0```

Set the java version to match pom
`cf set-env CFDemoApp JBP_CONFIG_OPEN_JDK_JRE '{ jre: { version: 11.+ }}'`

Deploy Again:
`./mvnw clean install`
` cf push CFDemoApp -p target/demo-0.0.1-SNAPSHOT.jar -b https://github.com/cloudfoundry/java-buildpack.git`

Setting in a manifest:
```
---
applications:
  - name: CFDemoApp-1
    instances: 1
    memory: 1G
    disk: 1G
    path: 'target/demo-0.0.1-SNAPSHOT.jar'
    env:
      JBP_CONFIG_OPEN_JDK_JRE: '{ jre: { version: 11.+ } }'
    buildpacks:
      - 'https://github.com/cloudfoundry/java-buildpack.git'
```

Java tips:
`https://docs.cloudfoundry.org/buildpacks/java/java-tips.html`


Access env variables:
`System.getenv("VCAP_SERVICES");`

Run local redis locally
```
docker run --name value-redis -p 6379:6379 -d redis
```

Let's Add a new Redis Service
`cf marketplace`
`cf create-service p-redis shared-vm CFDemoApp-1-redis`
`cf services`
`cf apps`
`cf bind-service --help`
`cf bind-service CFDemoApp-1 CFDemoApp-1-redis`
`curl https://cfdemoapp-1.apps.tas2.wesleyreisz.com/actuator/env | jq `

Push the code
`cf push`

Load the data locally
```
curl -X POST -H "Content-Type: application/json" -d '{"id":1,"quoteText":"The greatest glory in living lies not in never falling, but in rising every time we fall.","author":"Nelson Mandela"}' http://localhost:8080/quotes/add
curl -X POST -H "Content-Type: application/json" -d '{"id":2,"quoteText":"If you set your goals ridiculously high and its a failure, you will fail above everyone elses success.","author":"James Cameron"}' http://localhost:8080/quotes/add 
curl -X POST -H "Content-Type: application/json" -d '{"id":3,"quoteText":"Im not a great programmer; Im just a good programmer with great habits.","author":"Martin Fowler"}' http://localhost:8080/quotes/add                
```

Load the data on pcf
```
curl -X POST -H "Content-Type: application/json" -d '{"id":1,"quoteText":"The greatest glory in living lies not in never falling, but in rising every time we fall.","author":"Nelson Mandela"}' https://humana-quote-service.apps.tas2.wesleyreisz.com/quotes/add
curl -X POST -H "Content-Type: application/json" -d '{"id":2,"quoteText":"If you set your goals ridiculously high and its a failure, you will fail above everyone elses success.","author":"James Cameron"}' https://humana-quote-service.apps.tas2.wesleyreisz.com/quotes/add 
curl -X POST -H "Content-Type: application/json" -d '{"id":3,"quoteText":"Im not a great programmer; Im just a good programmer with great habits.","author":"Martin Fowler"}' https://humana-quote-service.apps.tas2.wesleyreisz.com/quotes/add                
```