# Spring Data REST Example - ip2nation web service

This is a sample project that uses Spring Data for its hypermedia-driven REST web services. It includes a basic security configuration, a sample controller for custom web services. The webservice is used for checking the country of an IP address. The project's data is taken from ip2nation.com

### Live Demo
https://api.ip2nation.view-this.info/


### Deployment
1. Make sure you have Java 8 and  Maven installed
2. Go to the project directory and build the application
```sh
mvn package
```
3. Run the application
```sh
java -jar target/ip2nation-1.0.0-SNAPSHOT.jar
```

### Usage

* Get the endpoints
```
http://<host:port>/
```

* List the countries
```
http://<host:port>/countries
```

* Get the country of an IP address
```
http://<host:port>/locate/<ipAddress>
```
Example:
````
http://localhost:8080/locate/172.217.25.14
````
Result:
```
{
	ipAddress: "172.217.25.14",
	country: {
		isoCode2: "US",
		isoCode3: "USA",
		isoCountry: "United States of America",
		country: "United States",
		lat: 38,
		lon: -97
	},
	_links: {
		self: {
			href: "http://localhost:8080/locate/172.217.25.14"
		}
	}
}
```

* Insert Country (Auth required in this example admin/admin)
```
curl -X POST \
  http://localhost:8080/countries \
  -H 'authorization: Basic YWRtaW46cGFzc3dvcmQ=' \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -d '{
	"code": "zz",
	"isoCode2": "ZZ",
	"isoCode3": "ZZZ",
	"country": "zZzzZZZz",
	"lat": 123,
	"lon": 123
}'
```

* Delete Country
```
curl -X DELETE \
  http://localhost:8080/countries/<country_code> \
  -H 'authorization: Basic YWRtaW46cGFzc3dvcmQ=' \
  -H 'cache-control: no-cache'
```