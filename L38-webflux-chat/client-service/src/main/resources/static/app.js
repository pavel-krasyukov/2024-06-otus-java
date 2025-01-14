let stompClient = null;

const chatLineElementId = "chatLine";
const roomIdElementId = "roomId";
const messageElementId = "message";
const sendButtonElementId = "send";

const setConnected = (connected) => {
    const connectBtn = document.getElementById("connect");
    const disconnectBtn = document.getElementById("disconnect");

    connectBtn.disabled = connected;
    disconnectBtn.disabled = !connected;
    const chatLine = document.getElementById(chatLineElementId);
    chatLine.hidden = !connected;
}

const connect = () => {
    stompClient = Stomp.over(new SockJS('/gs-guide-websocket'));
    stompClient.connect({}, (frame) => {
        setConnected(true);
        const userName = frame.headers["user-name"];
        const roomId = document.getElementById(roomIdElementId).value;
        console.log(`Connected to roomId: ${roomId} frame: ${frame}`);
        const topicName = `/topic/response.${roomId}`;
        const topicNameUser = `/user/${userName}${topicName}`;
        stompClient.subscribe(topicName, (message) => showMessage(JSON.parse(message.body).messageStr));
        stompClient.subscribe(topicNameUser, (message) => showMessage(JSON.parse(message.body).messageStr));
    });
}

const checkRoomId = () => {
    const roomId = document.getElementById(roomIdElementId).value;
    const sendButton = document.getElementById(sendButtonElementId);
    console.log("checkRoomId");
    console.log("roomId=" + roomId);
    sendButton.disabled = (roomId === "1408"); // Деактивируем кнопку, если roomId = "1408"
}

const disconnect = () => {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

const sendMsg = () => {
    const roomId = document.getElementById(roomIdElementId).value;
    const message = document.getElementById(messageElementId).value;
    stompClient.send(`/app/message.${roomId}`, {}, JSON.stringify({'messageStr': message}))
}

const showMessage = (message) => {
    const chatLine = document.getElementById(chatLineElementId);
    let newRow = chatLine.insertRow(-1);
    let newCell = newRow.insertCell(0);
    let newText = document.createTextNode(message);
    newCell.appendChild(newText);
}

document.addEventListener('DOMContentLoaded', () => {
    console.log("addEventListener");
    const roomIdInputElement = document.getElementById(roomIdElementId);
    roomIdInputElement.addEventListener('input', checkRoomId);
});