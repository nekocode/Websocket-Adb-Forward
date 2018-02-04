#!/usr/bin/env python

import asyncio
import websockets
import os

FORWARD_PORT = '5000'
SERVER_LOCAL_ADDRESS = 'msg-test'

async def hello():
    async with websockets.connect('ws://localhost:%s' % FORWARD_PORT) as websocket:
        # ping
        async def ping():
            while True:
                await asyncio.sleep(10)
                await websocket.ping()

        asyncio.get_event_loop().create_task(ping())

        # send msg
        async def send_msg():
            for i in range(1, 10):
                msg = "hello " + str(i)
                print('Sended: ' + msg)
                await websocket.send(msg)
                await asyncio.sleep(3)

        asyncio.get_event_loop().create_task(send_msg())

        # listen msg
        while True:
            raw_msg = await websocket.recv()
            print('Received: ' + raw_msg)

            # if receive a 'bye' text, close this session
            if raw_msg == 'bye':
                await websocket.send("bye")
                break

        await websocket.close()
        print("end")


# run adb forward firstly
os.system('adb forward tcp:%s localabstract:%s' % (FORWARD_PORT, SERVER_LOCAL_ADDRESS))
asyncio.get_event_loop().run_until_complete(hello())
