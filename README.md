<div id="top"></div>

<!-- ABOUT THE PROJECT -->
## About The Project

[![Product Name Screen Shot][product-screenshot]](https://example.com)

This is a spring boot application that focuses on user authentication and authorization. JWT has been used as well as using auxiliary apis for sending confirmation emails and sms.


### Built With

This section should list any major frameworks/libraries used to bootstrap your project. Leave any add-ons/plugins for the acknowledgements section. Here are a few examples.

* [Spring Boot](https://spring.io/projects/spring-boot)
* [Postgres](https://www.postgresql.org/)
* [JWT](https://jwt.io/)
* [FreeMarker Template Engine](https://freemarker.apache.org/)
* [Redis](https://redis.io/)
* [Twilio API](https://www.twilio.com/)
* [AfricasTalking API](https://africastalking.com/)



<!-- GETTING STARTED -->
## Getting Started

The following are needed to run the project

### Prerequisites


* Java - atleast java 8
* Postgres Databaase 
* Redis Server
* Twilio API Key
* AficasTalking API Key

### Installation

 To run the application

1. Get the API Keys 
2. Clone the repo
   ```sh
   git clone https://github.com/samwanjo41/auth-app
   ```
3. Make sure that Postgres, Java and Redis are in your local machine

   ```
4. Enter your API in `application.properties`
   ```js
   const API_KEY = 'ENTER YOUR API';
   ```



<!-- USAGE EXAMPLES -->
## Usage

1. To register a user :
``` sh
    POST : http://localhost:8080/api/v1/register
```
2. For login :
``` sh
    POST : http://localhost:8080/api/login
```
3. To test AfricasTalking api:
``` sh
    POST : http://localhost:8080/api/africasTalkin/sms
```




<!-- CONTACT -->
## Contact

Samuel Wanjohi - 

Project Link: [https://github.com/samwanjo41/auth-app]

<p align="right">(<a href="#top">back to top</a>)</p>





