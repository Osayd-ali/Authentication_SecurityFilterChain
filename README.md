## Authenticating oncoming RESTFul api requests through a series of security filters

Implementing application security authentication by adding additional layer of a chain of security filters for oncoming requests. Layered on top of password hashing and user authentication. 

* Every incoming HTTP request passes through a series of filters that perform different checks and transformations before it reached your application code.
* These filters do things like:
    * Check if the request is authenticated
    * Verify that the user has the required roles or permissions
    * Enforce HTTPS or CSRF protection
    * Parse and validate a JWT token (if you’re using token based auth)
* The order of these filters matters. For example, the authentication filter needs to run before the authorization check.
