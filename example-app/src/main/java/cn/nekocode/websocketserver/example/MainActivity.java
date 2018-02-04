/*
 * Copyright 2018 nekocode
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.nekocode.websocketserver.example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.facebook.stetho.websocket.SimpleEndpoint;
import com.facebook.stetho.websocket.SimpleSession;

import java.util.ArrayList;

import cn.nekocode.websocketserver.LocalWebSocketServer;

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
public class MainActivity extends AppCompatActivity {
    private static final String ADDRESS = "msg-test";
    private MessageView mMessageView;
    private EditText mMessageEditText;
    private LocalWebSocketServer mServer;
    private MessageEndpoint mEndpoint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMessageView = findViewById(R.id.messageTv);
        mMessageEditText = findViewById(R.id.messageEt);
        findViewById(R.id.sendBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEndpoint != null) {
                    mEndpoint.broadcast(mMessageEditText.getText().toString());
                    mMessageEditText.setText("");
                }
            }
        });

        startServer();
    }

    private void startServer() {
        mServer = LocalWebSocketServer.createAndStart(this, ADDRESS, mEndpoint = new MessageEndpoint());
        mMessageView.appendSystemMessage(getString(R.string.msg_server_started, ADDRESS));
    }

    @Override
    protected void onDestroy() {
        mServer.stop();
        super.onDestroy();
    }


    /**
     * A simple message server endpoint
     *
     * Be aware of that the endpoint's callbacks will be called on a non-ui thread.
     * So you should not do ui-operations directly in these callbacks.
     */
    class MessageEndpoint implements SimpleEndpoint {
        private ArrayList<SimpleSession> sessions = new ArrayList<>();


        void broadcast(final String message) {
            for (SimpleSession session : sessions) {
                session.sendText(message);
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mMessageView.appendServerMessage(message);
                }
            });
        }

        @Override
        public void onOpen(SimpleSession session) {
            sessions.add(session);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mMessageView.appendSystemMessage(getString(R.string.msg_client_connected));
                }
            });
        }

        @Override
        public void onMessage(SimpleSession session, final String message) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mMessageView.appendClientMessage(message);
                }
            });
        }

        @Override
        public void onMessage(SimpleSession session, byte[] message, int messageLen) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mMessageView.appendSystemMessage(getString(R.string.msg_client_ignored));
                }
            });
        }

        @Override
        public void onClose(SimpleSession session, int closeReasonCode, String closeReasonPhrase) {
            sessions.remove(session);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mMessageView.appendSystemMessage(getString(R.string.msg_client_disconnected));
                }
            });
        }

        @Override
        public void onError(SimpleSession session, Throwable t) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mMessageView.appendSystemMessage(getString(R.string.msg_client_error));
                }
            });
        }
    }
}
