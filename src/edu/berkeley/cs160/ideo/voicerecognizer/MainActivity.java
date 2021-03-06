package edu.berkeley.cs160.ideo.voicerecognizer;

import java.util.ArrayList;
import java.util.Timer;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.media.AudioManager;
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
	   private Timer speechTimeout = null;
	   String message;
	   AudioManager mAudioManager;
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
	                     mAudioManager.setStreamMute(AudioManager.STREAM_SYSTEM, true);
	            }
	            public void onError(int error)
	            {
	                     Log.d(TAG,  "error " +  error);
	                     switch (error)
	         			{
	         				case SpeechRecognizer.ERROR_AUDIO:
	         					message = "Audio recording error";
	         					speakButton.performClick();
	         					break;
	         				case SpeechRecognizer.ERROR_CLIENT:
	         					message = "Client side error";
	         					break;
	         				case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
	         					message = "Insufficient permissions";
	         					break;
	         				case SpeechRecognizer.ERROR_NETWORK:
	         					message = "Network error";
	         					speakButton.performClick();
	         					break;
	         				case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
	         					message = "Network timeout";
	         					speakButton.performClick();
	         					break;
	         				case SpeechRecognizer.ERROR_NO_MATCH:
	         					message = "No match";
	         					speakButton.performClick();
	         					break;
	         				case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
	         					message = "RecognitionService busy";
	         					
	         					break;
	         				case SpeechRecognizer.ERROR_SERVER:
	         					message = "error from server";
	         					break;
	         				case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
	         					message = "No speech input";
	         					speakButton.performClick();
	         					break;
	         				default:
	         					message = "Not recognised";
	         					speakButton.performClick();
	         					break;
	         			}
	                     mText.setText("error " + message);
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
	                    	 if (data.get(0).toString().equals("stop listening")){
	                    		//Stop listening 
	                    	 }else{
	                    		 speakButton.performClick();
	                    	 }
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
	                 mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
	                sr.startListening(intent);
	                Log.i("111111","11111111");
	            }
	   }
	@Override
	public void onClick(DialogInterface arg0, int arg1) {
		// TODO Auto-generated method stub
			
	}
}
