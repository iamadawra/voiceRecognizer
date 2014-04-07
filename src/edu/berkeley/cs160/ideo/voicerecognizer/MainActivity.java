package edu.berkeley.cs160.ideo.voicerecognizer;

import java.util.ArrayList;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
	
	  //Source:
	  // http://stackoverflow.com/questions/4975443/is-there-a-way-to-use-the-speechrecognizer-api-directly-for-speech-input
	
	   private TextView mText;
	   private SpeechRecognizer sr;
	   private static final String TAG = "MyStt3Activity";
	   Button speakButton;
	   @Override
	   public void onCreate(Bundle savedInstanceState) 
	   {
	            super.onCreate(savedInstanceState);
	            setContentView(R.layout.activity_main);
	            speakButton = (Button) findViewById(R.id.bVoice);     
	            mText = (TextView) findViewById(R.id.tv);     
	            //speakButton.setOnClickListener(this);
	            sr = SpeechRecognizer.createSpeechRecognizer(this);       
	            sr.setRecognitionListener(new listener());        
	   }

	   class listener implements RecognitionListener          
	   {
	            public void onReadyForSpeech(Bundle params)
	            {
	                     Log.d(TAG, "onReadyForSpeech");
	            }
	            public void onBeginningOfSpeech()
	            {
	                     Log.d(TAG, "onBeginningOfSpeech");
	            }
	            public void onRmsChanged(float rmsdB)
	            {
	                     Log.d(TAG, "onRmsChanged");
	            }
	            public void onBufferReceived(byte[] buffer)
	            {
	                     Log.d(TAG, "onBufferReceived");
	            }
	            public void onEndOfSpeech()
	            {
	                     Log.d(TAG, "onEndofSpeech");
	            }
	            public void onError(int error)
	            {
	                     Log.d(TAG,  "error " +  error);
	                     mText.setText("error " + error);
	                     speakButton.setText("Start Listening");
	                     
	            }
	            public void onResults(Bundle results)                   
	            {
	                     String str = new String();
	                     Log.d(TAG, "onResults " + results);
	                     ArrayList data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
	                     for (int i = 0; i < data.size(); i++)
	                     {
	                               Log.d(TAG, "result " + data.get(i));
	                               str += data.get(i);
	                     }
	                     //mText.setText("results: "+String.valueOf(data.size()));
	                     if (data.size() > 0){
	                    	 speakButton.setText("Start Listening");
	                    	 mText.setText(data.get(0).toString());
	                     }
	                     
	            }
	            public void onPartialResults(Bundle partialResults)
	            {
	                     Log.d(TAG, "onPartialResults");
	            }
	            public void onEvent(int eventType, Bundle params)
	            {
	                     Log.d(TAG, "onEvent " + eventType);
	            }
	   }
	   public void onClick(View v) {
	            if (v.getId() == R.id.bVoice) 
	            {
	            	speakButton.setText("Stop Listening");
	                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);        
	                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
	                intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,"voice.recognition.test");

	                intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,5); 
	                     sr.startListening(intent);
	                     Log.i("111111","11111111");
	            }
	   }
	@Override
	public void onClick(DialogInterface arg0, int arg1) {
		// TODO Auto-generated method stub
			
	}
}
