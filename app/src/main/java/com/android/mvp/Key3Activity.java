package com.android.mvp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.EditText;

import com.android.mvp.util.Key3boardUtil;
import com.android.mvp.util.Key4boardUtil;

public class Key3Activity extends Activity {
	private Context ctx;
	private Activity act;
	private EditText edit;
	private EditText edit1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_key3);
		ctx = this;
		act = this;

		edit = (EditText) this.findViewById(R.id.edit);
		edit.setInputType(InputType.TYPE_NULL);

		edit1 = (EditText) this.findViewById(R.id.edit1);

		edit.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				new Key4boardUtil(act, ctx, edit, Key4boardUtil.KeyboardStyle.KEYBOARD_STYLE_K4).showKeyboard();
				return false;
			}
		});

		edit1.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int inputback = edit1.getInputType();
				edit1.setInputType(InputType.TYPE_NULL);
				new Key3boardUtil(act, ctx, edit1).showKeyboard();
				edit1.setInputType(inputback);
				return false;
			}
		});

	}
}