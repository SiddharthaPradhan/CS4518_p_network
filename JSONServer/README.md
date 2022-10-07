# The Fake Server 

This is a simple server that provides basic RESTful APIs (e.g., GET, POST, PUT, DELETE). 

We provide two starting JSON databases. 

- simple.json: it is a minimal example that is useful for debug. It uses the same JSON object design as the `db.json` 
- db.json: the full version you should use for the assignment. 

To start the web service, in your command line terminal (e.g., iTerm for Mac, or the built-in terminals of VSCode or Android Studio), run `json-server -H 127.0.0.1 db.json`. 
This will start the server listening at host address 127.0.0.1 and the default port of 3000. 

## Dependencies  

The only required dependency is "json-server", which you can install by running this command "npm i json-server" inside your local development machine. 

- The official page for the json-server package:  https://www.npmjs.com/package/json-server 
- The video tutorial that provides a high-level overview about what you can do with "json-server": https://egghead.io/lessons/javascript-creating-demo-apis-with-json-server  (The video is a number of years old and the web changes fast, so it is likely some of the specific commands shown in the video might not work. This video is only meant to provide an intuitive understanding about json-server.)



## Common debugging tips 

- Make sure in your Android app you are using the correct IP address "10.0.2.2" if your server runs locally at "127.0.0.1"
    1. If you are curious, read here for more information: https://developer.android.com/studio/run/emulator-networking.html 


- If you run into errors such as "java.net.ConnectException: failed to connect to /10.0.2.2 (port 3000)" in your Android app, you could check the following: 
    1. Is your server actually listening in port 3000? (Note that in MacOS, some of the ports such as 5000 are reserved, and cannot be used to run your own local server.)
    2. Did you pass the `-H` option to explicitly declare the host IP the local server will listen?
    3. Can your browser successfully connect to the server and download the information via URL such as "http://127.0.0.1:3000/people" and "http://localhost:3000/people"? 
    4. Can other HTTP clients like `curl` (command line utility) and `REST client` (VSCode plugin) reach your servers?


## Additional useful resources 

- If you are not familiar with the HTTP requests, this link provides a short description: https://developer.mozilla.org/en-US/docs/Web/HTTP/Methods 
  - If you want to learn more on this topic, courses such as webware or networking probably cover more in-depth topics 

- If you would like to generate your own fake data, you can install a package called "faker" by running "npm install @faker-js/faker --save-dev" 
  - its official website contains good examples about how to get started at: https://fakerjs.dev/guide/

