package com.insieme.android;


import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.insieme.android.user.task.impl.LoginTask;
import com.insieme.android.user.task.impl.RegisterTask;
import com.insieme.common.domain.dto.InsiemeExceptionEntity;
import com.insieme.common.domain.dto.UserEntity;
import com.insieme.common.domain.rest.RestResult;

@ContentView(R.layout.activity_login)
public class LoginActivity extends RoboActivity{
	@InjectView(R.id.userNameInputId) 
	private EditText userIdInput;
	
	@InjectView(R.id.passwordInputId)
	private EditText userPasswordInput;
	
	@InjectView(R.id.registerEmailInputId)
	private EditText registerEmailInput;
	
	@InjectView(R.id.registerFirstNameInputId)
	private EditText registerFirstNameInput;
	
	@InjectView(R.id.registerLastNameInputId)
	private EditText registerLastNameInput;
	
	@InjectView(R.id.registerPasswordInputId)
	private EditText regiterPasswordInput;
	
	@InjectView(R.id.registerUserInputId)
	private EditText registerUserIdInput;
	
	@Inject
	private Provider<LoginTask> loginTaskProvider;
	
	@Inject
	private Provider<RegisterTask> registerTaskProvider;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    
    /**
     * Login user binding.
     * 
     * TODO: Only takes happy path into account. Need some kind of exception handling wrapper.
     */
    public void loginUser(View v) {
    	try{
	    	String userPassword = userPasswordInput.getText().toString();
			String userId = userIdInput.getText().toString();
    		
			RestResult<UserEntity> loginResult = loginTaskProvider.get().execute(userId, userPassword).get();
    		if (loginResult.isFailure()) {
    			InsiemeExceptionEntity insiemeExceptionEntity = loginResult.getError();
    			Toast.makeText(this, "welcome: " + insiemeExceptionEntity.getException(), Toast.LENGTH_LONG).show();
    		} else {
    			UserEntity userEntity = loginResult.getRestResult();
    			Toast.makeText(this, "welcome: " + userEntity.getFirstName(), Toast.LENGTH_LONG).show();
    		}
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public void registerUser(View v) {
    	String userId = registerUserIdInput.getText().toString();
    	String userPassword = regiterPasswordInput.getText().toString();
    	String userFirstName = registerFirstNameInput.getText().toString();
    	String userLastName  = registerLastNameInput.getText().toString();
    	String userEmail = registerEmailInput.getText().toString();
    	
    	try{
	    	RestResult<UserEntity> registerResult = registerTaskProvider.get()
	    			.execute(userId, userPassword, userFirstName, userLastName, userEmail)
	    			.get();
	    	
	    	if (registerResult.isFailure()) {
    			InsiemeExceptionEntity insiemeExceptionEntity = registerResult.getError();
    			Toast.makeText(this, "registration failed: " + insiemeExceptionEntity.getException(), Toast.LENGTH_LONG).show();
    		} else {
    			UserEntity userEntity = registerResult.getRestResult();
    			Toast.makeText(this, "welcome: " + userEntity.getFirstName(), Toast.LENGTH_LONG).show();
    		}
	    	
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_login, menu);
        return true;
    }

    
}
