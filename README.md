For interacting with the chrome explorer on computer, the well-known [stetho](https://github.com/facebook/stetho) library has implemented a tiny websocket server internally. In the same way, we can use it to make a local websocket server on mobile, and interact with our clients on computer too.

This project demonstrates a simple messenger system between mobile and computer. It includes a [android server app](example-server-app), some clients on computer such as a [py-client](example-py-client), and a [web-client](web-client). And through the `adb forward` command, we can setup a webscoket connection between the server on mobile and clients on computer. You can see the code for more detail.

![screenshot](img/screenshot.png)