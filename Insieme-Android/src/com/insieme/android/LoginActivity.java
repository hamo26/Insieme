package com.insieme.android;


import org.springframework.util.StringUtils;

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
import com.insieme.android.user.service.impl.LoginTask;
import com.insieme.common.domain.dto.InsiemeExceptionEntity;
import com.insieme.common.domain.dto.UserEntity;
import com.insieme.common.domain.rest.RestResult;

@ContentView(R.layout.activity_login)
public class LoginActivity extends RoboActivity{
	@InjectView(R.id.userNameInputId) 
	private EditText userIdInput;
	
	@InjectView(R.id.passwordInputId)
	private EditText userPasswordInput;
	
	@Inject
	private Provider<LoginTask> loginTaskProvider;
	
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
			
			if (!StringUtils.hasText(userId) || 
	    			!StringUtils.hasText(userPassword)) {
	    			Toast.makeText(this, "missing username or password.", Toast.LENGTH_LONG).show();
	    	} else {
	    		RestResult<UserEntity> loginResult = loginTaskProvider.get().execute(userId, userPassword).get();
	    		if (loginResult.isFailure()) {
	    			InsiemeExceptionEntity insiemeExceptionEntity = loginResult.getError();
	    			Toast.makeText(this, "welcome: " + insiemeExceptionEntity.getException(), Toast.LENGTH_LONG).show();
	    		} else {
	    			UserEntity userEntity = loginResult.getRestResult();
	    			Toast.makeText(this, "welcome: " + userEntity.getFirstName(), Toast.LENGTH_LONG).show();
	    		}
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
