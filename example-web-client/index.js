function appendMessage(msgHtml) {
    var msgsElement = document.getElementById("msgs-text")
    msgsElement.innerHTML += msgHtml
    msgsElement.appendChild(document.createElement("br"))
}

function appendSystemMessage(msgText) {
    appendMessage("<strong>System: </strong>" + msgText)
}

function appendClientMessage(msgText) {
    appendMessage("<strong>Client: </strong>" + msgText)
}

function appendServerMessage(msgText) {
    appendMessage("<strong>Server: </strong>" + msgText)
}

function showPanel() {
    document.getElementById("connect-tip").style.display = "none"
    document.getElementById("panel").style.display = "block"
}

websocket = new WebSocket("ws://localhost:5000")
websocket.onopen = function(evt) {
    showPanel()
    appendSystemMessage("Connected to the server.")
}
websocket.onclose = function(evt) {
    appendSystemMessage("Disconnected the server.")
}
websocket.onmessage = function(evt) {
    appendServerMessage(evt.data)
}
websocket.onerror = function(evt) {
    appendSystemMessage("Something went wrong.")
}

document.getElementById("send-btn").onclick = function() {
    var inputElement = document.getElementById("msg-input")
    websocket.send(inputElement.value)
    appendClientMessage(inputElement.value)
    inputElement.value = ""    
}