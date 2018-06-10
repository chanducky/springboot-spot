# springboot-spot
api to fetch place details from different place search api provider

How to run
Provide google api key to field spot.googleApiKey=YOUR_API_KEY in application.properties
Import project in eclipse and go to JimdoSpotApiApplication.java  and run as Java or SpringBoot Application.
OR open terminal and go to pom.xml directory type mvn spring-boot:run and hit Enter. 

End Point :
1 http://{domain_name}/api/place/search?parameters


parameters

1: required parameter- query

2 : optional parameters-
region,
location,
radius,
language,
minprice and maxprice,
opennow,
pagetoken,
type

2: http://{domain_name}/api/place/details/{placeid} is uri return from place search api which return details about that place by placeid
 
