# LocationInformation

This is a SpringBoot webservice that when provided a zip code will give back a short message with some information about the location.

Endpoint is: /zip-code/{zipCode}

A demo copy is deployed to AWS at: http://locationdata-env.pgpnspmcb3.us-west-2.elasticbeanstalk.com/zip-code/{zipCode}

Example:
Url: http://locationdata-env.pgpnspmcb3.us-west-2.elasticbeanstalk.com/zip-code/97220

Response: "At the location: Portland, the temperature is: 284.77, the timezone is: Pacific Standard Time, and the elevation is: 57.0084114074707"

In the JSUIPage folder there is a very minimalist front end app that connects to the AWS url.
