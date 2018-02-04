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

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.ScrollingMovementMethod;
import android.text.style.StyleSpan;
import android.util.AttributeSet;

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
public class MessageView extends AppCompatTextView {

    public MessageView(Context context) {
        super(context);
        init();
    }

    public MessageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MessageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setBackgroundColor(0xffeeeeee);
        setTextSize(12f);
        setVerticalScrollBarEnabled(true);
        setMovementMethod(new ScrollingMovementMethod());
    }

    public void appendSystemMessage(String msg) {
        final String prefixStr = getResources().getString(R.string.msg_prefix_sys);
        appendMessage(prefixStr, msg);
    }

    public void appendServerMessage(String msg) {
        final String prefixStr = getResources().getString(R.string.msg_prefix_server);
        appendMessage(prefixStr, msg);
    }

    public void appendClientMessage(String msg) {
        final String prefixStr = getResources().getString(R.string.msg_prefix_client);
        appendMessage(prefixStr, msg);
    }

    public void appendMessage(String prefixText, String msgBody) {
        final SpannableString str = new SpannableString(prefixText.concat(msgBody).concat("\n"));
        str.setSpan(new StyleSpan(Typeface.BOLD), 0, prefixText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        append(str);
    }
}
